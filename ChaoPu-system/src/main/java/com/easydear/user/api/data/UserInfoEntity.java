package com.easydear.user.api.data;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/16$ 下午4:49$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class UserInfoEntity {
    private String UserNo;
    private String Imagery;
    private String NickName;
    private String Mobile;

    public String getUserNo() {
        return UserNo;
    }

    public void setUserNo(String UserNo) {
        this.UserNo = UserNo;
    }

    public String getImagery() {
        return Imagery;
    }

    public void setImagery(String Imagery) {
        this.Imagery = Imagery;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }
}
