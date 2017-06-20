package com.easydear.user.api.data.source;

import com.easydear.user.api.ChaoPuRetrofitManamer;
import com.easydear.user.api.ResponseEntity;
import com.easydear.user.api.data.UserInfoEntity;
import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.net.retrofit.RetrofitCallbackV2;

import retrofit2.Call;


/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/16$ 下午4:58$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class AccountRepo implements BaseDataSourse {

    private Call<ResponseEntity<UserInfoEntity>> loginCall;

    private static AccountRepo instance;

    public static AccountRepo getInstance() {
        if (instance == null) {
            instance = new AccountRepo();
        }
        return instance;
    }

    public void login(String account, String pwd, final RemetoRepoCallbackV2<UserInfoEntity> callback) {
        callback.onReqStart();
        loginCall = ChaoPuRetrofitManamer.getAPIService().login(account, pwd);
        loginCall.enqueue(new RetrofitCallbackV2<ResponseEntity<UserInfoEntity>>() {
            @Override
            public void onSuccess(ResponseEntity<UserInfoEntity> data) {
                if (data.getCode() == 200) {
                    callback.onSuccess(data.getData());
                } else {
                    callback.onFailure(data.getCode(), data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    @Override
    public void cancelRequest() {
        if (loginCall!=null&&!loginCall.isCanceled()){
            loginCall.cancel();
        }
    }
}
