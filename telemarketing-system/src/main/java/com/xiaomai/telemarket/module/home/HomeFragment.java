package com.xiaomai.telemarket.module.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.ISystemUtil;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.MainActivity;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.account.data.UserInfoEntity;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.function.callOut.CallOutActivity;
import com.xiaomai.telemarket.module.function.callTrend.CallTrendActivity;
import com.xiaomai.telemarket.module.home.dial.DialingContract;
import com.xiaomai.telemarket.module.home.dial.DialingPresenter;
import com.xiaomai.telemarket.module.home.setting.SettingActivity;
import com.xiaomai.telemarket.module.order.OrderActivity;
import com.xiaomai.telemarket.utils.RegexUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/14 11:36
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class HomeFragment extends BaseFragment implements DialingContract.View {
    private static final String TAG = "HomeFragment";

    @BindView(R.id.Home_UserHead_iamgeView)
    ImageView HomeUserHeadIamgeView;
    @BindView(R.id.Home_UserName_TextView)
    TextView HomeUserNameTextView;
    @BindView(R.id.Home_Deparment_TextView)
    TextView HomeDeparmentTextView;
    @BindView(R.id.tv_dial_group_state)
    TextView tvDialGroupState;
    Unbinder unbinder;

    //拨号相关
    private DialingPresenter dialingPresenter;
    private String preDialCusId = "";//前一个已经拨号的用户
    private boolean isStopped = true;

    private boolean isFromPublic;//拨号来源 true -public,false-private
    public static final int MSG_START_REQUEST = 0x001;

    private HomeMenuItemClickListener homeMenuItemClickListener;

    private boolean isRecordPermissed;
    public static final int RECORD_PERMISSTION_REQUEST_CODE = 0x001;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, rootView);
        Context context = getActivity();
        if (context instanceof HomeMenuItemClickListener) {
            homeMenuItemClickListener = (HomeMenuItemClickListener) context;
        }
        initUI();
        initData(savedInstanceState);
        return rootView;
    }

    private void initUI() {
        UserInfoEntity entity = DataApplication.getInstance().getUserInfoEntity();
        if (entity != null) {
            HomeUserNameTextView.setText(entity.getDisplayName());
        }
    }

    private void initData(Bundle savedInstanceState) {
        isFromPublic = ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PRIVATE)
                .equals(Constant.DIAL_NUMBER_CODE_PUBLIC);
        if (savedInstanceState!=null) {
            isFromPublic = savedInstanceState.getBoolean("isFromPublic");
            isStopped = savedInstanceState.getBoolean("isStopped");
            preDialCusId = savedInstanceState.getString("preDialCusId");
        }
        dialingPresenter = new DialingPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},
                    RECORD_PERMISSTION_REQUEST_CODE);
        } else {
            isRecordPermissed = true;
            if (!isStopped) {
                restartRequest();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFromPublic",isFromPublic);
        outState.putBoolean("isStopped", isStopped);
        outState.putString("preDialCusId", preDialCusId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Home_call_TextView, R.id.Home_trend_TextView, R.id.Home_seting_TextView, R.id.Home_groupCall_Layout, R.id.Home_singCall_Layout, R.id.Home_customer_TextView, R.id.Home_customerStay_TextView, R.id.Home_Search_TextView, R.id.Home_order_TextView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Home_seting_TextView:/*设置*/
                ISkipActivityUtil.startIntent(getActivity(), SettingActivity.class);
                break;
            case R.id.Home_groupCall_Layout:/*群呼*/
                // TODO: 28/05/2017 群呼
//                ISkipActivityUtil.startIntent(getActivity(), DialingActivity.class);
                if (isStopped) {
                    tvDialGroupState.setText(getResources().getString(R.string.dial_stop_group));
                    restartRequest();
                }else{
                    stopRequest();
                    tvDialGroupState.setText(getResources().getString(R.string.dial_star_by_group));
                }
                break;
            case R.id.Home_singCall_Layout:/*点呼*/
                dialingSingle();
                break;
            case R.id.Home_customer_TextView:/*客户管理*/
                if (homeMenuItemClickListener != null) {
                    homeMenuItemClickListener.onMenuItemClick(MainActivity.TAB_CUSROMENTMANAGEMENT);
                }
                break;
            case R.id.Home_customerStay_TextView:/*待跟进订单*/
                if (homeMenuItemClickListener != null) {
                    homeMenuItemClickListener.onMenuItemClick(MainActivity.TAB_CUSROMENTLIST);
                }
                break;
            case R.id.Home_Search_TextView:/*产品查询*/
                break;
            case R.id.Home_order_TextView:/*订单管理*/
//                if (homeMenuItemClickListener != null) {
//                    homeMenuItemClickListener.onMenuItemClick(MainActivity.TAB_ORDER);
//                }
                ISkipActivityUtil.startIntent(getContext(), OrderActivity.class);
                break;
            case R.id.Home_call_TextView:/*员工外呼*/
                ISkipActivityUtil.startIntent(getContext(), CallOutActivity.class);
                break;
            case R.id.Home_trend_TextView:/*外呼趋势*/
                ISkipActivityUtil.startIntent(getContext(), CallTrendActivity.class);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_PERMISSTION_REQUEST_CODE) {
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                isRecordPermissed = true;
            } else {
                // Permission Denied
                isRecordPermissed = false;
            }
        }
    }

    private void dialingSingle() {
        if (!isRecordPermissed) {
            ToastUtil.showToast(getActivity(), "录音权限被禁止！请在权限管理中允许录音权限");
            return;
        }
        final EditText editText = new EditText(getActivity());
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("输入电话号码");
        editText.setText("10086");
        new AlertDialog.Builder(getActivity())
                .setTitle("输入电话号码")
                .setView(editText)
                .setPositiveButton("拨号", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String phoneNumber = editText.getText().toString();
                        if (RegexUtils.isPhoneLegal(phoneNumber)) {
                            ISystemUtil.makeCall(getActivity(), phoneNumber, true);
                        } else {
                            showToast("电话号码格式不正确！");
                        }
                    }
                }).create().show();
    }

    @Override
    public void showRequestNumberStar() {
        Log.i(TAG, "showRequestNumberStar: -----start request ----");
    }

    @Override
    public void showRequestNumberSuccess(CusrometListEntity entity) {
        if (entity == null) {
            if (!isStopped) {
                restartRequest();
            }
        } else {
            // 用户没有手动结束
            if (!isStopped) {
                preDialCusId = entity.getID();
                String phoneNumber = entity.getCustomerTel();
                if (RegexUtils.isPhoneLegal(phoneNumber)) {
                    //拨出号码
                    ISystemUtil.makeCall(getActivity(), phoneNumber, true);
                } else {
//                    showToast("无效号码！");
                    if (!isStopped) {
                        restartRequest();
                    }
                }
            }
        }
    }

    @Override
    public void showRequestNumberFailed(String msg) {
        Log.i(TAG, "showRequestNumberFailed: " + msg);
//        showToast("请求失败！重连中...");
        if (!isStopped) {
            restartRequest();
        }
    }

    @Override
    public void showRequestNumberStoped() {
        isStopped = true;
    }

    @Override
    protected void dispatchMessage(Message msg) {
        if (msg.what == MSG_START_REQUEST) {
            //重新发起请求
            isFromPublic = ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PRIVATE).equals(Constant.DIAL_NUMBER_CODE_PUBLIC);
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
        mHandler.sendEmptyMessage(MSG_START_REQUEST);
        isStopped = false;
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
