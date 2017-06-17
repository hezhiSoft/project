package com.xiaomai.telemarket.module.function.data.source;

import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.net.retrofit.RetrofitCallback;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.api.XiaomaiRetrofitManager;
import com.xiaomai.telemarket.module.function.data.CallOutDepStaticsEntity;
import com.xiaomai.telemarket.module.function.data.CallOutStaticsEntity;
import com.xiaomai.telemarket.module.function.data.StatisticsByMonthParam;
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
public class CallOutRemoteRepo implements BaseDataSourse {

    private Call<Responese<List<CallOutStaticsEntity>>> userStaticsCall;
    private Call<Responese<List<CallOutDepStaticsEntity>>> depStatisticsCall;

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

    /**
     * 部门按月统计
     * @param param {"DeptId":"252D62CE-63D4-4E9B-8328-A722011CA3F7","Year":2017,"Type":"call"}
     * @param callback
     */
    public void queryStatisticByDep(StatisticsByMonthParam param, final RemetoRepoCallback<List<CallOutDepStaticsEntity>> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            if (param != null) {
//                jsonObject.put("DeptId", param.getDeptId());// TODO: 14/06/2017 暂时不传部门参数
                jsonObject.put("Year", param.getYear());
                jsonObject.put("Type", param.getType());
                body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        depStatisticsCall = XiaomaiRetrofitManager.getAPIService().queryStaticsByMonth(body);
        depStatisticsCall.enqueue(new RetrofitCallback<Responese<List<CallOutDepStaticsEntity>>>() {
            @Override
            public void onSuccess(Responese<List<CallOutDepStaticsEntity>> data) {
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

    @Override
    public void cancelRequest() {
        if (userStaticsCall!=null&&!userStaticsCall.isCanceled()) {
            userStaticsCall.cancel();
        }if (depStatisticsCall!=null&&!depStatisticsCall.isCanceled()) {
            depStatisticsCall.cancel();
        }
    }
}
