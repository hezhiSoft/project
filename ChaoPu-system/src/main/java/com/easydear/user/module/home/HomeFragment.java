package com.easydear.user.module.home;

import android.Manifest;
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
import com.easydear.user.common.LocationManager;
import com.easydear.user.module.business.BusinessListFragment;
import com.easydear.user.module.message.MessageActivity;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.widget.WaytoTabLayout;
import com.jinggan.library.utils.ILogcat;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.PermissionHelper;

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

    @BindView(R.id.HomeFragment_TabLayout)
    WaytoTabLayout HomeFragmentTabLayout;
    Unbinder unbinder;
    @BindView(R.id.HomeFragment_location_textView)
    TextView HomeFragmentLocationTextView;

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
                    HomeFragmentLocationTextView.setText(location.getCity());
                    ILogcat.i(getClass().getSimpleName(), "onCreateView, location.getCity() = " + location.getCity());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initTab() {
        HomeFragmentTabLayout.initTabLayout(getChildFragmentManager(), fragments, tabNames);
    }

    @OnClick({R.id.HomeFragment_Message_Layout,R.id.HomeFragment_location_layout})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.HomeFragment_location_textView:
                break;
            case R.id.HomeFragment_Message_Layout:
                ISkipActivityUtil.startIntent(getContext(), MessageActivity.class);
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
