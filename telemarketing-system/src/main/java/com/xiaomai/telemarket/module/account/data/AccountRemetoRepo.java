package com.xiaomai.telemarket.module.account.data;

import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.net.retrofit.RetrofitCallback;
import com.jinggan.library.net.retrofit.RetrofitManager;
import com.xiaomai.telemarket.BuildConfig;
import com.xiaomai.telemarket.api.APIService;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.utils.MD5Util;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/15 21:19
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class AccountRemetoRepo implements BaseDataSourse {
    Call<Responese<UserInfoEntity>> loginCall;

    private static AccountRemetoRepo instance;

    public static AccountRemetoRepo getInstance() {
        if (instance == null) {
            instance = new AccountRemetoRepo();
        }
        return instance;
    }

    /**
     * 登录
     * <p>
     * author: hezhiWu
     * created at 2017/5/15 21:47
     */
    public void login(String account, String password, final RemetoRepoCallback<UserInfoEntity> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", account);
            jsonObject.put("password", MD5Util.string2MD5(password));
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loginCall = RetrofitManager.getService(BuildConfig.DOMAIN, APIService.class).login(body);
        loginCall.enqueue(new RetrofitCallback<Responese<UserInfoEntity>>() {
            @Override
            public void onSuccess(Responese<UserInfoEntity> data) {
                if (data.getCode() == 200) {
                    callback.onSuccess(data.getData());
                } else {
                    callback.onFailure(data.getCode(), data.getMsg());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code, msg);
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

    @Override
    public void cancelRequest() {
        if (loginCall != null && !loginCall.isCanceled()) {
            loginCall.cancel();
        }
    }
}
