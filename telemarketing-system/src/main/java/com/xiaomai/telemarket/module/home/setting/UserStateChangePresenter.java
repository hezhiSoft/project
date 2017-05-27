package com.xiaomai.telemarket.module.home.setting;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.xiaomai.telemarket.module.home.setting.data.repo.UserStateRemoteRepo;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description
 * @createtime 19/05/2017 1:11 AM
 **/
public class UserStateChangePresenter implements UserStateSetContract.Presenter {

    private UserStateSetContract.View mView;
    private UserStateRemoteRepo mRepo;

    public UserStateChangePresenter(UserStateSetContract.View view) {
        this.mView = view;
        this.mRepo = UserStateRemoteRepo.getInstance();
    }

    @Override
    public void changeUserState(final int status) {
        mView.showChangeUserStateStart();
        mRepo.updateUserState(status,new  RemetoRepoCallback<Void>(){
            @Override
            public void onSuccess(Void data) {
                mView.showChangeUserStateSuccess(status);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.showChangeUserStateFailed(msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                mView.showChangeUserStateFailed("操作失败！"+t!=null?t.getMessage():"");
            }

            @Override
            public void onUnauthorized() {
                // TODO: 19/05/2017 是否提醒用户重新登录
                mView.showChangeUserStateFailed("操作失败！未授权");
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
