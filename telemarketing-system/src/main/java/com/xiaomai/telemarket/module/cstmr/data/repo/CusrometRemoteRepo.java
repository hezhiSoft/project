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
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
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
import retrofit2.Callback;
import retrofit2.Response;

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

    private Call<Responese<List<DictionaryEntity>>> dictionaryCall;

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
     * 编辑负债
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 23:18
     */
    public void editDebto(DebtoEntity entity, final RemetoRepoCallback<Responese> callback) {
        final Call<Responese> call = XiaomaiRetrofitManager.getAPIService().editDebto(entity);
        call.enqueue(new RetrofitCallback<Responese>() {
            @Override
            public void onSuccess(Responese data) {
                if (data.getCode() == 210) {
                    callback.onSuccess(data);
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
     * 编辑负债
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 23:18
     */
    public void addDebto(DebtoEntity entity, final RemetoRepoCallback<Responese> callback) {
        final Call<Responese> call = XiaomaiRetrofitManager.getAPIService().addDebto(entity);
        call.enqueue(new RetrofitCallback<Responese>() {
            @Override
            public void onSuccess(Responese data) {
                if (data.getCode() == 210) {
                    callback.onSuccess(data);
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
     * 编辑房产
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 0:11
     */
    public void editHouse(PropertyEntity entity, final RemetoRepoCallback<Responese> callback) {
        final Call<Responese> call = XiaomaiRetrofitManager.getAPIService().editHouse(entity);
        call.enqueue(new RetrofitCallback<Responese>() {
            @Override
            public void onSuccess(Responese data) {
                if (data.getCode() == 210) {
                    callback.onSuccess(data);
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
     * 编辑保单
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 10:31
     */
    public void editCompany(CompanyEntity entity, final RemetoRepoCallback<Responese> callback) {
        final Call<Responese> call = XiaomaiRetrofitManager.getAPIService().editCompany(entity);
        call.enqueue(new RetrofitCallback<Responese>() {
            @Override
            public void onSuccess(Responese data) {
                if (data.getCode() == 210) {
                    callback.onSuccess(data);
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
     * 编辑保单
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 10:31
     */
    public void editInsurance(InsuranceEntity entity, final RemetoRepoCallback<Responese> callback) {
        final Call<Responese> call = XiaomaiRetrofitManager.getAPIService().editInsurance(entity);
        call.enqueue(new RetrofitCallback<Responese>() {
            @Override
            public void onSuccess(Responese data) {
                if (data.getCode() == 210) {
                    callback.onSuccess(data);
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
     * 编辑汽车
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 10:54
     */
    public void editCar(CarEntity entity, final RemetoRepoCallback<Responese> callback) {
        final Call<Responese> call = XiaomaiRetrofitManager.getAPIService().editCar(entity);
        call.enqueue(new RetrofitCallback<Responese>() {
            @Override
            public void onSuccess(Responese data) {
                if (data.getCode() == 210) {
                    callback.onSuccess(data);
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
     *添加汽车
     * 
     *author: hezhiWu
     *created at 2017/5/27 13:55
     */
    public void addCar(CarEntity entity,final RemetoRepoCallback<Responese> callback){
        final Call<Responese> call = XiaomaiRetrofitManager.getAPIService().addCar(entity);
        call.enqueue(new RetrofitCallback<Responese>() {
            @Override
            public void onSuccess(Responese data) {
                if (data.getCode() == 210) {
                    callback.onSuccess(data);
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
     * 查询客户文件资料
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 22:37
     */
    public void queryCusrometFileLists(String cusrometId, final RemetoRepoCallback<List<FileEntity>> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("customerid", cusrometId);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fileListsCall = XiaomaiRetrofitManager.getAPIService().queryCusrometFileLists(body);
        fileListsCall.enqueue(new RetrofitCallback<Responese<List<FileEntity>>>() {
            @Override
            public void onSuccess(Responese<List<FileEntity>> data) {
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
     * 编辑跟进进度
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 11:20
     */
    public void editFollow(FollowEntity entity, final RemetoRepoCallback<Responese> callback) {
        final Call<Responese> call = XiaomaiRetrofitManager.getAPIService().editFollow(entity);
        call.enqueue(new RetrofitCallback<Responese>() {
            @Override
            public void onSuccess(Responese data) {
                if (data.getCode() == 210) {
                    callback.onSuccess(data);
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
     * 查询字典
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 10:03
     */
    public void queryDictionary(String typecode, final RemetoRepoCallback<List<DictionaryEntity>> callback) {
        RequestBody body = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("typecode", typecode);
            body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dictionaryCall = XiaomaiRetrofitManager.getAPIService().queryDictionary(body);
        dictionaryCall.enqueue(new RetrofitCallback<Responese<List<DictionaryEntity>>>() {
            @Override
            public void onSuccess(Responese<List<DictionaryEntity>> data) {
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
