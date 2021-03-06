package com.xiaomai.telemarket.module.cstmr.fragment.debto;

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
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;
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

public class CusrometDebtoFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallback<List<DebtoEntity>>, CusrometDebtoAdapter.OnClickItemLisenter {

    @BindView(R.id.Details_number_TextView)
    TextView DetailsNumberTextView;
    @BindView(R.id.Edbto_recyclerView)
    PullToRefreshRecyclerView EdbtoRecyclerView;
    @BindView((R.id.Details_add_Button))
    Button addButtonDetails;
    Unbinder unbinder;

    private CusrometDebtoAdapter adapter;
    private CusrometRemoteRepo remoteRepo;

    private String cusrometId;

    private DebtoEntity debtoEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        cusrometId = getArguments().getString("id");
        remoteRepo = CusrometRemoteRepo.getInstance();
        adapter = new CusrometDebtoAdapter(getContext());
        adapter.setListenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cusromet_edbto, null);
        unbinder = ButterKnife.bind(this, rootView);
        addButtonDetails.setText("添加负债");
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        EdbtoRecyclerView.setRecyclerViewAdapter(adapter);
        EdbtoRecyclerView.setMode(PullToRefreshRecyclerView.Mode.DISABLED);
        EdbtoRecyclerView.setPullToRefreshListener(this);
//        EdbtoRecyclerView.startUpRefresh();
        remoteRepo.queryCusrometDebtoLists(cusrometId, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        remoteRepo.cancelRequest();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onUpdateUIData(EventBusValues values){
        if (values.getWhat()==0x202){
            EdbtoRecyclerView.startUpRefresh();
        }
        if (values.getWhat()==0x890){
            cusrometId=((CusrometListEntity)values.getObject()).getID();
            remoteRepo.queryCusrometDebtoLists(cusrometId, this);
        }
    }
    @Override
    public void onDownRefresh() {
        remoteRepo.queryCusrometDebtoLists(cusrometId, this);
    }

    @Override
    public void onPullRefresh() {

    }

    @OnClick(R.id.Details_add_Button)
    public void onClick() {
        DebtoActivity.startIntentToAdd(getActivity());
    }

    @Override
    public void onSuccess(List<DebtoEntity> data) {
        adapter.clearList();
        if (data != null && data.size() > 0) {
            DetailsNumberTextView.setText("共" + data.size() + "条负债信息");
            if (EdbtoRecyclerView != null) {
                EdbtoRecyclerView.setEmptyTextViewVisiblity(View.GONE);
            }
            adapter.addItems(data);
            ((CusrometDetailsActivity) getActivity()).getTabLayout().setTagNumber(1, data.size());
        } else {
            DetailsNumberTextView.setText("负债信息");
            if (EdbtoRecyclerView != null) {
                EdbtoRecyclerView.setPageHint(R.mipmap.icon_data_empty, "资料为空");
            }
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        if (EdbtoRecyclerView != null) {
            EdbtoRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onThrowable(Throwable t) {
        if (EdbtoRecyclerView != null) {
            EdbtoRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onUnauthorized() {
        if (EdbtoRecyclerView != null) {
            EdbtoRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        }
    }

    @Override
    public void onFinish() {
        if (EdbtoRecyclerView != null) {
            EdbtoRecyclerView.closeDownRefresh();
        }
    }

    @Override
    public void onSeleceItemPosition(DebtoEntity entity) {
        this.debtoEntity = entity;
    }

    public DebtoEntity getEntity() {
        return debtoEntity;
    }
}
