package com.xiaomai.telemarket.module.cstmr.fragment.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.CusrometDetailsActivity;
import com.xiaomai.telemarket.module.cstmr.data.CompanyEntity;
import com.xiaomai.telemarket.module.cstmr.data.InsuranceEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;
import com.xiaomai.telemarket.module.cstmr.fragment.insurance.CusrometInsuranceAdapter;

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

public class CusrometCompanyFragment extends BaseFragment implements CusrometCompanyAdapter.OnClickItemLisenter, PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallback<List<CompanyEntity>> {

    @BindView(R.id.Details_number_TextView)
    TextView DetailsNumberTextView;
    @BindView(R.id.Company_recyclerView)
    PullToRefreshRecyclerView PropertyRecyclerView;
    Unbinder unbinder;

    private CusrometCompanyAdapter adapter;
    private CusrometRemoteRepo remoteRepo;

    private String cusrometId;

    private CompanyEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new CusrometCompanyAdapter(getContext());
        cusrometId = getArguments().getString("id");
        remoteRepo = CusrometRemoteRepo.getInstance();
        adapter.setListenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cusromet_company, null);
        unbinder = ButterKnife.bind(this, rootView);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        PropertyRecyclerView.setRecyclerViewAdapter(adapter);
        PropertyRecyclerView.setMode(PullToRefreshRecyclerView.Mode.DISABLED);
        PropertyRecyclerView.setPullToRefreshListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        PropertyRecyclerView.startUpRefresh();
    }

    @Override
    public void onDownRefresh() {
        remoteRepo.queryCusrometCompanyLists(cusrometId,this);
    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onSuccess(List<CompanyEntity> data) {
        if (data != null && data.size() > 0) {
            adapter.clearList();
            DetailsNumberTextView.setText("共" + data.size() + "条公司信息");
            if (PropertyRecyclerView!=null){
                PropertyRecyclerView.setEmptyTextViewVisiblity(View.GONE);
            }
            adapter.addItems(data);
            ((CusrometDetailsActivity)getActivity()).getTabLayout().setTagNumber(5,data.size());
        } else {
            DetailsNumberTextView.setText("公司信息");
            if (PropertyRecyclerView!=null){
                PropertyRecyclerView.setPageHint(R.mipmap.icon_page_null,"资料为空");
            }
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        if (PropertyRecyclerView!=null) {
            PropertyRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onThrowable(Throwable t) {
        if (PropertyRecyclerView!=null) {
            PropertyRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onUnauthorized() {
        if (PropertyRecyclerView!=null) {
            PropertyRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        }
    }

    @Override
    public void onFinish() {
        if (PropertyRecyclerView!=null) {
            PropertyRecyclerView.closeDownRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        remoteRepo.cancelRequest();
    }

    @OnClick(R.id.Details_add_Button)
    public void onClick() {
        CompanyActivity.startIntentToAdd(getActivity());
    }

    @Override
    public void onSeleceItemPosition(CompanyEntity entity) {
        this.entity=entity;
    }

    public CompanyEntity getEntity(){
        return entity;
    }
}
