package com.xiaomai.telemarket.module.home.dial;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.home.dial.data.source.local.CustomerLocalDataSource;
import com.xiaomai.telemarket.module.home.dial.data.source.remote.CustomerPhoneNumberRemoteRepo;
import com.xiaomai.telemarket.utils.PhoneRecordUtil;

import java.util.List;

import static com.jinggan.library.utils.ISharedPreferencesUtils.setValue;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description
 * @createtime 30/05/2017 12:53 PM
 **/
public class HomeDialingPresenter implements HomeDialingContract.Presenter {
    private static final String TAG = "HomeDialingPresenter";

    private HomeDialingContract.View mView;
    private CustomerPhoneNumberRemoteRepo mRepo;
    private CustomerLocalDataSource mLocalCustomerDataSource;

    private boolean isDialingGroupFinished;//群呼是否结束

    public HomeDialingPresenter(HomeDialingContract.View mView) {
        this.mView = mView;
        this.mRepo = CustomerPhoneNumberRemoteRepo.getInstance();
        this.mLocalCustomerDataSource = CustomerLocalDataSource.getInstance();
    }

    @Override
    public void startDialingBySingle() {
        startDialing(true);
        setValue(DataApplication.getInstance().getApplicationContext(), Constant.DIALING_TYPE_KEY, Constant.DIALING_TYPE_BY_SINGLE);
    }

    @Override
    public void startDialingByGroup() {
        startDialing(false);
        mView.showDialingByGroupStarted();
        setValue(DataApplication.getInstance().getApplicationContext(), Constant.DIALING_TYPE_KEY, Constant.DIALING_TYPE_BY_GROUP);
    }

    @Override
    public void stopDialingByGroup() {
        setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);//TODO 手动停止群呼
        setValue(DataApplication.getInstance().getApplicationContext(), Constant.DIALING_TYPE_KEY, "");//停止群拨，置空拨号类型
        mRepo.cancelRequest();
        mView.showDialingStopped();
    }

    @Override
    public void checkIsDialingGroupUnStoppedAndDialingOut() {
        checkIsDialingGroupUnStoppedAndDialingOutNotRefreshUI();
        mView.showIsDialingGroupUnStopped(isDialingGroupFinished);
    }

    public void checkIsDialingGroupUnStoppedAndDialingOutNotRefreshUI() {
        isDialingGroupFinished = getIsDialingGroupStoped();//群拨是否结束
        boolean isDialingOffHook = (boolean) ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, false);//是否正在通话中
        String dialingType = ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIALING_TYPE_KEY, "").toString();//拨号类型
        //判断是否正在录音 辅助判断双卡取消拨号后无法再进行拨号的问题
        if (!PhoneRecordUtil.getINSTANCE().isStarted()) {
            isDialingOffHook=false;
            ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, false);//检测未在通话录音状态 即不再通话中
        }
        if (!isDialingGroupFinished && !isDialingOffHook && TextUtils.equals(dialingType, Constant.DIALING_TYPE_BY_GROUP)) {
            startDialingByGroup();//检查群拨状态未结束并且上次通话已结束，则继续拨出
        }
    }

    /**
     * 开始拨号，拨出前要先删除上一条拨号临时记录
     *
     * @param isDialingGroupFinished 群呼是否结束 点拨：true ，群拨：false
     */
    private void startDialing(final boolean isDialingGroupFinished) {
        setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, isDialingGroupFinished);//TODO 手动开始或者重检测群呼状态后开始群呼
        //检测权限
        if (ContextCompat.checkSelfPermission(DataApplication.getInstance().getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(), "录音权限被禁止！请在权限管理中允许录音权限");
            return;
        }
        if (ContextCompat.checkSelfPermission(DataApplication.getInstance().getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(), "拨号权限被禁止！请在权限管理中允许录音权限");
            return;
        }
        boolean isDialingOffHook = (boolean) ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, false);
        if (!PhoneRecordUtil.getINSTANCE().isStarted()) {
            isDialingOffHook=false;//判断是否正在录音 辅助判断双卡取消拨号后无法再进行拨号的问题
            ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, false);//检测未在通话录音状态 即不再通话中
        }
        if (isDialingOffHook) {
            //正在通话中
            ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(), "正在通话中，请稍后！");
            return;
        }
        // TODO: 15/06/2017 start request
        mView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.showDialingRequestStarted();
            }
        });
        startRequestNumber(isDialingGroupFinished);//不再需要删除接口调用
//        CusrometListEntity mPreCustomerEnity = mLocalCustomerDataSource.getPreCustomer();
//        if (mPreCustomerEnity != null) {
//            mRepo.deleteFromList(mPreCustomerEnity.getID(), new RemetoRepoCallback<Void>() {
//                @Override
//                public void onSuccess(Void data) {
//                    startRequestNumber(isDialingGroupFinished);
//                }
//
//                @Override
//                public void onFailure(int code, String msg) {
//                    // TODO: 30/05/2017 删除记录失败 暂时不做处理，删除接口后台均返回成功
//                }
//
//                @Override
//                public void onThrowable(Throwable t) {
//
//                }
//
//                @Override
//                public void onUnauthorized() {
//
//                }
//
//                @Override
//                public void onFinish() {
//
//                }
//            });
//        } else {
//            startRequestNumber(isDialingGroupFinished);
//        }
    }

    /**
     * 开始从服务器请求号码
     *
     * @param isDialingGroupFinished 群呼是否结束 点拨：true ，群拨：false
     */
    private void startRequestNumber(final boolean isDialingGroupFinished) {
        CusrometListEntity mPreCustomerEnity = mLocalCustomerDataSource.getPreCustomer();
        if (getDialingNumberSource() == Constant.DIAL_NUMBER_CODE_PRIVATE) {
            //私有库
            // TODO: 08/06/2017 foloowDate 改为createdate
            mRepo.requestPhoneNumberFromPrivate(mPreCustomerEnity != null ? mPreCustomerEnity.getCreatedDate() : "", new RemetoRepoCallback<List<CusrometListEntity>>() {
                @Override
                public void onSuccess(List<CusrometListEntity> data) {
//                    requestFinished();//成功之后等拨出后再消失进度条
                    if (data != null && data.size() > 0) {
                        CusrometListEntity entity = data.get(0);
                        mLocalCustomerDataSource.setPreCustomer(entity);
                        mView.showDialingOutStarted(entity);
                    } else {
                        mView.showRequestNumberFailed("数据异常！");
                    }
                }

                @Override
                public void onFailure(int code, String msg) {
                    requestFinished();
                    if (code == Constant.RESPONSE_CODE_DIALING_FINISH) {
                        setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);
                        mView.showDialingFinished(msg);//TODO 所有号码请求完成,停止群呼
                    } else {
                        mView.showRequestNumberFailed(msg);
                    }
                }

                @Override
                public void onThrowable(Throwable t) {
                    mView.showRequestNumberFailed("数据异常！");
                }

                @Override
                public void onUnauthorized() {
                    mView.showRequestNumberFailed("数据异常！");
                }

                @Override
                public void onFinish() {
                    requestFinished();
                }
            });
        } else if (getDialingNumberSource() == Constant.DIAL_NUMBER_CODE_PUBLIC) {
            //公共库
            mRepo.requestPhoneNumberFromPublic(new RemetoRepoCallback<List<CusrometListEntity>>() {
                @Override
                public void onSuccess(List<CusrometListEntity> data) {
//                    requestFinished();//成功之后等拨出后再消失进度条
                    if (data != null && data.size() > 0) {
                        CusrometListEntity entity = data.get(0);
                        mLocalCustomerDataSource.setPreCustomer(entity);
                        mView.showDialingOutStarted(entity);
                    } else {
                        mView.showRequestNumberFailed("数据异常！");
                    }
                }

                @Override
                public void onFailure(int code, String msg) {
                    requestFinished();
                    if (code == Constant.RESPONSE_CODE_DIALING_FINISH) {
                        setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);
                        mView.showDialingFinished(msg);//TODO 所有号码请求完成,停止群呼
                    } else {
                        mView.showRequestNumberFailed(msg);
                    }
                }

                @Override
                public void onThrowable(Throwable t) {
                    mView.showRequestNumberFailed("数据异常！");
                }

                @Override
                public void onUnauthorized() {
                    mView.showRequestNumberFailed("数据异常！");
                }

                @Override
                public void onFinish() {
                    requestFinished();
                }
            });
        }
    }

    public void requestFinished() {
        if (mView.getActivity()!=null) {
            mView.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mView.showDialingRequestFinished();
                }
            });
        }
    }

    public boolean getIsDialingGroupStoped() {
        return (boolean) ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);
    }

    private int getDialingNumberSource() {
        //默认公共库
        return Integer.valueOf(ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PUBLIC) + "");
    }

    /**
     * 重置客户信息
     */
    public void resetPreCustomerInfoWhenFinished() {
        mLocalCustomerDataSource.setPreCustomer(null);
    }

}
