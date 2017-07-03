package com.easydear.user.module.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.easydear.user.R;
import com.easydear.user.module.cards.data.InterestsEntity;
import com.easydear.user.module.cards.data.source.CardRepo;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-07-01
 * Time: 09:56
 * Version:1.0
 */

public class InterestsActivity extends BaseActivity implements RemetoRepoCallbackV2<List<InterestsEntity>>,PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener {

    @BindView(R.id.Cards_RecyclerView)
    PullToRefreshRecyclerView CardsRecyclerView;

    private InterestsListaAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        ButterKnife.bind(this);
        setToolbarTitle("我的权益");

        initRecyclerView();
    }

    private void initRecyclerView(){
        adapter=new InterestsListaAdapter(this);
        CardsRecyclerView.setRecyclerViewAdapter(adapter);

        CardsRecyclerView.setPullToRefreshListener(this);
        CardsRecyclerView.startUpRefresh();
    }

    @Override
    public void onReqStart() {

    }

    @Override
    public void onSuccess(List<InterestsEntity> data) {
        adapter.addItems(data);
    }

    @Override
    public void onFailure(int code, String msg) {
        CardsRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        if (code==404){
            showToast(msg);
        }
    }

    @Override
    public void onFinish() {
        CardsRecyclerView.closeDownRefresh();
    }

    @Override
    public void onDownRefresh() {
        CardRepo.getInstance().queryInterests(1,20,this);
    }

    @Override
    public void onPullRefresh() {

    }
}
