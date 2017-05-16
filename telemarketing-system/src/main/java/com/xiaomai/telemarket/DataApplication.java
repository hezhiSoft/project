package com.xiaomai.telemarket;

import com.google.gson.Gson;
import com.jinggan.library.base.BaseApplication;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.account.data.UserInfoEntity;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/16$ 下午9:28$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class DataApplication extends BaseApplication {

    private UserInfoEntity userInfoEntity;
    
    private static DataApplication instance;
    
    public static DataApplication getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
    
    /**
     *用户信息
     * 
     *author: hezhiWu
     *created at 2017/5/16 下午9:30
     */
    public UserInfoEntity getUserInfoEntity(){
        if (userInfoEntity==null){
            userInfoEntity=new Gson().fromJson(ISharedPreferencesUtils.getValue(getInstance(), Constant.USERINFO_KEY,"").toString(),UserInfoEntity.class);
        }
        return userInfoEntity;
    }
}
