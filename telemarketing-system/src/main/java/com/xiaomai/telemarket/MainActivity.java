package com.xiaomai.telemarket;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.view.MainBottomNavigationBar;
import com.xiaomai.telemarket.module.cstmr.fragment.CusrometManagementAllFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.CusrometManagementStayFragment;
import com.xiaomai.telemarket.module.home.HomeFragment;
import com.xiaomai.telemarket.module.order.OrderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainBottomNavigationBar.BottomTabSelectedListener {
    /*模块标记*/
    private final int TAB_HOME = 0;
    private final int TAB_CUSROMENTMANAGEMENT = 1;
    private final int TAB_CUSROMENTLIST = 2;
    private final int TAB_ORDER = 3;

    @BindView(R.id.main_bottom_navigationBar)
    MainBottomNavigationBar mainBottomNavigationBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeEnabled(false);

        initBottomNavigationBar();
    }

    private void initBottomNavigationBar() {
        mainBottomNavigationBar.initConfig(this, R.id.main_container_FrameLayout);
        mainBottomNavigationBar.addTabItem(R.drawable.ic_tab_home, R.string.main_home_tab, new HomeFragment())
                .addTabItem(R.drawable.ic_tab_customer, R.string.main_all_cusroment_tab, new CusrometManagementAllFragment())
                .addTabItem(R.drawable.ic_tab_list, R.string.main_stay_cusroment_tab, new CusrometManagementStayFragment())
                .addTabItem(R.drawable.ic_tab_order, R.string.main_order_tab,new OrderFragment())
                .setTabSelectedListener(this)
                .setFirstSelectedTab(TAB_HOME);
        setToolbarVisibility(View.GONE);
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case TAB_HOME:
                setToolbarVisibility(View.GONE);
                break;
            case TAB_CUSROMENTMANAGEMENT:
                setToolbarVisibility(View.GONE);
                break;
            case TAB_CUSROMENTLIST:
                setToolbarCenterTitle("待跟进列表");
                setToolbarVisibility(View.VISIBLE);
                setToolbarRightImage(R.mipmap.icon_screen);
                break;
            case TAB_ORDER:
                setToolbarCenterTitle(R.string.main_order_tab);
                setToolbarRightImage(R.mipmap.icon_screen);
                setToolbarVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            DialogFactory.showMsgDialog(this, "退出", "确定退出电销系统?", "退出", "取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }, null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
