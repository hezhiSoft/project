package com.easydear.user.module.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easydear.user.R;
import com.easydear.user.module.business.BusinessListFragment;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.widget.WaytoTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/9 23:04
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DynamicFragment extends BaseFragment {

    @BindView(R.id.HomeFragment_TabLayout)
    WaytoTabLayout HomeFragmentTabLayout;
    Unbinder unbinder;

    private String[] tabNames;
    private String[] tabKeys = new String[]{"jx", "ms", "yl", "zs", "ac", "js", "lr", "sh"};

    private List<BaseFragment> fragments=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabNames = getResources().getStringArray(R.array.home_tab_array);
        for (int i=0;i<tabKeys.length;i++){

            Bundle bundle=new Bundle();
            bundle.putString("key",tabKeys[i]);

            DynamicListFragment fragment=new DynamicListFragment();
            fragment.setArguments(bundle);

            fragments.add(fragment);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dynamic, null);
        unbinder = ButterKnife.bind(this, rootView);
        initTab();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initTab() {
        HomeFragmentTabLayout.initTabLayout(getChildFragmentManager(), fragments, tabNames);
    }
}
