package com.xiaomai.telemarket;

import android.util.Log;

import com.google.gson.Gson;
import com.jinggan.library.base.BaseApplication;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.account.data.UserInfoEntity;
import com.xiaomai.telemarket.service.PhoneCallStateService;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/16$ 下午9:28$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class DataApplication extends BaseApplication implements Thread.UncaughtExceptionHandler{

    private static final String TAG = "DataApplication";
    private UserInfoEntity userInfoEntity;

    private static DataApplication instance;
    
    public static DataApplication getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
         /*初始化Bugly*/
        CrashReport.initCrashReport(getApplicationContext(), "05c5820e16", false);
        instance=this;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            PhoneCallStateService.StopService(getApplicationContext());
        }catch (Exception ex){
            Log.i(TAG, e.getMessage());
        }
    }

    /**
     *用户信息
     * 
     *author: hezhiWu
     *created at 2017/5/16 下午9:35
     */
    public UserInfoEntity getUserInfoEntity(){
        if (userInfoEntity==null){
            userInfoEntity=new Gson().fromJson(ISharedPreferencesUtils.getValue(getInstance(), Constant.USERINFO_KEY,"").toString(),UserInfoEntity.class);
        }
        return userInfoEntity;
    }
}
