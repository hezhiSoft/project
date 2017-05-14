package com.jinggan.library.ui.widget.pullRefreshRecyler;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinggan.library.R;

import butterknife.ButterKnife;

/**
 * 上拉下拉刷新RecyclerView
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/20 17:15
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class PullToRefreshRecyclerView extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener {
    private final String TAG = getClass().getSimpleName();

    private RecyclerView pullToRefreshRecyclerView;
    private SwipeRefreshLayout pullToRefreshSwipeRefreshLayout;
    private TextView pullToRefreshEmptyTextView;

    private boolean isLoading = false;
    private boolean isLoadMore = true;

    private LinearLayoutManager linearLayoutManager;
    private PullToRefreshRecyclerViewListener listener;
    private BaseRecyclerViewAdapter adapter;

    private Mode mode;

    public PullToRefreshRecyclerView(Context context) {
        super(context, null);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attri) {
        super(context, attri);
        this.mode = Mode.getDefault();
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.pulltorefresh_recyclerview, null);
        ButterKnife.bind(this, rootView);
        pullToRefreshRecyclerView = ButterKnife.findById(rootView, R.id.pullToRefresh_recyclerView);
        pullToRefreshSwipeRefreshLayout = ButterKnife.findById(rootView, R.id.pullToRefresh_swipeRefreshLayout);
        pullToRefreshEmptyTextView = ButterKnife.findById(rootView, R.id.pullToRefresh_empty_textView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        pullToRefreshRecyclerView.setLayoutManager(linearLayoutManager);
        pullToRefreshRecyclerView.addOnScrollListener(new RecyclerViewOnScrollListener());
        pullToRefreshSwipeRefreshLayout.setOnRefreshListener(this);
        addView(rootView);
    }

    /**
     * 设置RecyclerView com.yixia.videoeditor.adapter
     *
     * @param adapter
     */
    public void setRecyclerViewAdapter(BaseRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        pullToRefreshRecyclerView.setAdapter(adapter);
    }

    /**
     * 设置内容为空
     */
    public void setEmptyTextViewVisiblity(int visiblity) {
        pullToRefreshEmptyTextView.setVisibility(visiblity);
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setPullToRefreshListener(PullToRefreshRecyclerViewListener listener) {
        this.listener = listener;
    }

    /**
     * 启动刷新
     */
    public void startUpRefresh() {
        pullToRefreshSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                pullToRefreshSwipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();
    }

    /**
     * 取消下拉刷新
     */
    public void closeDownRefresh() {
        pullToRefreshSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                pullToRefreshSwipeRefreshLayout.setRefreshing(false);
                setLoading(false);
            }
        });
    }

    /**
     * 设置模式
     *
     * @param mode
     */
    public void setMode(Mode mode) {
        this.mode = mode;
        if (mode == Mode.DISABLED || mode == Mode.PULL_FROM_END) {
            pullToRefreshSwipeRefreshLayout.setEnabled(false);
        }
        adapter.setMode(mode);
    }

    /**
     * 设置加载状态
     *
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        pullToRefreshSwipeRefreshLayout.setEnabled(!loading);
    }

    /**
     * 设置是否加载更多
     */
    public void onLoadMoreFinish() {
        isLoadMore = false;
        adapter.setLoadMore(false);
    }

    @Override
    public void onRefresh() {
        /*判断是否正在刷新中*/
        if (isLoading) {
            pullToRefreshSwipeRefreshLayout.setEnabled(false);
        } else if (listener != null && !isLoading) {
            adapter.setLoadState(BaseRecyclerViewAdapter.REFRESH);
            setLoading(true);
            isLoadMore = true;
            adapter.setLoadMore(true);
            adapter.setLoadState(BaseRecyclerViewAdapter.REFRESH);
            listener.onDownRefresh();
        }
    }

    /**
     * RecyclerView滑动监听
     */
    private class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            /*加载更多完成*/
            if (!isLoadMore) {
                return;
            }
            /*判断是否正在刷新中*/
            if (pullToRefreshSwipeRefreshLayout.isRefreshing()) {
                adapter.setRefresh(true);
                return;
            }
            int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            int totalItemCount = linearLayoutManager.getItemCount();
            if (mode == Mode.BOTH || mode == Mode.PULL_FROM_END) {
                // dy>0 表示向下滑动
                if (lastVisibleItem == totalItemCount - 2 && dy > 0) {
                    if (listener != null && !isLoading) {
                        adapter.setLoadState(BaseRecyclerViewAdapter.LOADING_MORE);
                        adapter.setRefresh(false);
                        setLoading(true);
                        adapter.setLoadState(BaseRecyclerViewAdapter.LOADING_MORE);
                        listener.onPullRefresh();
                    }
                }
            }
        }
    }

    /**
     * 模式
     */
    public static enum Mode {
        /**
         * 禁用所有拉刷新的手势和刷新处理
         */
        DISABLED(0x0),

        /**
         * 只允许用户下拉刷新
         */
        PULL_FROM_START(0x1),

        /**
         * 只允许用户上拉拉刷新.
         */
        PULL_FROM_END(0x2),

        /**
         * 允许用户上拉刷新及下拉刷新
         */
        BOTH(0x3);


        static Mode getDefault() {
            return DISABLED;
        }

        private int mIntValue;

        Mode(int modeInt) {
            mIntValue = modeInt;
        }
    }

    /**
     * 刷新监听器
     */
    public interface PullToRefreshRecyclerViewListener {
        void onDownRefresh();

        void onPullRefresh();
    }
}
