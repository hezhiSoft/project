package com.easydear.user.module.order;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.easydear.user.R;
import com.easydear.user.module.order.data.OrderEntity;
import com.easydear.user.module.order.data.source.OrderRepo;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的订单
 */
public class OrderActivity extends BaseActivity implements RemetoRepoCallbackV2<List<OrderEntity>>,PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener{

    @BindView(R.id.Order_RecyclerView)
    PullToRefreshRecyclerView OrderRecyclerView;

    private OrderListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        setToolbarTitle("我的订单");

        initRecyclerView();
    }

    private void initRecyclerView(){
        adapter=new OrderListAdapter(this);
        OrderRecyclerView.setRecyclerViewAdapter(adapter);

        OrderRecyclerView.setPullToRefreshListener(this);
        OrderRecyclerView.startUpRefresh();
    }

    @Override
    public void onDownRefresh() {
        OrderRepo.getInstance().queryOrders(1,20,this);
    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onReqStart() {

    }

    @Override
    public void onSuccess(List<OrderEntity> data) {
        adapter.addItems(data);
    }

    @Override
    public void onFailure(int code, String msg) {
        OrderRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onFinish() {
        OrderRecyclerView.closeDownRefresh();
    }
}
