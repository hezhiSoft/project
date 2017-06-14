package com.xiaomai.telemarket.module.function.callOut;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.date.bean.DateType;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.jinggan.library.utils.IDateTimeUtils;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.module.function.data.CallOutStaticsEntity;
import com.xiaomai.telemarket.module.function.data.StatisticByUserParam;
import com.xiaomai.telemarket.module.function.data.source.CallOutRemoteRepo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xiaomai.telemarket.R.id.user_state_recyclerview;

/**
 * 员工外呼
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/28$ 下午12:23$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CallOutActivity extends XiaoMaiBaseActivity implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener,StatisticFilterBottomDialog.OnConfirmClickListener ,RemetoRepoCallback<List<CallOutStaticsEntity>> {


    @BindView(user_state_recyclerview)
    PullToRefreshRecyclerView userStateRecyclerview;
    private UserStatisticListAdapter mAdatper;

    private CallOutRemoteRepo callOutRemoteRepo;
    private StatisticByUserParam userParamEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //selector_toolbar_seletor
        setContentView(R.layout.activity_call_out);
        setToolbarTitle("员工外呼统计");
        setToolbarRightImage(R.mipmap.icon_screen);
        setToolbarRightLayoutVisibility(true);
        ButterKnife.bind(this);
        initParams();
    }

    private void initParams() {
        callOutRemoteRepo = CallOutRemoteRepo.getInstance();
        mAdatper = new UserStatisticListAdapter(this);
        userStateRecyclerview.setRecyclerViewAdapter(mAdatper);
        userStateRecyclerview.setMode(PullToRefreshRecyclerView.Mode.PULL_FROM_START);
        userStateRecyclerview.setPullToRefreshListener(this);
        userStateRecyclerview.startUpRefresh();//
    }


    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
        StatisticFilterBottomDialog dialog = new StatisticFilterBottomDialog();
        dialog.setUserParamntity(userParamEntity).setOnConfirmClickListener(this);
        dialog.show(getSupportFragmentManager(), getClass().getSimpleName());
    }

    @Override
    public void onConfirm(StatisticByUserParam userParamntity) {
        this.userParamEntity = userParamntity;
        callOutRemoteRepo.queryStaticsByUser(userParamntity,this);
    }

    @Override
    public void onDownRefresh() {
        if (userParamEntity==null) {
            userParamEntity = new StatisticByUserParam();
            userParamEntity.setFromDate(IDateTimeUtils.getCurrentDate(DateType.TYPE_YMD.getFormat()));
            userParamEntity.setToDate(IDateTimeUtils.getCurrentDate(DateType.TYPE_YMD.getFormat()));
        }
        callOutRemoteRepo.queryStaticsByUser(userParamEntity,this);
    }

    @Override
    public void onPullRefresh() {
    }

    @Override
    public void onSuccess(List<CallOutStaticsEntity> data) {
        if (userStateRecyclerview!=null) {
            userStateRecyclerview.setEmptyTextViewVisiblity(View.GONE);
        }
        mAdatper.clearList();
        mAdatper.addItems(data);
    }

    @Override
    public void onFailure(int code, String msg) {
        mAdatper.clearList();
        if (userStateRecyclerview!=null) {// TODO: 30/05/2017 连续两次重启app这里报空指针错误
            userStateRecyclerview.setPageHint(R.mipmap.icon_data_empty,"数据为空");
        }
    }

    @Override
    public void onThrowable(Throwable t) {
        if (userStateRecyclerview != null) {
            userStateRecyclerview.setPageHint(R.mipmap.icon_page_error,"页面出错");
        }
    }

    @Override
    public void onUnauthorized() {
        if (userStateRecyclerview != null) {
            userStateRecyclerview.setPageHint(R.mipmap.icon_page_error,"页面出错");
        }
    }

    @Override
    public void onFinish() {
        if (userStateRecyclerview!=null) {
            userStateRecyclerview.closeDownRefresh();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (callOutRemoteRepo!=null) {
            callOutRemoteRepo.cancelRequest();
        }
    }
}
