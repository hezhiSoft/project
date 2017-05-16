package com.xiaomai.telemarket.module.account;

import com.xiaomai.telemarket.module.account.data.UserInfoEntity;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/16$ 下午8:24$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class AccountContract  {

    interface LoginView{
        void onLoginSuccess(UserInfoEntity entity);
        void onLoginFailure(int code,String msg);
    }

    interface Presenter{
        void login(String account,String password,LoginView loginView);
    }
}
