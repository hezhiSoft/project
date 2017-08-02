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
    public static final String TOKEN_KEN = "token";

    public static final int DEFAULT_LOAD_SIZE = 10;

    public static final int NOTICE_HOME_UPDATE_CITY = 101;  /*通知 HomeFragment 更新选择城市*/
    public static final int NOTICE_KEY_SEARCH_HOME = 102;  /*通知 HomeFragment 更新...*/
    public static final int NOTICE_KEY_SEARCH_DYNAMIC = 103;  /*通知 HomeFragment 更新...*/

    public static final int HOME_SELECT_CITY_REQUEST_CODE = 1001;
    public static final int HOME_SEARCH_KEY_REQUEST_CODE = 1002;

    public interface EventValue {
        int SET_SHOP_ACTIVITY = 5001;
        int SET_BUSINESS_DETAIL = 5002;
        int SET_MEMBER_INFO = 5003;
    }

    /**
     * SharedPreference Key
     */
    public static final String AMAP_LOCATION_CITY = "amap_location_city"; /*地图获取城市*/
    public static final String AMAP_LOCATION_ADCODE = "amap_location_adcode"; /*地图获取Address Code*/

    /**
     * Http code
     */
    public static final int HTTP_SUCESS_CODE = 200;/*Http请求成功返回码*/
    public static final int HTTP_PASSWORD_ERROR_CODE = 400;/*登录账号或密码失败*/

}
