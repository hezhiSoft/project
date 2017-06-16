package com.easydear.user.common;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.easydear.user.DataApplication;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/16$ 下午5:32$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class LocationManager {

    private static LocationManager instance;

    public static LocationManager getInstance() {
        if (instance == null) {
            instance = new LocationManager();
        }
        return instance;
    }

    /**
     * 启动完成
     * <p>
     * author: hezhiWu
     * created at 2017/6/16 下午5:37
     */
    public void startLocation(final LocationCallBack callBack) {
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
                        if (callBack != null) {
                            callBack.onLocation(aMapLocation);
                        }
                        mLocationClient.stopLocation();
                    }
                }
            }
        });
    }

    public interface LocationCallBack {
        void onLocation(AMapLocation location);
    }
}
