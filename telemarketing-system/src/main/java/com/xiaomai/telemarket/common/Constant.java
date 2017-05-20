package com.xiaomai.telemarket.common;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/16$ 下午8:54$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class Constant {

    public static final String ACCOUNT_KEY = "account";
    public static final String PASSWORD_KEY = "password";
    public static final String USERINFO_KEY = "userInfo";

    /**
     * 意向状态
     * <p>
     * author: hezhiWu
     * created at 2017/5/17 22:00
     */
    public enum Description {
        NoInterested(1), YesInterested(2);
        private int value;

        public int getValue() {
            return value;
        }

        Description(int value) {
            this.value = value;
        }
    }
}
