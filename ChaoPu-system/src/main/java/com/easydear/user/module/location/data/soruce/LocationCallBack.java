package com.easydear.user.module.location.data.soruce;

import com.easydear.user.module.location.data.LocationEntity;

import java.util.List;

/**
 * Created by LJH on 2017/7/4.
 */

public interface LocationCallBack {

    String getCityCode();

    void onGetDistrictSuccess(List<LocationEntity> data);

    void onGetDistrictFailure(String message);

    void onGetAllCitySuccess(List<LocationEntity> data);

    void onGetAllCityFailure(String message);

}
