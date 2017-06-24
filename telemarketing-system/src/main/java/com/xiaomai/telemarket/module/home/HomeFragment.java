package com.xiaomai.telemarket.module.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.ISystemUtil;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.MainActivity;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.account.data.UserInfoEntity;
import com.xiaomai.telemarket.module.cstmr.CusrometDetailsActivity;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.function.callOut.CallOutActivity;
import com.xiaomai.telemarket.module.function.callTrend.CallTrendActivity;
import com.xiaomai.telemarket.module.function.produtQuery.ProdutQueryActivity;
import com.xiaomai.telemarket.module.function.statusCount.StatusCountActivity;
import com.xiaomai.telemarket.module.function.statusQuery.StatusQueryActivity;
import com.xiaomai.telemarket.module.home.dial.HomeDialingContract;
import com.xiaomai.telemarket.module.home.dial.HomeDialingPresenter;
import com.xiaomai.telemarket.module.home.dial.data.source.local.CustomerLocalDataSource;
import com.xiaomai.telemarket.module.home.setting.SettingActivity;
import com.xiaomai.telemarket.module.home.setting.SettingEditActivity;
import com.xiaomai.telemarket.module.order.OrderActivity;
import com.xiaomai.telemarket.service.UploadService;
import com.xiaomai.telemarket.utils.RegexUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.jinggan.library.utils.ISharedPreferencesUtils.setValue;

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

    private boolean isRecordPermissible=true;
    private boolean isCallPhonePermissible=true;
    public static final int RECORD_PERMISSION_REQUEST_CODE = 0x001;
    public static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 0x002;
    public static final int CONTACT_PERMISSION_REQUEST_CODE = 0x003;

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
        checkPermission();
        if (isRecordPermissible && isCallPhonePermissible) {
            homeDialingPresenter.checkIsDialingGroupUnStoppedAndDialingOutNotRefreshUI();
        }
        if (HomeUserStateTextView != null) {
            HomeUserStateTextView.setText(ISharedPreferencesUtils.getValue(getActivity(), Constant.USER_STATE_NAME_KEY, "上线").toString());
        }
    }

    private void checkPermission() {
        //检测录音权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_REQUEST_CODE);
            isRecordPermissible = false;
        }
        //检测通话权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST_CODE);
            isCallPhonePermissible = false;
        }
        //检测联系人读写权限
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS,Manifest.permission.WRITE_CALL_LOG}, CONTACT_PERMISSION_REQUEST_CODE);
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

    @OnClick({R.id.Home_Status_count_TextView,R.id.Home_status_query_TextView,R.id.Home_call_TextView, R.id.Home_trend_TextView, R.id.Home_seting_TextView, R.id.Home_groupCall_Layout, R.id.Home_singCall_Layout, R.id.Home_customer_TextView, R.id.Home_customerStay_TextView, R.id.Home_Search_TextView, R.id.Home_order_TextView,R.id.Home_UserState_TextView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Home_seting_TextView:/*设置*/
                ISkipActivityUtil.startIntent(getActivity(), SettingActivity.class);
                break;
            case R.id.Home_UserState_TextView:
                Bundle bundle=new Bundle();
                bundle.putString(SettingEditActivity.EXTRA_TAG,SettingEditActivity.TAG_USERSTATE);
                ISkipActivityUtil.startIntent(getActivity(), SettingEditActivity.class, bundle);
                break;
            case R.id.Home_groupCall_Layout:/*群呼*/
                if (homeDialingPresenter.getIsDialingGroupStoped()) {
                    // TODO: 04/06/2017 判断正在通话中禁止群呼
                    boolean isDialingOffHook = (boolean) ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, false);
                    if (!isDialingOffHook) {
                        homeDialingPresenter.startDialingByGroup();
                    } else {
                        //正在通话中,不让继续拨出
//                        showToast("");
                    }
                } else {
                    homeDialingPresenter.stopDialingByGroup();//手动停止
                }
                break;
            case R.id.Home_singCall_Layout:/*点呼*/
                if (TextUtils.equals(tvDialGroupState.getText().toString(),getResources().getString(R.string.dial_star_by_group))) {
                    homeDialingPresenter.startDialingBySingle();
                }
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
            ISkipActivityUtil.startIntent(getContext(), ProdutQueryActivity.class);
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
            case R.id.Home_Status_count_TextView:
                ISkipActivityUtil.startIntent(getContext(), StatusCountActivity.class);
                break;
            case R.id.Home_status_query_TextView:
                ISkipActivityUtil.startIntent(getContext(), StatusQueryActivity.class);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_PERMISSION_REQUEST_CODE) {//录音权限
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                isRecordPermissible = true;
            } else {
                // Permission Denied
                isRecordPermissible = false;
            }
        } else if (requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE) {//拨打电话权限
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                isCallPhonePermissible = true;
            } else {
                // Permission Denied
                isCallPhonePermissible = false;
            }
        }
    }

    @Override
    public void showDialingRequestStarted() {
        showProgressDlg("正在获取号码中...");
    }

    @Override
    public void showDialingRequestFinished() {
        dismissProgressDlg();
    }

    @Override
    public void showDialingByGroupStarted() {
        if (tvDialGroupState!=null) {
            tvDialGroupState.setText(getResources().getString(R.string.dial_stop_group));
            HomeImageDial.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_sing_call));
        }
    }

    @Override
    public void showDialingOutStarted(final CusrometListEntity entity) {
        if (entity!=null) {
            String dialingType = ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIALING_TYPE_KEY, "").toString();
            if (!TextUtils.isEmpty(dialingType)) {
                //通知更新客户列表
                EventBusValues busValues = new EventBusValues();
                busValues.setWhat(0x201);
                EventBus.getDefault().post(busValues);
                //从群拨跳转到客户信息界面
                Bundle bundle=new Bundle();
                bundle.putSerializable("entity",entity);
                ISkipActivityUtil.startIntent(getActivity(), CusrometDetailsActivity.class,bundle);
                //拨出，设置正在通话中状态
                setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_FROM_HOME_GROUP_DIALING, true);
                setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, true);
                setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);
                //保存到系统通讯录
                ISystemUtil.makeCall(getActivity(), entity.getCustomerTel(), true);
                dismissProgressDlg();
//                ContactsUtils.getINSTANCE().saveCustomerToContacts(DataApplication.getInstance().getApplicationContext(), entity.getCustomerName(), entity.getCustomerTel(), new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//                        //拨出
//                        ISystemUtil.makeCall(getActivity(), entity.getCustomerTel(), true);
//                        dismissProgressDlg();
//                    }
//                });
            }
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
        if (homeDialingPresenter!=null) {
            homeDialingPresenter.stopDialingByGroup();//出错停止
        }

    }

    @Override
    public void showDialingFinished(String msg) {
        String dialingType = ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIALING_TYPE_KEY, "").toString();
        if (homeDialingPresenter != null) {
            homeDialingPresenter.stopDialingByGroup();//取号完成 结束
            homeDialingPresenter.resetPreCustomerInfoWhenFinished();
        } else {
            ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);//TODO 手动停止群呼
            ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.DIALING_TYPE_KEY, "");//停止群拨，置空拨号类型
            CustomerLocalDataSource.getInstance().setPreCustomer(null);
        }
        //发送切换名单源消息
        EventBusValues values = new EventBusValues();
        values.setWhat(0x666666);
        EventBus.getDefault().post(values);
    }

    @Deprecated
    private void dialingSingle() {
        if (!isRecordPermissible) {
            ToastUtil.showToast(getActivity(), "录音权限被禁止！请在权限管理中允许录音权限");
            return;
        }
        if (!isCallPhonePermissible) {
            ToastUtil.showToast(getActivity(), "拨号权限被禁止！请在权限管理中允许录音权限");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainUserEvent(EventBusValues values) {
        Log.i(TAG, "onMainUserEvent: "+values.getWhat()+" ,object="+values.getObject());
        //只需要判断当前是否处于群拨，并且未手动结束，才接受消息处理
        String dialingType = ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIALING_TYPE_KEY, "").toString();
        switch (values.getWhat()) {
            case 0x10101://客户信息保存通知
                if (TextUtils.equals(dialingType,Constant.DIALING_TYPE_BY_GROUP)) {
                    Object obj = values.getObject();
                    boolean isResume = obj != null ? (boolean) values.getObject() : false;
                    if (isResume) {
                        //TODO 保存成功后，在群拨状态下要继续拨号 直接在挂断监听那里执行重拨操作，这里只处理异常停止
//                        if (homeDialingPresenter != null) {
//                            homeDialingPresenter.checkIsDialingGroupUnStoppedAndDialingOutNotRefreshUI();
//                        }
                        ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, false);
                    } else {
                        if (homeDialingPresenter!=null) {
                            homeDialingPresenter.stopDialingByGroup();//TODO 保存失败，出错，暂停群拨
                        }
                        ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);
                    }
                }
                break;
            case 0x10102:
                //通话结束通知
                ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, false);//结束消息
                if (TextUtils.equals(dialingType,Constant.DIALING_TYPE_BY_GROUP)) {
                    //TODO 群呼通话被挂断之前离开了详情页，没有接受到msg，也要继续拨出
                    //但如果还是停留在详情页面，这里不做处理，等待通话挂断消息被详情页面接收并且保存处理，再根据0x10101消息判断是否继续群拨
                    // TODO: 06/06/2017 这里还要判断是否处在一客户详情界面
                    ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, false);
                    homeDialingPresenter.checkIsDialingGroupUnStoppedAndDialingOutNotRefreshUI();
                    boolean isInCustomerDetailUI=ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_IN_CUSTOMER_DETAIL_UI, false);
                }else{
                    //点拨挂断电话后，置空拨号类型
                    ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.DIALING_TYPE_KEY, "");
                }
                //upload record files
                if (getActivity()!=null) {
                    Intent intent = new Intent(getActivity(),UploadService.class);
                    getActivity().startService(intent);
                }
                break;
        }
    }
}
