package com.xiaomai.telemarket.module.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
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
import com.xiaomai.telemarket.module.home.dial.HomeDialingContract;
import com.xiaomai.telemarket.module.home.dial.HomeDialingPresenter;
import com.xiaomai.telemarket.module.home.setting.SettingActivity;
import com.xiaomai.telemarket.module.order.OrderActivity;
import com.xiaomai.telemarket.utils.RegexUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
public class HomeFragment extends BaseFragment implements HomeDialingContract.View {
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

    /**
     * 拨号相关
     */
    private HomeDialingPresenter homeDialingPresenter;
//    private DialingPresenter dialingPresenter;
//    private String preDialCusFollowTime = "";//前一个已经拨号的用户时间
//    private String preUserID = "";//前一个已经拨号的用户时间
//    private boolean isStopped = true;
//
//    private boolean isFromPublic;//拨号来源 true -public,false-private
//    private boolean isDialingByGroup;//拨号方式 tur -by group，false - by single
//    public static final int MSG_DIAL_REQUEST = 0x001;

    private boolean isRecordPermissible;
    public static final int RECORD_PERMISSION_REQUEST_CODE = 0x001;

    private HomeMenuItemClickListener homeMenuItemClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        homeDialingPresenter = new HomeDialingPresenter(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //检测录音权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_REQUEST_CODE);
        } else {
            isRecordPermissible = true;
            homeDialingPresenter.checkIsDialingGroupUnStoppedAndDialingOut();
        }
        if (HomeUserStateTextView != null) {
            HomeUserStateTextView.setText(ISharedPreferencesUtils.getValue(getActivity(), Constant.USER_STATE_NAME_KEY, "上线").toString());
        }
    }

    @Override
    public void showIsDialingGroupUnStopped(boolean isStopped) {
        if (tvDialGroupState != null && HomeImageDial != null) {
            if (isStopped) {
                tvDialGroupState.setText(getResources().getString(R.string.dial_star_by_group));
                HomeImageDial.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_call));
            } else {
                tvDialGroupState.setText(getResources().getString(R.string.dial_stop_group));
                HomeImageDial.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_sing_call));
            }
        }
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
                if (homeDialingPresenter.getIsDialingGroupStoped()) {
                    homeDialingPresenter.startDialingByGroup();
                } else {
                    homeDialingPresenter.stopDialingByGroup();
                }
                break;
            case R.id.Home_singCall_Layout:/*点呼*/
                homeDialingPresenter.startDialingBySingle();
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
        if (requestCode == RECORD_PERMISSION_REQUEST_CODE) {
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                isRecordPermissible = true;
            } else {
                // Permission Denied
                isRecordPermissible = false;
            }
        }
    }

    @Override
    public void showDialingByGroupStarted() {
        if (tvDialGroupState!=null) {
            tvDialGroupState.setText(getResources().getString(R.string.dial_stop_group));
            HomeImageDial.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_sing_call));
        }
    }

    @Override
    public void showDialingOutStarted(CusrometListEntity entity) {
        if (entity!=null) {
            ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, true);
            ISystemUtil.makeCall(getActivity(), entity.getCustomerTel(), true);
        }
    }

    @Override
    public void showDialingStopped() {
        if (tvDialGroupState != null) {
            tvDialGroupState.setText(getResources().getString(R.string.dial_star_by_group));
            HomeImageDial.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_call));
        }
    }

    @Override
    public void showRequestNumberFailed(String msg) {
        // TODO: 30/05/2017 请求号码错误
        showToast(msg);
        if (homeDialingPresenter!=null&&!homeDialingPresenter.getIsDialingGroupStoped()) {
            homeDialingPresenter.startDialingByGroup();// TODO: 30/05/2017 请求号码失败,只有处于群拨状态时才重拨
        }
    }

    @Override
    public void showDialingFinished(String msg) {
        if (homeDialingPresenter != null) {
            homeDialingPresenter.stopDialingByGroup();
        }
        DialogFactory.showMsgDialog(getActivity(), "提示", msg+"是否停止拨号?", "停止", "取消",null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeDialingPresenter.stopDialingByGroup();
            }
        });
    }

    private void dialingSingle() {
        if (!isRecordPermissible) {
            ToastUtil.showToast(getActivity(), "录音权限被禁止！请在权限管理中允许录音权限");
            return;
        }
        final EditText editText = new EditText(getActivity());
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("输入电话号码");
        editText.setText("10010");
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

    @Subscribe
    public void notifactionFollowNumber(EventBusValues values){
        switch (values.getWhat()){
            case 0x900:

                break;
        }
    }
}
