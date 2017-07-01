package com.easydear.user.module.dynamic.data.soruce;


import com.easydear.user.api.ChaoPuRetrofitManamer;
import com.easydear.user.api.ResponseEntity;
import com.easydear.user.api.RetrofitManager;
import com.easydear.user.module.dynamic.data.DynamicDetailsEntity;
import com.easydear.user.module.dynamic.data.DynamicEntity;
import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.net.retrofit.RetrofitCallbackV2;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/16$ 下午3:09$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class DynamicRepo implements BaseDataSourse {

    private Call<ResponseEntity<List<DynamicEntity>>> dynamicsCall;
    private Call<ResponseEntity<DynamicDetailsEntity>> detailsCall;

    private static DynamicRepo instance;

    public static DynamicRepo getInstance() {
        if (instance == null)
            instance = new DynamicRepo();
        return instance;
    }

    /**
     * 查询动态软文
     * <p>
     * author: hezhiWuv
     * created at 2017/6/16 下午3:21
     */
    public void queryDynamics(int pageSize, int pageCount, String keywords, String type, String province, String city, String area, final RemetoRepoCallbackV2<List<DynamicEntity>> callback) {
        callback.onReqStart();
        String url = "neweasydear-app/article/listByKey??pageSize=" + pageSize + "&pageCount=" + pageCount + "&keywords=" + keywords + "&type=" + type + "&province=" + province + "&city=" + city + "&area=" + area;
        dynamicsCall = ChaoPuRetrofitManamer.getAPIService().queryDynamics(url);
        dynamicsCall = RetrofitManager.getInstance().getService().queryDynamics(url);
        dynamicsCall.enqueue(new RetrofitCallbackV2<ResponseEntity<List<DynamicEntity>>>() {
            @Override
            public void onSuccess(ResponseEntity<List<DynamicEntity>> data) {
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
     * 软文详情
     * <p>
     * author: hezhiWu
     * created at 2017/6/29 20:54
     */
    public void queryDynamic(int articleId, final RemetoRepoCallbackV2<DynamicDetailsEntity> callback) {
        callback.onFinish();
        detailsCall = ChaoPuRetrofitManamer.getAPIService().queryDynamic(articleId);
        detailsCall.enqueue(new RetrofitCallbackV2<ResponseEntity<DynamicDetailsEntity>>() {
            @Override
            public void onSuccess(ResponseEntity<DynamicDetailsEntity> data) {
                if (data.getCode()==200){
                    callback.onSuccess(data.getData());
                }else {
                    callback.onFailure(data.getCode(),data.getMessage());
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
        if (dynamicsCall != null && !dynamicsCall.isCanceled()) {
            dynamicsCall.cancel();
        }
    }
}
