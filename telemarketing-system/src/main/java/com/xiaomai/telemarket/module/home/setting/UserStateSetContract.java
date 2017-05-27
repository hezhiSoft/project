package com.xiaomai.telemarket.module.home.setting;

/**
 * @description 用户状态设置
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 19/05/2017 1:05 AM
 **/
public class UserStateSetContract {

    interface View{
        void showChangeUserStateStart();
        void showChangeUserStateSuccess(int status);
        void showChangeUserStateFailed(String msg);
    }

    interface Presenter{
        void changeUserState(int status);
    }
}
