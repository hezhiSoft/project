package com.easydear.user.module.home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.easydear.user.DataApplication;
import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.common.LocationManager;
import com.easydear.user.module.business.BusinessListFragment;
import com.easydear.user.module.location.LocationActivity;
import com.easydear.user.module.message.MessageActivity;
import com.easydear.user.module.search.SearchActivity;
import com.easydear.user.util.ISpfUtil;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.ui.widget.WaytoTabLayout;
import com.jinggan.library.utils.ILogcat;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.PermissionHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/9 22:55
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class HomeFragment extends BaseFragment {

    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.HomeFragment_TabLayout)
    WaytoTabLayout HomeFragmentTabLayout;
    @BindView(R.id.HomeFragment_location_textView)
    TextView HomeFragmentLocationTextView;

    Unbinder unbinder;

    private String mCity = "";
    private String mCityCode = "";
    private String mDistrict = "";
    private String mDistrictCode;
    private String mSearchKey = "";

    private String[] tabNames;
    private String[] tabKeys = new String[]{"jx", "ms", "yl", "zs", "ac", "js", "ls", "sh"};

    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabNames = getResources().getStringArray(R.array.home_tab_array);
        for (int i = 0; i < tabKeys.length; i++) {

            Bundle bundle = new Bundle();
            bundle.putString("key", tabKeys[i]);

            BusinessListFragment fragment = new BusinessListFragment();
            fragment.setArguments(bundle);

            fragments.add(fragment);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initTab();

        if (PermissionHelper.checkPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION,0x9002)){
            startLocation();
        }
        return rootView;
    }

    private void startLocation(){
        LocationManager.getInstance().startLocation(new LocationManager.LocationCallBack() {
            @Override
            public void onLocation(AMapLocation location) {
                if (location!=null){
                    mCity = location.getCity();
                    ISpfUtil.setValue(Constant.AMAP_LOCATION_CITY, location.getCity());
                    HomeFragmentLocationTextView.setText(mCity);
                    ILogcat.i(getClass().getSimpleName(), "onCreateView, location.getCity() = " + location.getCity());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initTab() {
        HomeFragmentTabLayout.initTabLayout(getChildFragmentManager(), fragments, tabNames);
    }

    /**
     * EventBus 更新 Location City
     */
    @Subscribe
    public void onEventMainThread(EventBusValues value) {
        if (value == null) {
            return;
        }
        switch (value.getWhat()) {
//            case EventConstant.NOTICE11:
//                setLocationCity();
//                break;
            case Constant.NOTICE_HOME_UPDATE_CITY:
                Intent intent = (Intent) value.getObject();
                mCity = intent.getStringExtra("city");
                mCityCode = intent.getStringExtra("city_code");
                if (intent.hasExtra("district") && intent.hasExtra("district_code")) {
                    mDistrictCode = intent.getStringExtra("district_code");
                    mDistrict = intent.getStringExtra("district");
                    HomeFragmentLocationTextView.setText(mDistrict);
//                    mSelectDistrictTextView.setText(mDistrict);
                } else {
                    HomeFragmentLocationTextView.setText(mCity);
//                    mSelectDistrictTextView.setText("");
                    mDistrict = "";
                    mDistrictCode = "";
                }
                mSearchKey = "";
//                requestTopScrollArticles();
                break;
            case Constant.NOTICE_HOME_SEARCH:
                Intent search = (Intent) value.getObject();
                mSearchKey = search.getStringExtra("search_key");
                ILogcat.v(TAG, "Current Search Key = " + mSearchKey);
//                requestTopScrollArticles();
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.HomeFragment_Message_Layout,R.id.HomeFragment_location_layout})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.HomeFragment_location_layout:
            case R.id.HomeFragment_location_textView:
                Bundle bundle = new Bundle();
                bundle.putString("city", mCity);
                bundle.putString("city_code", mCityCode);
                ISkipActivityUtil.startIntentForResult(getActivity(), LocationActivity.class, bundle, Constant.HOME_SELECT_CITY_REQUEST_CODE);
                break;
            case R.id.HomeFragment_Message_Layout:
//                ISkipActivityUtil.startIntent(getContext(), MessageActivity.class);
                ISkipActivityUtil.startIntentForResult(getActivity(), SearchActivity.class, Constant.HOME_SEARCH_KEY_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==0x9002){
            startLocation();
        }
    }
}
