package com.xiaomai.telemarket.module.cstmr.fragment.follow;

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
import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.DebtoActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 22:21
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometFolloFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallback<List<FollowEntity>> {

    @BindView(R.id.Follow_recyclerView)
    PullToRefreshRecyclerView FollowRecyclerView;
    @BindView(R.id.Details_number_TextView)
    TextView DetailsNumberTextView;
    @BindView((R.id.Details_add_Button))
    Button addButtonDetails;

    private CusrometFollowAdapter adapter;
    private CusrometRemoteRepo remoteRepo;

    private String cusrometId;

    private FollowEntity debtoEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        cusrometId = getArguments().getString("id");
        remoteRepo = CusrometRemoteRepo.getInstance();
        adapter = new CusrometFollowAdapter(getContext());
        adapter.setListenter(new CusrometFollowAdapter.OnClickItemLisenter() {
            @Override
            public void onSeleceItemPosition(FollowEntity entity) {
                debtoEntity = entity;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cusromet_follow, null);
        ButterKnife.bind(this, rootView);
        addButtonDetails.setText("添加跟进");
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        FollowRecyclerView.setRecyclerViewAdapter(adapter);
        FollowRecyclerView.setMode(PullToRefreshRecyclerView.Mode.DISABLED);
        FollowRecyclerView.setPullToRefreshListener(this);
//        FollowRecyclerView.startUpRefresh();
        remoteRepo.queryCusrometFollowLists(cusrometId, this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    @Subscribe
    public void onUpdateUIData(EventBusValues values){
        if (values.getWhat()==0x208){
            remoteRepo.queryCusrometFollowLists(cusrometId, this);
        }
        if (values.getWhat()==0x890){
            cusrometId=((CusrometListEntity)values.getObject()).getID();
            remoteRepo.queryCusrometFollowLists(cusrometId, this);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        remoteRepo.cancelRequest();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDownRefresh() {
        remoteRepo.queryCusrometFollowLists(cusrometId, this);
    }

    @Override
    public void onPullRefresh() {

    }

    @OnClick(R.id.Details_add_Button)
    public void onClick() {
        FollowActivity.startIntentToAdd(getActivity());
    }

    @Override
    public void onSuccess(List<FollowEntity> data) {
        adapter.clearList();
        if (data != null && data.size() > 0) {
            DetailsNumberTextView.setText("共" + data.size() + "条跟进明细");
            if (FollowRecyclerView != null) {
                FollowRecyclerView.setEmptyTextViewVisiblity(View.GONE);
            }
            adapter.addItems(data);
            ((CusrometDetailsActivity) getActivity()).getTabLayout().setTagNumber(7, data.size());
        } else {
            DetailsNumberTextView.setText("跟进明细");
            if (FollowRecyclerView != null) {
                FollowRecyclerView.setPageHint(R.mipmap.icon_data_empty, "资料为空");
            }
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        if (FollowRecyclerView != null) {
            FollowRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onThrowable(Throwable t) {
        if (FollowRecyclerView != null) {
            FollowRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onUnauthorized() {
        if (FollowRecyclerView != null) {
            FollowRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        }
    }

    @Override
    public void onFinish() {
        if (FollowRecyclerView != null) {
            FollowRecyclerView.closeDownRefresh();
        }
    }

    public FollowEntity getEntity() {
        return debtoEntity;
    }
}
