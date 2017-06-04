package com.xiaomai.telemarket.module.home.dial;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.home.dial.data.repo.CustomerPhoneNumberRemoteRepo;

import java.util.List;

/**
 * @description
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 30/05/2017 12:53 PM
 **/
public class HomeDialingPresenter implements HomeDialingContract.Presenter {

    private HomeDialingContract.View mView;
    private CustomerPhoneNumberRemoteRepo mRepo;

    private CusrometListEntity mPreCustomerEnity;
    private boolean isDialingGroupFinished;//群呼是否结束

q    public HomeDialingPresenter(HomeDialingContract.View mView) {
        this.mView = mView;
        this.mRepo = CustomerPhoneNumberRemoteRepo.getInstance();
    }

    @Override
    public void startDialingBySingle() {
        startDialing(true);
    }

    @Override
    public void startDialingByGroup() {
        startDialing(false);
        mView.showDialingByGroupStarted();
    }

    @Override
    public void stopDialingByGroup() {
        ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);
        mRepo.cancelRequest();
        mView.showDialingStopped();
    }

    @Override
    public void checkIsDialingGroupUnStoppedAndDialingOut() {
        isDialingGroupFinished = getIsDialingGroupStoped();
        boolean isDialingOffHook=(boolean) ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, false);
        if (!isDialingGroupFinished && !isDialingOffHook) {
            startDialingByGroup();//检查群拨状态未结束并且上次通话已结束，则继续拨出
        }
        mView.showIsDialingGroupUnStopped(isDialingGroupFinished);
    }

    /**
     * 开始拨号，拨出前要先删除上一条拨号临时记录
     * @param isDialingGroupFinished 群呼是否结束 点拨：true ，群拨：false
     */
    private void startDialing(final boolean isDialingGroupFinished){
        //设置群呼是否结束
        ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, isDialingGroupFinished);
        if (mPreCustomerEnity!=null) {
            mRepo.deleteFromList(mPreCustomerEnity.getID(), new RemetoRepoCallback<Void>() {
                @Override
                public void onSuccess(Void data) {
                    startRequestNumber(isDialingGroupFinished);
                }

                @Override
                public void onFailure(int code, String msg) {
                    // TODO: 30/05/2017 删除记录失败 暂时不做处理，删除接口后台均返回成功
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
            startRequestNumber(isDialingGroupFinished);
        }
    }

    /**
     * 开始从服务器请求号码
     * @param isDialingGroupFinished 群呼是否结束 点拨：true ，群拨：false
     */
    private void startRequestNumber(final boolean isDialingGroupFinished){
        if (getDialingNumberSource()== Constant.DIAL_NUMBER_CODE_PRIVATE) {
            //私有库
            mRepo.requestPhoneNumberFromPrivate(mPreCustomerEnity!=null?mPreCustomerEnity.getFollowDate():"", new RemetoRepoCallback<List<CusrometListEntity>>() {
                @Override
                public void onSuccess(List<CusrometListEntity> data) {
                    if (data!=null&&data.size()>0) {
                        mPreCustomerEnity = data.get(0);
                        mView.showDialingOutStarted(mPreCustomerEnity);
                    }else{
                        mView.showRequestNumberFailed("数据异常！");
                    }
                }

                @Override
                public void onFailure(int code, String msg) {
                    if (code == Constant.RESPONSE_CODE_DIALING_FINISH) {
                        ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);
                        mView.showDialingFinished(msg);//所有号码请求完成
                    }
                    else {
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

                }
            });
        }else if (getDialingNumberSource()== Constant.DIAL_NUMBER_CODE_PUBLIC) {
            //公共库
            mRepo.requestPhoneNumberFromPublic(new RemetoRepoCallback<List<CusrometListEntity>>() {
                @Override
                public void onSuccess(List<CusrometListEntity> data) {
                    if (data!=null&&data.size()>0) {
                        mPreCustomerEnity = data.get(0);
                        mView.showDialingOutStarted(mPreCustomerEnity);
                    }else{
                        mView.showRequestNumberFailed("数据异常！");
                    }
                }

                @Override
                public void onFailure(int code, String msg) {
                    if (code == Constant.RESPONSE_CODE_DIALING_FINISH) {
                        ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);
                        mView.showDialingFinished(msg);//所有号码请求完成
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
                }
            });
        }
    }

    public boolean getIsDialingGroupStoped(){
        return (boolean) ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);
    }

    private int getDialingNumberSource(){
        return Integer.valueOf(ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PRIVATE) + "");
    }
}
