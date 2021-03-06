package com.xiaomai.telemarket.module.cstmr.fragment.insurance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.CusrometDetailsActivity;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.InsuranceEntity;
import com.xiaomai.telemarket.module.cstmr.data.PropertyEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/16$ 下午10:38$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CusrometInsurancePolicyFragment extends BaseFragment implements CusrometInsuranceAdapter.OnClickItemLisenter, PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallback<List<InsuranceEntity>> {

    @BindView(R.id.Details_number_TextView)
    TextView DetailsNumberTextView;
    @BindView(R.id.Insurance_recyclerView)
    PullToRefreshRecyclerView PropertyRecyclerView;
    @BindView((R.id.Details_add_Button))
    Button addButtonDetails;

    private CusrometInsuranceAdapter adapter;
    private CusrometRemoteRepo remoteRepo;

    private String cusrometId;

    private InsuranceEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        cusrometId = getArguments().getString("id");
        remoteRepo = CusrometRemoteRepo.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cusromet_insurance, null);
        ButterKnife.bind(this, rootView);
        addButtonDetails.setText("添加保单");
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        adapter = new CusrometInsuranceAdapter(getContext());
        adapter.setListenter(this);
        PropertyRecyclerView.setRecyclerViewAdapter(adapter);
        PropertyRecyclerView.setMode(PullToRefreshRecyclerView.Mode.DISABLED);
        PropertyRecyclerView.setPullToRefreshListener(this);
//        PropertyRecyclerView.startUpRefresh();
        remoteRepo.queryCusrometInsuranceLists(cusrometId, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onUpdateUIData(EventBusValues values){
        if (values.getWhat()==0x204){
            remoteRepo.queryCusrometInsuranceLists(cusrometId, this);
        }
        if (values.getWhat()==0x890){
            cusrometId=((CusrometListEntity)values.getObject()).getID();
            remoteRepo.queryCusrometInsuranceLists(cusrometId, this);
        }
    }

    @Override
    public void onDownRefresh() {
        remoteRepo.queryCusrometInsuranceLists(cusrometId, this);
    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onSuccess(List<InsuranceEntity> data) {
        adapter.clearList();
        if (data != null && data.size() > 0) {
            DetailsNumberTextView.setText("共" + data.size() + "条保单信息");
            if (PropertyRecyclerView != null) {
                PropertyRecyclerView.setEmptyTextViewVisiblity(View.GONE);
            }
            adapter.addItems(data);
            ((CusrometDetailsActivity) getActivity()).getTabLayout().setTagNumber(3, data.size());
        } else {
            DetailsNumberTextView.setText("保单信息");
            if (PropertyRecyclerView != null) {
                PropertyRecyclerView.setPageHint(R.mipmap.icon_data_empty, "资料为空");
            }
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        if (PropertyRecyclerView != null) {
            PropertyRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onThrowable(Throwable t) {
        if (PropertyRecyclerView != null) {
            PropertyRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onUnauthorized() {
        if (PropertyRecyclerView != null) {
            PropertyRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        }
    }

    @Override
    public void onFinish() {
        if (PropertyRecyclerView != null) {
            PropertyRecyclerView.closeDownRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        remoteRepo.cancelRequest();
    }

    @OnClick(R.id.Details_add_Button)
    public void onClick() {
        InsuranceActivity.startIntentToAdd(getActivity());
    }

    @Override
    public void onSeleceItemPosition(InsuranceEntity entity) {
        this.entity = entity;
    }

    public InsuranceEntity getEntity() {
        return entity;
    }
}
