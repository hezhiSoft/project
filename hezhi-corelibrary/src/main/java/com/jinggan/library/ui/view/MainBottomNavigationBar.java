package com.jinggan.library.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;

import com.jinggan.library.R;
import com.jinggan.library.ui.widget.bottomnavigation.BadgeItem;
import com.jinggan.library.ui.widget.bottomnavigation.BottomNavigationBar;
import com.jinggan.library.ui.widget.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面Bottom导航栏
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:16
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class MainBottomNavigationBar extends BottomNavigationBar implements BottomNavigationBar.OnTabSelectedListener{
    /**
     * Fragment集合
     */
    private List<Fragment> fragments;
    /**
     * Activity
     */
    private AppCompatActivity activity;
    /**
     * 容器Id
     */
    private int containerId;

    private List<Integer> mIconResources;
    private List<Integer> mTitleResources;

    private BottomTabSelectedListener tabSelectedListener;

    private int tab_active;

    public MainBottomNavigationBar(Context context) {
        super(context, null);
    }

    public MainBottomNavigationBar(Context context, AttributeSet attri) {
        super(context, attri);
        init();
        initAttri(context, attri);
    }

    /**
     * 初始化
     */
    private void init() {
        this.mIconResources = new ArrayList<>();
        this.mTitleResources = new ArrayList<>();
        setDefaultConfig();
        setTabSelectedListener(this);
    }

    private void initAttri(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MainBottomNavigationBar, 0, 0);
        tab_active = typedArray.getColor(R.styleable.MainBottomNavigationBar_tab_active, ContextCompat.getColor(context, R.color.tab_active));
    }

    /**
     * 初始配置
     */
    private void setDefaultConfig() {
        setMode(BottomNavigationBar.MODE_FIXED);
        setBackgroundResource(R.color.white);
        setActiveColor(R.color.white);
    }

    /**
     * 初始化配置
     *
     * @param activity
     * @param containerId
     */
    public void initConfig(AppCompatActivity activity, int containerId) {
        this.activity = activity;
        this.containerId = containerId;
        this.fragments = new ArrayList<>();
    }

    /**
     * 添加Fragment
     *
     * @param fragment
     * @return
     */
    public MainBottomNavigationBar addFragment(Fragment fragment) {
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fragments.add(fragment);

        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(containerId, fragment);
        transaction.commitNowAllowingStateLoss();
        return this;
    }


    /**
     * 添加TabItem
     *
     * @param iconResId
     * @param nameResId
     * @return
     */
    public MainBottomNavigationBar addTabItem(int iconResId, int nameResId,Fragment fragment) {
        addItem(new BottomNavigationItem(iconResId, nameResId)).setActiveColorV2(tab_active).initialise();

        mIconResources.add(iconResId);
        mTitleResources.add(nameResId);
        addFragment(fragment);
        return this;
    }
    /**
     * 添加TabItem
     *
     * @param iconResId
     * @param nameResId
     * @return
     */
    public MainBottomNavigationBar addTabItem(int iconResId, int nameResId) {
        addItem(new BottomNavigationItem(iconResId, nameResId)).setActiveColorV2(tab_active).initialise();

        mIconResources.add(iconResId);
        mTitleResources.add(nameResId);
        return this;
    }


    /**
     * 添加底部菜单Tab Item
     * <p>
     * author: hezhiWu
     * created at 2017/4/18 18:07
     *
     * @param position 添加位置
     */
    public MainBottomNavigationBar addTabMenuItem(int position, OnTabMenuClickListener listener) {
        addRecordImageView(position, listener);
        return this;
    }

    /**
     * set Tab sign
     *
     * @param tabPosition
     * @param number
     */
    public void addTabSign(int tabPosition, int number) {
        int iconResource = mIconResources.get(tabPosition);
        int titleResource = mTitleResources.get(tabPosition);
        if (number > 0) {
            BadgeItem item = new BadgeItem();
            item.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.badgeItem_color));
            if (number > 99) {
                item.setText("99+");
            } else {
                item.setText(String.valueOf(number));
            }
            removeItem(tabPosition)
                    .addItem(tabPosition, new BottomNavigationItem(iconResource, titleResource).setBadgeItem(item))
                    .setActiveColor(R.color.tab_active).initialise();
        } else {
            removeItem(tabPosition)
                    .addItem(tabPosition, new BottomNavigationItem(iconResource, titleResource)).setActiveColor(R.color.tab_active)
                    .initialise();
        }
    }

    /**
     * remove Tab sign
     *
     * @param tabPosition
     */
    public void removeTabSign(int tabPosition) {
        int iconResource = mIconResources.get(tabPosition);
        int titleResource = mTitleResources.get(tabPosition);
        removeItem(tabPosition).addItem(tabPosition, new BottomNavigationItem(iconResource, titleResource)).setActiveColor(R.color.tab_active).initialise();
    }

    /**
     * 设置默认选中的Tab
     *
     * @param position
     */
    public MainBottomNavigationBar setFirstSelectedTab(int position) {
        switchTab(position);
        setFirstSelectedPosition(position).initialise();
        onTabSelected(position);
        return this;
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabSelected(int position) {
        switchTab(position);
        closeMenu();
        if (tabSelectedListener != null) {
            tabSelectedListener.onTabSelected(position);
        }
    }

    /**
     * Tab切换
     *
     * @param position
     */
    private void switchTab(int position) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        transaction.show(fragments.get(position));
        transaction.commitNowAllowingStateLoss();
    }

    /**
     * hide Fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        for (Fragment fragment : fragments) {
            transaction.hide(fragment);
        }
    }

    /**
     * set listener
     *
     * @param listener
     */
    public MainBottomNavigationBar setTabSelectedListener(BottomTabSelectedListener listener) {
        this.tabSelectedListener = listener;
        return this;
    }

    public interface BottomTabSelectedListener {
        void onTabSelected(int position);
    }
}
