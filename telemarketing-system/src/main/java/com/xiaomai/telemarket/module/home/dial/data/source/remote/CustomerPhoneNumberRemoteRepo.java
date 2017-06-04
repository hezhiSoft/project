package com.xiaomai.telemarket.module.home.dial.data.source.remote;

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
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @description 获取拨号资源
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 26/05/2017 00:33 AM
 **/
public class CustomerPhoneNumberRemoteRepo implements BaseDataSourse{
    private Call<Responese<List<CusrometListEntity>>> listCusrometListCall;
    private Call<Responese<Void>> deleteTempInfoCall;

    private static CustomerPhoneNumberRemoteRepo instance;

    public static CustomerPhoneNumberRemoteRepo getInstance() {
        if (instance == null) {
            synchronized (CustomerPhoneNumberRemoteRepo.class){
                if (instance == null) {
                    instance = new CustomerPhoneNumberRemoteRepo();
                }
            }
        }
        return instance;
    }


    /**
     * 从公共库中获取客户信息
     * @param callback
     */
    public void requestPhoneNumberFromPublic(final RemetoRepoCallback<List<CusrometListEntity>> callback) {
        listCusrometListCall = XiaomaiRetrofitManager.getAPIService().getCustomerInfoFromPublic();
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

    /**
     * 从私有库中获取客户信息
     * @param preUserTime
     * @param callback
     */
    public void requestPhoneNumberFromPrivate(String preUserTime,final RemetoRepoCallback<List<CusrometListEntity>> callback){
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("predate", preUserTime);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listCusrometListCall = XiaomaiRetrofitManager.getAPIService().getCustomerInfoFromPrivate(body);
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

    /**
     * 清除拨号信息
     * @param userid
     * @param callback
     */
    public void deleteFromList(String userid,final RemetoRepoCallback<Void> callback){
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerid", userid);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        deleteTempInfoCall = XiaomaiRetrofitManager.getAPIService().DelFromList(body);
        deleteTempInfoCall.enqueue(new Callback<Responese<Void>>() {
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
        if (listCusrometListCall!=null&&!listCusrometListCall.isCanceled()) {
            listCusrometListCall.cancel();
        }if (deleteTempInfoCall!=null&&!deleteTempInfoCall.isCanceled()) {
            deleteTempInfoCall.cancel();
        }
    }

}
