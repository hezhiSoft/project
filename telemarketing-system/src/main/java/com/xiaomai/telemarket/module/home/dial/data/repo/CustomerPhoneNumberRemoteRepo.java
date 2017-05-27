package com.xiaomai.telemarket.module.home.dial.data.repo;

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
 * @description 获取拨号资源
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 26/05/2017 00:33 AM
 **/
public class CustomerPhoneNumberRemoteRepo implements BaseDataSourse{
    private Call<Responese<List<CusrometListEntity>>> listCusrometListCall;

    private static CustomerPhoneNumberRemoteRepo instance;

    public static CustomerPhoneNumberRemoteRepo getInstance() {
        if (instance == null) {
            instance = new CustomerPhoneNumberRemoteRepo();
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
     * @param preUserId
     * @param callback
     */
    public void requestPhoneNumberFromPrivate(String preUserId,final RemetoRepoCallback<List<CusrometListEntity>> callback){
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("preid", preUserId);
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

    @Override
    public void cancelRequest() {
        if (listCusrometListCall!=null&&!listCusrometListCall.isCanceled()) {
            listCusrometListCall.cancel();
        }
    }
}
