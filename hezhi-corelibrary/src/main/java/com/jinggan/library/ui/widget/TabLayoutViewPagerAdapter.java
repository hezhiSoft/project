package com.jinggan.library.ui.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jinggan.library.base.BaseFragment;

import java.util.List;

/**
 * TabLayout ViewPagert适配器
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/17 21:24
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class TabLayoutViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;

    public TabLayoutViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabLayoutViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
