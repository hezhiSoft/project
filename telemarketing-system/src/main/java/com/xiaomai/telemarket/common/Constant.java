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
    public static final String ISLOGIN_KEY = "is_login";
    public static final String USERINFO_KEY = "userInfo";
    public static final String USER_STATE_KEY = "userState";
    public static final String USER_STATE_NAME_KEY = "userStateName";

    /**setting*/
    public static final String USER_STATE_LIST="user_state_list";
    public static final String DIAL_NUMBER_SOURCE = "dial_number_source";

    /**dialing*/
    public static final String IS_DIALING_KEY= "is_dialing";//是否接通正在通话中
    public static final String IS_DIALING_GROUP_FINISHED = "is_dialing_group_finished";//群呼是否结束
    public static final String PRE_CUSTOMER_KEY = "pre_customer_key";//上一个拨号的用户信息
    public static final String IS_FROM_HOME_GROUP_DIALING = "is_from_group_dialing";//是否从群拨跳转到的客户信息界面
    public static final String DIALING_TYPE_KEY = "is_dial_by_group";//拨号类别 群呼1／点呼0 停止拨号后会置未空串
    public static final String IS_IN_CUSTOMER_DETAIL_UI = "is_in_customer_detail";//在查看或编辑客户信息界面CusrometDetailsActivity／
    public static final String NOT_SEND_DIALING_MSG = "not_send_dialing_msg";//客户详情中单独拨号不需要接受挂断监听

    public static final String DIALING_TYPE_BY_GROUP = "group";//拨号类别 群呼
    public static final String DIALING_TYPE_BY_SINGLE = "single";//拨号类别 点呼

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

    /**
     *用户状态枚举
     *
     *author: Yang Du <youngdu29@gmail.com>
     *created at 20/05/2017 9:24 PM
     */
    public enum UserState{
        INWORK(1),OFFWORK(6);

        private int value;

        public int getValue(){return value;}

        UserState(int value) {
            this.value = value;
        }
    }

    /**
     * 拨号来源：公共库
     */
    public static final int DIAL_NUMBER_CODE_PUBLIC=1;
    /**
     * 拨号来源：私有库
     */
    public static final int DIAL_NUMBER_CODE_PRIVATE=2;

    /**所有号码请求完成 状态码*/
    public static final int RESPONSE_CODE_DIALING_FINISH =411;
}
