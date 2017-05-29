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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
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
    @BindView(R.id.Home_UserState_TextView)
    TextView HomeUserStateTextView;
    @BindView(R.id.tv_dial_group_state)
    TextView tvDialGroupState;
    @BindView(R.id.Home_Image_Dial)
    ImageView HomeImageDial;
    @BindView(R.id.Home_groupCall_Layout)
    RelativeLayout HomeGroupCallLayout;
    Unbinder unbinder;

    //拨号相关
    private DialingPresenter dialingPresenter;
    private String preDialCusFollowTime = "";//前一个已经拨号的用户时间
    private String preUserID = "";//前一个已经拨号的用户时间
    private boolean isStopped = true;

    private boolean isFromPublic;//拨号来源 true -public,false-private
    private boolean isDialingByGroup;
//    private boolean isFirstDial = true;
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
            HomeDeparmentTextView.setText(entity.getDepartment());
        }
        HomeUserStateTextView.setText(ISharedPreferencesUtils.getValue(getActivity(), Constant.USER_STATE_NAME_KEY, "上线").toString());
    }

    private void initData(Bundle savedInstanceState) {
        isFromPublic = ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PRIVATE)
                .equals(Constant.DIAL_NUMBER_CODE_PUBLIC);
        if (savedInstanceState != null) {
            isFromPublic = savedInstanceState.getBoolean("isFromPublic");
            isStopped = savedInstanceState.getBoolean("isStopped");
//            isFirstDial = savedInstanceState.getBoolean("isFirstDial");
            isDialingByGroup = savedInstanceState.getBoolean("isDialingByGroup");
            preDialCusFollowTime = savedInstanceState.getString("preDialCusFollowTime");
        }
        dialingPresenter = new DialingPresenter(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFromPublic", isFromPublic);
        outState.putBoolean("isStopped", isStopped);
        outState.putBoolean("isDialingByGroup", isDialingByGroup);
//        outState.putBoolean("isFirstDial", isFirstDial);
        outState.putString("preDialCusFollowTime", preDialCusFollowTime);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (HomeUserStateTextView != null) {
            HomeUserStateTextView.setText(ISharedPreferencesUtils.getValue(getActivity(), Constant.USER_STATE_NAME_KEY, "上线").toString());
        }
        if (tvDialGroupState != null) {
            if (isStopped) {
                tvDialGroupState.setText(getResources().getString(R.string.dial_star_by_group));
                HomeImageDial.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_call));
            } else {
                tvDialGroupState.setText(getResources().getString(R.string.dial_stop_group));
                HomeImageDial.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_sing_call));
            }
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},
                    RECORD_PERMISSTION_REQUEST_CODE);
        } else {
            isRecordPermissed = true;
//            isDialingByGroup = (boolean) ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_BY_GROUP, true);
            if (!isStopped && !getDialingState()) {
                Log.i(TAG, "onResume: request again###");
                restartRequest();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
                isDialingByGroup = true;
                setDialByGroup(isDialingByGroup);
                // TODO: 28/05/2017 群呼
//                ISkipActivityUtil.startIntent(getActivity(), DialingActivity.class);
                if (isStopped) {
                    tvDialGroupState.setText(getResources().getString(R.string.dial_stop_group));
                    HomeImageDial.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_sing_call));
                    restartRequest();
                } else {
                    stopRequest();
                    tvDialGroupState.setText(getResources().getString(R.string.dial_star_by_group));
                    HomeImageDial.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_call));
                }
                break;
            case R.id.Home_singCall_Layout:/*点呼*/
                isDialingByGroup = false;
                setDialByGroup(isDialingByGroup);
                restartRequest();
//                dialingSingle();
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
            if (!isStopped && !getDialingState()) {
                restartRequest();
                Log.i(TAG, "showRequestNumberSuccess: request null #####273");
            }
        } else {
            // 用户没有手动结束
            if (!isStopped && !getDialingState()) {
                Log.i(TAG, "showRequestNumberSuccess: dial out number#" + entity.getCustomerTel());
                preDialCusFollowTime = entity.getFollowDate();
                preUserID = entity.getID();
                String phoneNumber = entity.getCustomerTel();
                if (RegexUtils.isPhoneLegal(phoneNumber)) {
                    //拨出号码
                    ISystemUtil.makeCall(getActivity(), phoneNumber, true);
                } else {
//                    showToast("无效号码！");
                    if (!isStopped && !getDialingState()) {
                        restartRequest();
                        Log.i(TAG, "showRequestNumberSuccess: request null #####289");
                    }
                }
            }
        }
    }

    @Override
    public void showRequestNumberFailed(String msg) {
        Log.i(TAG, "showRequestNumberFailed: " + msg);
//        showToast("请求失败！重连中...");
        if (!isStopped && !getDialingState()) {
            restartRequest();
            Log.i(TAG, "showRequestNumberSuccess: request null #####302");
        }
    }

    @Override
    public void showRequestFinished(String msg) {
        stopRequest();
        DialogFactory.showMsgDialog(getActivity(), "提示", msg+"是否停止群呼?", "停止", "继续", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    if (HomeGroupCallLayout!=null) {
//                        HomeGroupCallLayout.performClick();
//                    }
                stopRequest();
                tvDialGroupState.setText(getResources().getString(R.string.dial_star_by_group));
                HomeImageDial.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_call));
            }
        }, new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                restartRequest();
            }
        });
    }

    @Override
    public void showRequestNumberStoped() {
        isStopped = true;
    }

    @Override
    protected void dispatchMessage(Message msg) {
        if (msg.what == MSG_START_REQUEST) {
            //重新发起请求
            Log.i(TAG, "dispatchMessage: request again#####");
            isFromPublic = ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PRIVATE).equals(Constant.DIAL_NUMBER_CODE_PUBLIC);
            if (!TextUtils.isEmpty(preUserID)) {
                dialingPresenter.deleteTempInfo(preUserID, new RemetoRepoCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        if (isFromPublic) {
                            dialingPresenter.requestNumberFromPublic();
                            Log.i(TAG, "dispatchMessage: request from #public");
                        } else {
                            dialingPresenter.requestNumberFromPrivate(preDialCusFollowTime);
                            Log.i(TAG, "dispatchMessage: request from #private");
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        showRequestFinished(msg);
                    }

                    @Override
                    public void onThrowable(Throwable t) {

                    }

                    @Override
                    public void onUnauthorized() {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
            }else{
                if (isFromPublic) {
                    dialingPresenter.requestNumberFromPublic();
                    Log.i(TAG, "dispatchMessage: request from #public");
                } else {
                    dialingPresenter.requestNumberFromPrivate(preDialCusFollowTime);
                    Log.i(TAG, "dispatchMessage: request from #private");
                }
            }
        }
    }

    /**
     * 重新请求号码
     */
    private void restartRequest() {
        Log.i(TAG, "restartRequest: request again########");
        mHandler.sendEmptyMessage(MSG_START_REQUEST);
        isStopped = false;
//        mHandler.sendEmptyMessageDelayed(MSG_START_REQUEST, 2000);
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

    /**
     * 获取是否在通话状态中
     *
     * @return
     */
    public boolean getDialingState() {
        return (boolean) ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, false);
    }

    public boolean getIsDialPublic(){
        return ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PRIVATE)
                .equals(Constant.DIAL_NUMBER_CODE_PUBLIC);
    }

    public void setDialByGroup(boolean isGroup){
        ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_BY_GROUP, isGroup);
    }

}
