package com.easydear.user;

import android.app.Application;

import com.amap.api.location.AMapLocation;
import com.easydear.user.module.account.data.UserInfoEntity;
import com.easydear.user.common.Constant;
import com.easydear.user.common.LocationManager;
import com.google.gson.Gson;
import com.jinggan.library.utils.ISharedPreferencesUtils;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/9 22:07
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DataApplication extends Application implements LocationManager.LocationCallBack{

    private UserInfoEntity userInfoEntity;
    private AMapLocation aMapLocation;


    private static DataApplication instance;

    public static DataApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        LocationManager.getInstance().startLocation(this);
    }

    public UserInfoEntity getUserInfoEntity() {
        if (userInfoEntity==null){
            userInfoEntity=new Gson().fromJson(ISharedPreferencesUtils.getValue(this,Constant.USERINFO_KEY,"").toString(),UserInfoEntity.class);
        }
        return userInfoEntity;
    }

    public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
        ISharedPreferencesUtils.setValue(this, Constant.USERINFO_KEY,new Gson().toJson(userInfoEntity));
        this.userInfoEntity = userInfoEntity;
    }

    public static void setInstance(DataApplication instance) {
        DataApplication.instance = instance;
    }

    public AMapLocation getaMapLocation() {
        return aMapLocation;
    }

    @Override
    public void onLocation(AMapLocation location) {
        this.aMapLocation=location;
    }
}
