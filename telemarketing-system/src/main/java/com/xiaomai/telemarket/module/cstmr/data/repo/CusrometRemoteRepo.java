package com.xiaomai.telemarket.module.cstmr.data.repo;

import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.net.retrofit.RetrofitCallback;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.api.XiaomaiRetrofitManager;
import com.xiaomai.telemarket.module.cstmr.data.CarEntity;
import com.xiaomai.telemarket.module.cstmr.data.CompanyEntity;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;
import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;
import com.xiaomai.telemarket.module.cstmr.data.InsuranceEntity;
import com.xiaomai.telemarket.module.cstmr.data.PropertyEntity;

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
    private Call<Responese<List<DebtoEntity>>> debtoListsCall;
    private Call<Responese<List<PropertyEntity>>> propertyListsCall;
    private Call<Responese<List<InsuranceEntity>>> insuranceListsCall;
    private Call<Responese<List<CompanyEntity>>> companyListsCall;
    private Call<Responese<List<CarEntity>>> carListsCall;
    private Call<Responese<List<FileEntity>>> fileListsCall;
    private Call<Responese<List<FollowEntity>>> followListsCall;

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
        listCusrometListCall = XiaomaiRetrofitManager.getAPIService().queryCusrometLists(body);
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
     * 获取客户负债信息
     * <p>
     * author: hezhiWu
     * created at 2017/5/20 16:27
     */
    public void queryCusrometDebtoLists(String cusrometId, final RemetoRepoCallback<List<DebtoEntity>> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerid", cusrometId);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        debtoListsCall = XiaomaiRetrofitManager.getAPIService().queryCusrometDebtoLists(body);
        debtoListsCall.enqueue(new RetrofitCallback<Responese<List<DebtoEntity>>>() {
            @Override
            public void onSuccess(Responese<List<DebtoEntity>> data) {
                callback.onSuccess(data.getData());
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
     * 获取房产信息列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 9:02
     *
     * @param cusrometId 客户ID
     * @param callback
     */
    public void queryCusrometHouseLists(String cusrometId, final RemetoRepoCallback<List<PropertyEntity>> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerid", cusrometId);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        propertyListsCall = XiaomaiRetrofitManager.getAPIService().queryCusrometHouse(body);
        propertyListsCall.enqueue(new RetrofitCallback<Responese<List<PropertyEntity>>>() {
            @Override
            public void onSuccess(Responese<List<PropertyEntity>> data) {
                if (data.getData().size() > 0) {
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
     * 查询客户公司列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 9:22
     *
     * @param cusrometId
     * @param callback
     */
    public void queryCusrometCompanyLists(String cusrometId, final RemetoRepoCallback<List<CompanyEntity>> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerid", cusrometId);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        companyListsCall = XiaomaiRetrofitManager.getAPIService().queryCusrometCompany(body);
        companyListsCall.enqueue(new RetrofitCallback<Responese<List<CompanyEntity>>>() {
            @Override
            public void onSuccess(Responese<List<CompanyEntity>> data) {
                if (data.getData().size() > 0) {
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
     * 查询客户保单列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 9:09
     */
    public void queryCusrometInsuranceLists(String cusrometId, final RemetoRepoCallback<List<InsuranceEntity>> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerid", cusrometId);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        insuranceListsCall = XiaomaiRetrofitManager.getAPIService().queryCusrometInsurance(body);
        insuranceListsCall.enqueue(new RetrofitCallback<Responese<List<InsuranceEntity>>>() {
            @Override
            public void onSuccess(Responese<List<InsuranceEntity>> data) {
                if (data.getData().size() > 0) {
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
     * 获取客户车辆信息
     * <p>
     * author: hezhiWu
     * created at 2017/5/20 19:32
     */
    public void queryCusrometCarLists(String cusrometId, final RemetoRepoCallback<List<CarEntity>> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerid", cusrometId);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        carListsCall = XiaomaiRetrofitManager.getAPIService().queryCusrometCarLists(body);
        carListsCall.enqueue(new RetrofitCallback<Responese<List<CarEntity>>>() {
            @Override
            public void onSuccess(Responese<List<CarEntity>> data) {
                callback.onSuccess(data.getData());
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
     * 查询客户文件资料
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 22:37
     */
    public void queryCusrometFileLists(String cusrometId, RemetoRepoCallback<List<FileEntity>> callback) {

    }

    /**
     * 查询客户跟进明细
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 22:41
     */
    public void queryCusrometFollowLists(String cusrometId, final RemetoRepoCallback<List<FollowEntity>> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerid", cusrometId);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        followListsCall = XiaomaiRetrofitManager.getAPIService().queryCusrometFollowLists(body);
        followListsCall.enqueue(new RetrofitCallback<Responese<List<FollowEntity>>>() {
            @Override
            public void onSuccess(Responese<List<FollowEntity>> data) {
                if (data.getData() != null && data.getData().size() > 0) {
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
        if (debtoListsCall != null && !debtoListsCall.isCanceled()) {
            debtoListsCall.cancel();
        }
        if (propertyListsCall != null && !propertyListsCall.isCanceled()) {
            propertyListsCall.cancel();
        }
        if (insuranceListsCall != null && !insuranceListsCall.isCanceled()) {
            insuranceListsCall.cancel();
        }
        if (companyListsCall != null && !companyListsCall.isCanceled()) {
            companyListsCall.cancel();
        }
        if (carListsCall != null && !carListsCall.isCanceled()) {
            carListsCall.cancel();
        }
        if (fileListsCall != null && !fileListsCall.isCanceled()) {
            fileListsCall.cancel();
        }
        if (followListsCall != null && !followListsCall.isCanceled()) {
            followListsCall.cancel();
        }
    }
}
