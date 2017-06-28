package com.easydear.user.module.mine.data.source;

import com.easydear.user.DataApplication;
import com.easydear.user.api.ChaoPuRetrofitManamer;
import com.easydear.user.api.ResponseEntity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.net.retrofit.RetrofitCallbackV2;

import retrofit2.Call;

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
        Call<ResponseEntity<String>> call = ChaoPuRetrofitManamer.getService().getCardSize(DataApplication.getInstance().getUserInfoEntity().getUserNo());
        call.enqueue(new RetrofitCallbackV2<ResponseEntity<String>>() {
            @Override
            public void onSuccess(ResponseEntity<String> data) {
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
     * 获取商家数量
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 22:22
     */
    public void getBussinessSize(final RemetoRepoCallbackV2<String> callback) {
        callback.onReqStart();
        Call<ResponseEntity<String>> call = ChaoPuRetrofitManamer.getService().getBussinessSize(DataApplication.getInstance().getUserInfoEntity().getUserNo());
        call.enqueue(new RetrofitCallbackV2<ResponseEntity<String>>() {
            @Override
            public void onSuccess(ResponseEntity<String> data) {
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
}
