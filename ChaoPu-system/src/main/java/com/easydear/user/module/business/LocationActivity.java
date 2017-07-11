package com.easydear.user.module.business;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.R;
import com.jinggan.library.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 定位
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/7/4 16:30
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class LocationActivity extends ChaoPuBaseActivity {

    @BindView(R.id.MapView)
    MapView mMapView;

    private AMap amap;

    private double lng, lat;

    private LatLng latLng;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setToolbarTitle("商家定位");
        ButterKnife.bind(this);

        lng = getIntent().getDoubleExtra("lng", 116.397972);
        lat = getIntent().getDoubleExtra("lat", 39.906901);
        latLng = new LatLng(lat,lng);

        mMapView.onCreate(savedInstanceState);

        amap=mMapView.getMap();

        if (lng==0){
            lng=116.397972;
        }

        if (lat==0){
            lat=39.906901;
        }

        Marker marker = amap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
