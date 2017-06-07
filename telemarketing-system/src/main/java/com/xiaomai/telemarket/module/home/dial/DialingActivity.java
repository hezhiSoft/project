package com.xiaomai.telemarket.module.home.dial;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISystemUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.utils.RegexUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 拨号界面
 * @createtime 26/05/2017 2:17 AM
 **/
@Deprecated
public class DialingActivity extends XiaoMaiBaseActivity implements DialingContract.View {
    private static final String TAG = "DialingActivity";

    @BindView(R.id.img_dial_switch)
    ImageView imgDialSwitch;
    @BindView(R.id.tv_dial_name)
    TextView tvDialName;
    @BindView(R.id.tv_dial_state)
    TextView tvDialState;
    @BindView(R.id.img_dial_mute)
    ImageView imgDialMute;
    @BindView(R.id.img_dial_mark_empty)
    ImageView imgDialMarkEmpty;
    @BindView(R.id.img_dial_handoff)
    ImageView imgDialHandoff;
    @BindView(R.id.img_dial_stop)
    ImageView imgDialStop;
    @BindView(R.id.img_dial_cancel)
    ImageView imgDialCancel;
    @BindView(R.id.tv_dial_stop_on)
    TextView tvDialStopOn;

    private DialingPresenter dialingPresenter;
    private String preDialCusId = "";//前一个已经拨号的用户
    private boolean isStopped = false;

    private boolean isFromPublic;//拨号来源 true -public,false-private
    private boolean isDialByGroup;//是否群呼 // TODO: 27/05/2017 点呼到时候也复用这个界面
    public static final String EXTRA_DIAL_TYPE = "dial_type";
    public static final int MSG_START_REQUEST = 0x001;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialing);
        ButterKnife.bind(this);
        setDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setToolbarVisibility(View.GONE);
        if (savedInstanceState!=null) {
            isFromPublic = savedInstanceState.getBoolean("isFromPublic");
            isStopped = savedInstanceState.getBoolean("isStopped");
            preDialCusId = savedInstanceState.getString("preDialCusId");
        }
        initData();
    }

    private void initData() {
        isFromPublic = ISharedPreferencesUtils.getValue(this, Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PRIVATE)
                .equals(Constant.DIAL_NUMBER_CODE_PUBLIC);
        isDialByGroup = getIntent().getBooleanExtra(EXTRA_DIAL_TYPE, true);
        dialingPresenter = new DialingPresenter(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFromPublic",isFromPublic);
        outState.putBoolean("isStopped", isStopped);
        outState.putString("preDialCusId", preDialCusId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        restartRequest();
    }

    @Override
    protected void onDestroy() {
        stopRequest();
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //防止再次点击屏幕时恢复状态栏和导航栏
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setDecorView();
        }
    }

    private void setDecorView() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(option);
    }

    @OnClick({R.id.img_dial_switch, R.id.img_dial_mute, R.id.img_dial_mark_empty, R.id.img_dial_handoff, R.id.img_dial_stop, R.id.img_dial_cancel})
    public void onViewClicked(View view) {
        String msg = "";
        switch (view.getId()) {
            case R.id.img_dial_switch:
                msg = "switch";
                break;
            case R.id.img_dial_mute:
                msg = "mute";
                break;
            case R.id.img_dial_mark_empty:
                msg = "empty";
                break;
            case R.id.img_dial_handoff:
                msg = "handoff";
                break;
            case R.id.img_dial_stop:
                if (isStopped) {
                    //继续
                    isStopped = !isStopped;
                    tvDialStopOn.setText(getResources().getString(R.string.dial_stop_group));
                    restartRequest();
                } else {
                    stopRequest();
                }
                break;
            case R.id.img_dial_cancel:
                msg = "群呼结束";
                stopRequest();
                finish();
                break;
        }
        if (!msg.isEmpty()) {
            ToastUtil.showToast(this, msg);
        }
    }

    @Override
    public void showRequestNumberStar() {
        Log.i(TAG, "showRequestNumberStar: -----start request ----");
        tvDialName.setText(getResources().getString(R.string.dial_xxx));
        tvDialState.setText(getResources().getString(R.string.dial_requesting));
    }

    @Override
    public void showRequestNumberSuccess(CusrometListEntity entity) {
        if (entity == null) {
            restartRequest();
        } else {
            // 用户没有手动结束
            if (!isStopped) {
                preDialCusId = entity.getID();
                tvDialName.setText(entity.getCustomerName());
                String phoneNumber = entity.getCustomerTel();
                if (RegexUtils.isPhoneLegal(phoneNumber)) {
                    //拨出号码
                    ISystemUtil.makeCall(DialingActivity.this, phoneNumber, true);
                    tvDialState.setText(getResources().getString(R.string.dial_dialling));
                } else {
                    showToast("无效号码！");
                    restartRequest();
                }
            }
        }
    }

    @Override
    public void showRequestNumberFailed(String msg) {
        Log.i(TAG, "showRequestNumberFailed: "+msg);
        showToast("请求失败！重连中...");
        restartRequest();
    }

    @Override
    public void showRequestNumberStoped() {
        if (TextUtils.isEmpty(preDialCusId)) {
            tvDialName.setText(getResources().getString(R.string.dial_xxx));
        }
        tvDialState.setText(getResources().getString(R.string.dial_stoped));
        tvDialStopOn.setText(getResources().getString(R.string.dial_resume_group));
        isStopped = true;
    }

    @Override
    public void showRequestFinished(String mag) {

    }

    @Override
    protected void dispatchMessage(Message msg) {
        if (msg.what == MSG_START_REQUEST) {
            //重新发起请求
            isFromPublic = ISharedPreferencesUtils.getValue(this, Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PRIVATE)
                    .equals(Constant.DIAL_NUMBER_CODE_PUBLIC);
            if (isFromPublic) {
                dialingPresenter.requestNumberFromPublic();
                Log.i(TAG, "dispatchMessage: request from #public");
            } else {
                dialingPresenter.requestNumberFromPrivate(preDialCusId);
                Log.i(TAG, "dispatchMessage: request from #private");
            }

        }
    }

    /**
     * 重新请求号码
     */
    private void restartRequest() {
        if (!isStopped) {
            mHandler.sendEmptyMessage(MSG_START_REQUEST);
            isStopped = false;
        }
    }


    /**
     * 停止群呼
     */
    private void stopRequest() {
        if (mHandler != null && mHandler.hasMessages(MSG_START_REQUEST)) {
            mHandler.removeMessages(MSG_START_REQUEST);
        }
        dialingPresenter.stopRequest();
        isStopped = true;
        Log.i(TAG, "stopRequest: -----stop request ----");
    }


}
