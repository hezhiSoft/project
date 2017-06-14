package com.xiaomai.telemarket.module.function.callTrend;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.widget.WaytoTabLayout;
import com.jinggan.library.utils.IDateTimeUtils;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.function.data.StatisticsByMonthParam;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 部门外呼走势
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/28$ 下午12:24$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CallTrendActivity extends XiaoMaiBaseActivity implements DepStatisticFilterBottomDialog.OnConfirmClickListener {

    @BindView(R.id.TrendStatistics_tabLayout)
    WaytoTabLayout TrendStatisticsTabLayout;

    private String[] tabNames;
    private List<BaseFragment> fragments = new ArrayList<>();
    private StatisticsByMonthParam paramEntity;

    private CallTrendDetailFragment outCountFragment,connectFragment,intentFragment, reservationFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_trend);
        ButterKnife.bind(this);
        setToolbarTitle("外呼走势");
        setToolbarRightImage(R.mipmap.icon_screen);
        setToolbarRightLayoutVisibility(true);
        initTabLayout();
    }

    private void initTabLayout() {
        tabNames = getResources().getStringArray(R.array.statistic_dep_details_tab_array);
        Bundle bundle = new Bundle();

        outCountFragment = new CallTrendDetailFragment();
        paramEntity = getParamEntity();
        paramEntity.setType(Constant.StatisticType.CALL.getType());
        bundle.putSerializable(CallTrendDetailFragment.EXTRA_PARAM, paramEntity);
        outCountFragment.setArguments(bundle);
        fragments.add(outCountFragment);//外呼数量

        connectFragment = new CallTrendDetailFragment();
        paramEntity = getParamEntity();
        paramEntity.setType(Constant.StatisticType.CONNECT.getType());
        bundle.putSerializable(CallTrendDetailFragment.EXTRA_PARAM, paramEntity);
        connectFragment.setArguments(bundle);
        fragments.add(connectFragment);//接通数量

        intentFragment = new CallTrendDetailFragment();
        paramEntity = getParamEntity();
        paramEntity.setType(Constant.StatisticType.INTENT.getType());
        bundle.putSerializable(CallTrendDetailFragment.EXTRA_PARAM, paramEntity);
        intentFragment.setArguments(bundle);
        fragments.add(intentFragment);//意向状态

        reservationFragment = new CallTrendDetailFragment();
        paramEntity = getParamEntity();
        paramEntity.setType(Constant.StatisticType.APPOINT.getType());
        bundle.putSerializable(CallTrendDetailFragment.EXTRA_PARAM, paramEntity);
        reservationFragment.setArguments(bundle);
        fragments.add(reservationFragment);//预约上门
        TrendStatisticsTabLayout.initTabLayout(getSupportFragmentManager(), fragments, tabNames);
    }

    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
        DepStatisticFilterBottomDialog dialog = new DepStatisticFilterBottomDialog();
        dialog.setUserParamntity(paramEntity).setOnConfirmClickListener(this);
        dialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    @Override
    public void onConfirm(StatisticsByMonthParam paramEntity) {
        this.paramEntity = paramEntity;
        int currentItem = TrendStatisticsTabLayout.getViewPager().getCurrentItem();
        StatisticsByMonthParam entity;
        if (outCountFragment==null||connectFragment==null||intentFragment==null||reservationFragment==null) {
            return;
        }
        // TODO: 14/06/2017 通知刷新
        if (currentItem == 0) {/*外呼数量*/
            entity=outCountFragment.getParamEntity();
            entity.setYear(paramEntity.getYear());
            outCountFragment.setParamEntity(entity);
            outCountFragment.onDownRefresh();
        } else if (currentItem == 1) {/*接通数量*/
            entity=connectFragment.getParamEntity();
            entity.setYear(paramEntity.getYear());
            connectFragment.setParamEntity(entity);
            connectFragment.onDownRefresh();
        } else if (currentItem == 2) {/*意向状态*/
            entity=intentFragment.getParamEntity();
            entity.setYear(paramEntity.getYear());
            intentFragment.setParamEntity(entity);
            intentFragment.onDownRefresh();
        } else if (currentItem == 3) {/*预约上门*/
            entity=reservationFragment.getParamEntity();
            entity.setYear(paramEntity.getYear());
            reservationFragment.setParamEntity(entity);
            reservationFragment.onDownRefresh();
        }
    }

    public StatisticsByMonthParam getParamEntity(){
        paramEntity = new StatisticsByMonthParam();
        paramEntity.setYear(IDateTimeUtils.getCurrentDate("yyyy"));//默认今年
        return paramEntity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
