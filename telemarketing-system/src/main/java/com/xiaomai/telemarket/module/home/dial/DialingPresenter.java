package com.xiaomai.telemarket.module.home.dial;


import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.home.dial.data.repo.CustomerPhoneNumberRemoteRepo;

import java.util.List;

/**
 * @description 
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 27/05/2017 7:23 PM
 **/
public class DialingPresenter implements DialingContract.Presenter {

    private DialingContract.View mView;
    private CustomerPhoneNumberRemoteRepo mRepo;

    public DialingPresenter(DialingContract.View view) {
        this.mView = view;
        this.mRepo = CustomerPhoneNumberRemoteRepo.getInstance();
    }

    @Override
    public void requestNumberFromPublic() {
        mView.showRequestNumberStar();
        mRepo.requestPhoneNumberFromPublic(new RemetoRepoCallback<List<CusrometListEntity>>() {
            @Override
            public void onSuccess(List<CusrometListEntity> data) {
                if (data != null && data.size() > 0) {
                    mView.showRequestNumberSuccess(data.get(0));
                } else {
                    mView.showRequestNumberFailed("failed！");
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.showRequestNumberFailed(msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                mView.showRequestNumberFailed(t != null ? t.getMessage() : "failed!");
            }

            @Override
            public void onUnauthorized() {
                mView.showRequestNumberFailed("onUnauthorized!");
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void requestNumberFromPrivate(String preid) {
        mRepo.requestPhoneNumberFromPrivate(preid, new RemetoRepoCallback<List<CusrometListEntity>>() {
            @Override
            public void onSuccess(List<CusrometListEntity> data) {
                if (data != null && data.size() > 0) {
                    mView.showRequestNumberSuccess(data.get(0));
                } else {
                    mView.showRequestNumberFailed("failed！");
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.showRequestNumberFailed(msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                mView.showRequestNumberFailed(t != null ? t.getMessage() : "failed!");
            }

            @Override
            public void onUnauthorized() {
                mView.showRequestNumberFailed("onUnauthorized!");
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void stopRequest() {
        mRepo.cancelRequest();
        mView.showRequestNumberStoped();
    }
}
