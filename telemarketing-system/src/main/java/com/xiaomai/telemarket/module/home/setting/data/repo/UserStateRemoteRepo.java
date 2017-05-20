package com.xiaomai.telemarket.module.home.setting.data.repo;

import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.net.retrofit.RetrofitCallback;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.api.XiaomaiRetrofitManager;
import com.xiaomai.telemarket.module.home.setting.data.UserStateEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 用户状态获取
 * @createtime 20/05/2017 5:18 PM
 **/
public class UserStateRemoteRepo implements BaseDataSourse {

    private Call<Responese<List<UserStateEntity>>> userStateListCall;
    private Call<Responese<Void>> updataUserStateCall;

    private static UserStateRemoteRepo instance;

    public static UserStateRemoteRepo getInstance() {
        if (instance == null) {
            instance = new UserStateRemoteRepo();
        }
        return instance;
    }

    /**
     * 获取用户当前状态
     * @param callback
     */
//    public void requestUserCurrentState(final RemetoRepoCallback<String> callback){
//        userStateCall = XiaomaiRetrofitManager.getAPIService().requestUserState();
//        userStateCall.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.code() == 200) {
//                    callback.onSuccess(response.body());
//                } else {
//                    callback.onFailure(response.code(), "failure");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                callback.onFailure(405, "failure");
//            }
//        });
//    }

    /**
     * 获取用户状态枚举
     *
     * @param callback
     */
    public void requestUserStateLists(final RemetoRepoCallback<List<UserStateEntity>> callback) {
        userStateListCall = XiaomaiRetrofitManager.getAPIService().userStateList();
        userStateListCall.enqueue(new RetrofitCallback<Responese<List<UserStateEntity>>>() {
            @Override
            public void onSuccess(Responese<List<UserStateEntity>> data) {
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

    /**
     * 更新用户状态
     * @param status
     * @param callback
     */
    public void updateUserState(int status,final RemetoRepoCallback<Void> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updataUserStateCall = XiaomaiRetrofitManager.getAPIService().updateUserState(body);
        updataUserStateCall.enqueue(new Callback<Responese<Void>>() {
            @Override
            public void onResponse(Call<Responese<Void>> call, Response<Responese<Void>> response) {
                if (response.code() == 200 && response.isSuccessful() && response.body() != null) {
                    Responese<Void> body = response.body();
                    if (body.isSuccess()) {
                        callback.onSuccess(null);
                    } else {
                        callback.onFailure(body.getCode(), body.getMsg());
                    }
                } else {
                    callback.onFailure(response.code(), "failure");
                }
            }

            @Override
            public void onFailure(Call<Responese<Void>> call, Throwable t) {
                callback.onFailure(400, "failure");
            }
        });
    }

    @Override
    public void cancelRequest() {
        if (userStateListCall != null && !userStateListCall.isCanceled()) {
            userStateListCall.cancel();
        }
    }

}
