package com.easydear.user.module.location.data.soruce;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.easydear.user.DataApplication;
import com.easydear.user.api.ResponseEntity;
import com.easydear.user.api.RetrofitManager;
import com.easydear.user.common.Constant;
import com.easydear.user.common.ResponseModel;
import com.easydear.user.module.location.data.LocationEntity;
import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.utils.ILogcat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LJH on 2017/7/3.
 */

public class LocationRepo implements BaseDataSourse {

    final String TAG = getClass().getSimpleName();

    private Call<ResponseEntity<List<LocationEntity>>> locationCall;

    private static LocationRepo instance;

    public static LocationRepo getInstance() {
        if (instance == null) {
            instance = new LocationRepo();
        }
        return instance;
    }

    public void startLocationAction(final StartLocationCallBack callBack) {
        final AMapLocationClient mLocationClient = new AMapLocationClient(DataApplication.getInstance().getApplicationContext());
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度
        locationClientOption.setNeedAddress(true);
        locationClientOption.setInterval(4 * 1000);
        mLocationClient.setLocationOption(locationClientOption);
        mLocationClient.startLocation();
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        callBack.locationSuccess(aMapLocation);
                        mLocationClient.stopLocation();
                    }
                }
            }
        });
    }

    public void requestAllCity(final LocationCallBack callBack) {
        Call<ResponseModel<List<LocationEntity>>> call = RetrofitManager.getInstance().getService().reqCity();
        call.enqueue(new Callback<ResponseModel<List<LocationEntity>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<LocationEntity>>> call, Response<ResponseModel<List<LocationEntity>>> response) {
                if (response.isSuccessful() && response.body().getCode() == Constant.HTTP_SUCESS_CODE) {
                    callBack.onGetAllCitySuccess(response.body().getData());
                } else {
                    callBack.onGetAllCityFailure(response.message());
                }

            }

            @Override
            public void onFailure(Call<ResponseModel<List<LocationEntity>>> call, Throwable t) {
                callBack.onGetAllCityFailure("获取当前城市区域列表失败");
            }
        });
    }

    public void requestCurrentCityDistrict(final LocationCallBack callBack) {
        Call<ResponseModel<List<LocationEntity>>> call = RetrofitManager.getInstance().getService().reqDistrict(callBack.getCityCode());
        call.enqueue(new Callback<ResponseModel<List<LocationEntity>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<LocationEntity>>> call, Response<ResponseModel<List<LocationEntity>>> response) {
                if (response.isSuccessful() && response.body().getCode() == Constant.HTTP_SUCESS_CODE) {
                    callBack.onGetDistrictSuccess(response.body().getData());
                } else {
                    callBack.onGetDistrictFailure(response.message());
                }

            }

            @Override
            public void onFailure(Call<ResponseModel<List<LocationEntity>>> call, Throwable t) {
                callBack.onGetDistrictFailure("获取当前城市区域列表失败");
            }
        });
    }

    @Override
    public void cancelRequest() {
        if (locationCall != null && !locationCall.isCanceled()) {
            locationCall.cancel();
        }
    }
}
