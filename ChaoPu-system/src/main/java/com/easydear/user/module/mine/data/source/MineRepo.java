package com.easydear.user.module.mine.data.source;

import com.easydear.user.DataApplication;
import com.easydear.user.api.ChaoPuRetrofitManamer;
import com.easydear.user.api.ResponseEntity;
import com.easydear.user.module.business.data.BusinessEntity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.net.retrofit.RetrofitCallbackV2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Query;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/28 22:02
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class MineRepo {

    private static MineRepo instance;

    public static MineRepo getInstance() {
        if (instance == null) {
            instance = new MineRepo();
        }
        return instance;
    }

    /**
     * 获取卡卷数量
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 22:10
     */
    public void getCardSize(final RemetoRepoCallbackV2<String> callback) {
        callback.onReqStart();
        Call<ResponseEntity<Object>> call = ChaoPuRetrofitManamer.getService().getCardSize();
        call.enqueue(new RetrofitCallbackV2<ResponseEntity<Object>>() {
            @Override
            public void onSuccess(ResponseEntity<Object> data) {
                if (data.getCode() == 200) {
                    callback.onSuccess(data.getData().toString());
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
     * 获取商家数量
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 22:22
     */
    public void getBussinessSize(final RemetoRepoCallbackV2<String> callback) {
        callback.onReqStart();
        Call<ResponseEntity<Object>> call = ChaoPuRetrofitManamer.getService().getBussinessSize();
        call.enqueue(new RetrofitCallbackV2<ResponseEntity<Object>>() {
            @Override
            public void onSuccess(ResponseEntity<Object> data) {
                if (data.getCode() == 200) {
                    callback.onSuccess(data.getData().toString());
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

    public void queryUserBusiness(final RemetoRepoCallbackV2<List<BusinessEntity>> callback){
        callback.onReqStart();

        final Call<ResponseEntity<List<BusinessEntity>>> call=ChaoPuRetrofitManamer.getService().queryUserBusiness();
        call.enqueue(new RetrofitCallbackV2<ResponseEntity<List<BusinessEntity>>>() {
            @Override
            public void onSuccess(ResponseEntity<List<BusinessEntity>> data) {
                if (data.getCode()==200){
                    callback.onSuccess(data.getData());
                }else {
                    callback.onFailure(data.getCode(),data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code,msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    public void addFeedback(String content, final RemetoRepoCallbackV2<Void> callback) {
        callback.onReqStart();
        Call<ResponseEntity<Void>> call=ChaoPuRetrofitManamer.getService().addFeedback(content);
        call.enqueue(new RetrofitCallbackV2<ResponseEntity<Void>>() {
            @Override
            public void onSuccess(ResponseEntity<Void> data) {
                if (data.getCode()==200){
                    callback.onSuccess(data.getData());
                }else {
                    callback.onFailure(data.getCode(),data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code,msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }
}
