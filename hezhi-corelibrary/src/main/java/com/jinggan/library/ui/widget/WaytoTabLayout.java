package com.jinggan.library.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinggan.library.R;
import com.jinggan.library.base.BaseFragment;

import java.util.List;

import butterknife.ButterKnife;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/17 21:15
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class WaytoTabLayout extends LinearLayout {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FrameLayout otherFl;


    private int tabMode;
    private int tabIndicatorColor;
    private int tabTextColor;

    private int defaultTextClolr;
    private String[] tabNames;

    public WaytoTabLayout(Context context) {
        super(context, null);
    }

    public WaytoTabLayout(Context context, AttributeSet attri) {
        super(context, attri);
        initView();
        parseAttrs(context, attri);
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tablayout_layout, null);
        tabLayout = ButterKnife.findById(view, R.id.Wayto_tabLayout);
        viewPager = ButterKnife.findById(view, R.id.WaytoTabLayout_ViewPager);
        otherFl = ButterKnife.findById(view, R.id.other_fl);
        addView(view);
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaytoTabLayout, 0, 0);
        tabMode = typedArray.getInt(R.styleable.WaytoTabLayout_tabLayout_mode, TabLayout.MODE_FIXED);
        if (tabMode == TabLayout.MODE_FIXED) {
            tabMode = TabLayout.MODE_FIXED;
        } else {
            tabMode = TabLayout.MODE_SCROLLABLE;
        }
        tabIndicatorColor = typedArray.getColor(R.styleable.WaytoTabLayout_tabLayout_indicatorColor, ContextCompat.getColor(getContext(), R.color.red));
        tabTextColor = typedArray.getColor(R.styleable.WaytoTabLayout_tabLayout_tab_textColor, ContextCompat.getColor(getContext(), R.color.red));
        defaultTextClolr=typedArray.getColor(R.styleable.WaytoTabLayout_tabLayout_tab_defaultTextColor, ContextCompat.getColor(getContext(),R.color.gray));
        int background=typedArray.getColor(R.styleable.WaytoTabLayout_tabLayout_background, ContextCompat.getColor(getContext(),R.color.white));
        tabLayout.setBackgroundColor(background);
    }

    /**
     * 初始化TabLayout
     * <p>
     * author: hezhiWu
     * created at 2017/3/17 22:13
     */
    public void initTabLayout(FragmentManager fm, List<BaseFragment> fragments, String[] tabNames) {
        this.tabNames = tabNames;
        viewPager.setAdapter(new TabLayoutViewPagerAdapter(fm, fragments));
        viewPager.setOffscreenPageLimit(fragments.size());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(tabMode);
        tabLayout.setSelectedTabIndicatorColor(tabIndicatorColor);
        initTabView(tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView()).setTextColor(tabTextColor);
//                ((TextView) tab.getCustomView()).setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView()).setTextColor(defaultTextClolr);
//                ((TextView) tab.getCustomView()).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 初始化Tab
     * <p>
     * author: hezhiWu
     * created at 2017/3/17 22:13
     */
    private void initTabView(TabLayout layout) {
        for (int i = 0; i < layout.getTabCount(); i++) {
            TabLayout.Tab tab = layout.getTabAt(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.tablayout_tab_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.TabMission_textView);
            textView.setText(tabNames[i]);
             /*设置默认选择*/
            if (i == 0) {
                textView.setTextColor(tabTextColor);
//                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }else {
                textView.setTextColor(defaultTextClolr);
//                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }
            tab.setCustomView(textView);
        }
    }

    /**
     * 返回ViewPager
     * <p>
     * author: hezhiWu
     * created at 2017/3/18 14:43
     *
     * @return ViewPager
     */
    public ViewPager getViewPager() {
        return viewPager;
    }

    /**
     * 返回TabLayout
     * <p>
     * author: hezhiWu
     * created at 2017/3/18 14:43
     *
     * @return TabLayout
     */
    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public void addOtherView(View view){
        otherFl.addView(view);
    }
}
