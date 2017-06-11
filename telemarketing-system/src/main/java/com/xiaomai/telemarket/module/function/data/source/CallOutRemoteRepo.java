package com.xiaomai.telemarket.module.function.data.source;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.net.retrofit.RetrofitCallback;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.api.XiaomaiRetrofitManager;
import com.xiaomai.telemarket.module.function.data.CallOutStaticsEntity;
import com.xiaomai.telemarket.module.function.data.StatisticByUserParam;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description
 * @createtime 11/06/2017 9:46 PM
 **/
public class CallOutRemoteRepo {

    private Call<Responese<List<CallOutStaticsEntity>>> userStaticsCall;

    private static CallOutRemoteRepo instance;

    public static CallOutRemoteRepo getInstance() {
        if (instance == null) {
            synchronized (CallOutRemoteRepo.class) {
                if (instance == null) {
                    instance = new CallOutRemoteRepo();
                }
            }
        }
        return instance;
    }

    private CallOutRemoteRepo() {}

    /**
     * 查询员工外呼统计
     * @param param
     * @param callback
     */
    public void queryStaticsByUser(StatisticByUserParam param, final RemetoRepoCallback<List<CallOutStaticsEntity>> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            if (param != null) {
                jsonObject.put("FromDate", param.getFromDate());
                jsonObject.put("ToDate", param.getToDate());
                jsonObject.put("Name", param.getName());
                //
                jsonObject.put("FromCall", param.getFromCall());
                jsonObject.put("ToCall", param.getToCall());
                //
                jsonObject.put("FromConnect", param.getFromConnect());
                jsonObject.put("ToConnect", param.getToConnect());
                //
                jsonObject.put("FromAppoint", param.getFromAppoint());
                jsonObject.put("ToAppoint", param.getToAppoint());
                //
                jsonObject.put("FromDuration", param.getFromDuration());
                jsonObject.put("ToDuration", param.getToDuration());
                body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        userStaticsCall = XiaomaiRetrofitManager.getAPIService().queryStaticsByUser(body);
        userStaticsCall.enqueue(new RetrofitCallback<Responese<List<CallOutStaticsEntity>>>() {
            @Override
            public void onSuccess(Responese<List<CallOutStaticsEntity>> data) {
                if (data.isSuccess() && data.getData() != null && data.getData().size() > 0) {
                    callback.onSuccess(data.getData());
                } else {
                    callback.onFailure(data.getCode(), data.getMsg());
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
