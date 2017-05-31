package com.xiaomai.telemarket.appCheck;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.net.retrofit.RetrofitCallback;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.api.XiaomaiRetrofitManager;
import com.xiaomai.telemarket.appCheck.data.VersionEntity;

import java.util.List;

import retrofit2.Call;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/31 17:59
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class AppCheckReto {

    private static AppCheckReto instance;

    public static AppCheckReto getInstance() {
        if (instance == null) {
            instance = new AppCheckReto();
        }
        return instance;
    }

    public void appCheck(final RemetoRepoCallback<VersionEntity> callback){
        Call<Responese<List<VersionEntity>>> call= XiaomaiRetrofitManager.getAPIService().checkAppVersion();
        call.enqueue(new RetrofitCallback<Responese<List<VersionEntity>>>() {
            @Override
            public void onSuccess(Responese<List<VersionEntity>> data) {
                if (data.getCode()==211&&data.getData()!=null&&data.getData().size()>0){
                    callback.onSuccess(data.getData().get(0));
                }else {
                    callback.onFailure(data.getCode(),data.getMsg());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code,msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                callback.onThrowable(t);
            }

            @Override
            public void onUnauthorized() {
                callback.onUnauthorized();
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }
}
