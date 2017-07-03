package com.easydear.user.common;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/16$ 下午5:12$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class Constant {

    public static final String ACCOUNT_KEY = "account";
    public static final String PASSWORD_KEY = "password";
    public static final String USERINFO_KEY = "userInfo";
    public static final String TOKEN_KEN="token";

    public static final int DEFAULT_LOAD_SIZE = 10;

    public interface EventValue {
        int SET_SHOP_ACTIVITY = 5001;
        int SET_BUSINESS_DETAIL = 5002;
        int SET_MEMBER_INFO = 5003;
    }
}
