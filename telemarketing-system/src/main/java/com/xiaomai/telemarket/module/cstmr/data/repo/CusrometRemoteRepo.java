package com.xiaomai.telemarket.module.cstmr.data.repo;

import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.net.retrofit.RetrofitCallback;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.api.XiaomaiRetrofitManager;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/17 21:19
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometRemoteRepo implements BaseDataSourse {

    private Call<Responese<List<CusrometListEntity>>> listCusrometListCall;

    private static CusrometRemoteRepo instance;

    public static CusrometRemoteRepo getInstance() {
        if (instance == null) {
            instance = new CusrometRemoteRepo();
        }
        return instance;
    }

    /**
     * 获取客户列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/17 21:25
     *
     * @param pageIndex
     * @param callback
     */
    public void requestCusrometLists(int pageIndex, JSONObject filter, final RemetoRepoCallback<List<CusrometListEntity>> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject pageIndexObj = new JSONObject();
            pageIndexObj.put("PageIndex", pageIndex);

            jsonObject.put("pageparamer", pageIndexObj);
            jsonObject.put("filter", filter);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listCusrometListCall = XiaomaiRetrofitManager.getAPIService().cusrometLists(body);
        listCusrometListCall.enqueue(new RetrofitCallback<Responese<List<CusrometListEntity>>>() {
            @Override
            public void onSuccess(Responese<List<CusrometListEntity>> data) {
                if (data.getData() != null && data.getData().size() >= 0) {
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
        if (listCusrometListCall != null && !listCusrometListCall.isCanceled()) {
            listCusrometListCall.cancel();
        }
    }
}
