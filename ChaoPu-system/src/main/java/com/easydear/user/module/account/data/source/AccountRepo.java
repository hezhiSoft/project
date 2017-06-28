package com.easydear.user.module.account.data.source;

import com.easydear.user.api.ChaoPuRetrofitManamer;
import com.easydear.user.api.ResponseEntity;
import com.easydear.user.module.account.data.UserInfoEntity;
import com.easydear.user.api.RetrofitManager;
import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.net.retrofit.RetrofitCallbackV2;

import retrofit2.Call;

import static android.R.attr.data;


/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/16$ 下午4:58$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class AccountRepo implements BaseDataSourse {

    private Call<ResponseEntity<UserInfoEntity>> loginCall;
    private Call<ResponseEntity<UserInfoEntity>> registCall;
    private Call<ResponseEntity<String>> validateCodeCall;

    private static AccountRepo instance;

    public static AccountRepo getInstance() {
        if (instance == null) {
            instance = new AccountRepo();
        }
        return instance;
    }

    /**
     * 登录
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 20:53
     */
    public void login(String account, String pwd, final RemetoRepoCallbackV2<UserInfoEntity> callback) {
        callback.onReqStart();
        loginCall = ChaoPuRetrofitManamer.getAPIService().login(account, pwd);
        loginCall = RetrofitManager.getInstance().getService().login(account, pwd);
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


    /**
     * 注册
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 21:32
     */
    public void regist(String mobile, String password, String valiCode, final RemetoRepoCallbackV2<UserInfoEntity> callback) {
        callback.onReqStart();
        registCall = ChaoPuRetrofitManamer.getService().regist(mobile, password, valiCode);
        registCall.enqueue(new RetrofitCallbackV2<ResponseEntity<UserInfoEntity>>() {
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

    /**
     * 发送验证码
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 20:52
     */
    public void sendMobileValidateCode(String mobile, final RemetoRepoCallbackV2<String> callback) {
        callback.onReqStart();
        validateCodeCall = ChaoPuRetrofitManamer.getService().sendMobileValidateCode(mobile);
        validateCodeCall.enqueue(new RetrofitCallbackV2<ResponseEntity<String>>() {
            @Override
            public void onSuccess(ResponseEntity<String> data) {
                if (data.getCode() == 200) {
                    callback.onSuccess("验证已发送");
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
        if (loginCall != null && !loginCall.isCanceled()) {
            loginCall.cancel();
        }

        if (validateCodeCall != null && !validateCodeCall.isCanceled()) {
            validateCodeCall.cancel();
        }
    }
}
