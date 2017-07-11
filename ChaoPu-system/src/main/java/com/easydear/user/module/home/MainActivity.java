package com.easydear.user.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.cards.CardsFragment;
import com.easydear.user.module.dynamic.DynamicFragment;
import com.easydear.user.module.mine.MineFragment;
import com.easydear.user.module.scann.ScanningFragment;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.ui.view.MainBottomNavigationBar;
import com.jinggan.library.utils.IActivityManage;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/9 22:55
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class MainActivity extends BaseActivity implements MainBottomNavigationBar.BottomTabSelectedListener{

    private final static int TAB_HOME=0;
    private final static int TAB_DYNAMIC=1;
    private final static int TAB_CARDS=2;
    private final static int TAB_SCANN=3;
    private final static int TAB_MINE=4;

    @BindView(R.id.main_bottom_navigationBar)
    MainBottomNavigationBar mainBottomNavigationBar;

    private long exitTime = 0;

    private int currentPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeEnabled(false);

        initBottomNavigationBar();
    }

    /**
     * 初始化BottomNavigationBar
     * author: hezhiWu
     * created at 2017/3/14 22:25
     */
    private void initBottomNavigationBar() {
        mainBottomNavigationBar.initConfig(this, R.id.main_container_FrameLayout);
        mainBottomNavigationBar.addTabItem(R.mipmap.ic_tab_home, R.string.tab_home, new HomeFragment())
                .addTabItem(R.mipmap.ic_tab_dynamic, R.string.tab_dynamic, new DynamicFragment())
                .addTabItem(R.mipmap.ic_tab_cards, R.string.tab_cards, new CardsFragment())
                .addTabItem(R.mipmap.ic_tab_scann,R.string.tab_scann,new ScanningFragment())
                .addTabItem(R.mipmap.ic_tab_mine,R.string.tab_mine,new MineFragment())
                .setTabSelectedListener(this)
                .setFirstSelectedTab(TAB_HOME);
        setToolbarVisibility(View.GONE);
//        setToolbarCenterTitle(R.string.tab_home);
    }

    @Override
    public void onTabSelected(int position) {
        switch (position){
            case TAB_HOME:
                setToolbarCenterTitle(R.string.tab_home);
                setToolbarVisibility(View.GONE);
                currentPosition=TAB_HOME;
                break;
            case TAB_DYNAMIC:
                setToolbarCenterTitle("商家动态");
                setToolbarVisibility(View.GONE);
                currentPosition=TAB_DYNAMIC;
                break;
            case TAB_CARDS:
                setToolbarCenterTitle(R.string.tab_cards);
                setToolbarVisibility(View.VISIBLE);
                currentPosition=TAB_CARDS;
                break;
            case TAB_SCANN:
                mainBottomNavigationBar.setFirstSelectedTab(currentPosition);

//                setToolbarCenterTitle(R.string.tab_scann);
//                setToolbarVisibility(View.VISIBLE);

//                Intent intent=new Intent(this, CaptureActivity.class);
//                startActivity(intent);

                ISkipActivityUtil.startIntent(this,CaptureActivity.class);
                break;
            case TAB_MINE:
                setToolbarCenterTitle(R.string.tab_mine);
                setToolbarVisibility(View.GONE);
                currentPosition=TAB_MINE;
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        EventBusValues event = new EventBusValues();
        if (resultCode == RESULT_OK){
            switch (requestCode) {
                case Constant.HOME_SELECT_CITY_REQUEST_CODE:
                    event.setWhat(Constant.NOTICE_HOME_UPDATE_CITY);
                    event.setObject(data);
                    EventBus.getDefault().post(event);
                    break;
                case Constant.HOME_SEARCH_KEY_REQUEST_CODE:
                    event.setWhat(Constant.NOTICE_HOME_SEARCH);
                    event.setObject(data);
                    EventBus.getDefault().post(event);
                    break;
                default:
                    break;
            }
        }
    }


    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            IActivityManage.getInstance().exit();
            System.exit(0);
        }
    }
}
