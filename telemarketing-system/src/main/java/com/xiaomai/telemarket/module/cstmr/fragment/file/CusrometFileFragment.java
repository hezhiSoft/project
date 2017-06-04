package com.xiaomai.telemarket.module.cstmr.fragment.file;

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
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.DebtoActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 22:21
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometFileFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallback<List<FileEntity>>, CusrometFileAdapter.OnClickItemLisenter {

    @BindView(R.id.Details_number_TextView)
    TextView DetailsNumberTextView;
    @BindView(R.id.File_recyclerView)
    PullToRefreshRecyclerView EdbtoRecyclerView;
    @BindView((R.id.Details_add_Button))
    Button addButtonDetails;
    Unbinder unbinder;

    private CusrometFileAdapter adapter;
    private CusrometRemoteRepo remoteRepo;

    private String cusrometId;

    private FileEntity debtoEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        cusrometId = getArguments().getString("id");
        remoteRepo = CusrometRemoteRepo.getInstance();
        adapter = new CusrometFileAdapter(getContext());
        adapter.setListenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cusromet_file, null);
        unbinder = ButterKnife.bind(this, rootView);
        addButtonDetails.setText("添加文件");
        initRecyclerView();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initRecyclerView() {
        EdbtoRecyclerView.setRecyclerViewAdapter(adapter);
        EdbtoRecyclerView.setMode(PullToRefreshRecyclerView.Mode.DISABLED);
        EdbtoRecyclerView.setPullToRefreshListener(this);
//        EdbtoRecyclerView.startUpRefresh();
        remoteRepo.queryCusrometFileLists(cusrometId, this);
    }

    @Subscribe
    public void onUpdateUIData(EventBusValues values){
        if (values.getWhat()==0x207){
            remoteRepo.queryCusrometFileLists(cusrometId, this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        remoteRepo.cancelRequest();
    }

    @Override
    public void onDownRefresh() {
        remoteRepo.queryCusrometFileLists(cusrometId, this);
    }

    @Override
    public void onPullRefresh() {

    }

    @OnClick(R.id.Details_add_Button)
    public void onClick() {
        FileActivity.startIntentToAdd(getActivity());
    }

    @Override
    public void onSuccess(List<FileEntity> data) {
        if (data != null && data.size() > 0) {
            adapter.clearList();
            DetailsNumberTextView.setText("共" + data.size() + "条文件资料");
            if (EdbtoRecyclerView!=null){
                EdbtoRecyclerView.setEmptyTextViewVisiblity(View.GONE);
            }
            adapter.addItems(data);
            ((CusrometDetailsActivity)getActivity()).getTabLayout().setTagNumber(6,data.size());
        } else {
            DetailsNumberTextView.setText("文件资料");
            if (EdbtoRecyclerView!=null){
                EdbtoRecyclerView.setPageHint(R.mipmap.icon_page_null,"资料为空");
            }
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        if (EdbtoRecyclerView!=null) {
            EdbtoRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onThrowable(Throwable t) {
        if (EdbtoRecyclerView!=null) {
            EdbtoRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onUnauthorized() {
        if (EdbtoRecyclerView!=null) {
            EdbtoRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        }
    }

    @Override
    public void onFinish() {
        if (EdbtoRecyclerView!=null) {
            EdbtoRecyclerView.closeDownRefresh();
        }
    }

    @Override
    public void onSeleceItemPosition(FileEntity entity) {
        this.debtoEntity = entity;
    }

    public FileEntity getEntity() {
        return debtoEntity;
    }
}
