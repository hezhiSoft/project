package com.jinggan.library.ui.widget.pullRefreshRecyler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinggan.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView.Adapter基类
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/20 17:16
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static String TAG = "BaseRecyclerViewAdapter";

    /**
     * 正在刷新
     */
    public final static int REFRESH = 0x10;
    /**
     * 正在加载更多
     */
    public final static int LOADING_MORE = 0x20;
    /**
     * 加载状态 REFRESH or LOADING_MORE
     */
    private int loadState;
    /**
     * 普通Item View
     */
    private static final int TYPE_ITEM = 0;
    /**
     * 顶部FootView
     */
    private static final int TYPE_FOOTER = 1;

    protected Context mContent;
    protected LayoutInflater inflater;

    /*资源集合*/
    protected List<T> mLists = new ArrayList<>();
    /*item 点击事件*/
    protected OnRecyclerViewItemClickListener listener;
    /*item 长按事件*/
    protected OnRecyclerViewItemLongClickListener longListener;
    /*刷新模式*/
    private PullToRefreshRecyclerView.Mode mode;
    /*是否正在刷新*/
    public boolean isRefresh = false;
    /*是否加载更多*/
    public boolean isLoadMore = true;

    public BaseRecyclerViewAdapter(Context context) {
        this.mContent = context;
        this.mode = PullToRefreshRecyclerView.Mode.DISABLED;
        if (context != null) {
            this.inflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getItemViewType(int position) {
        /* 最后一个item设置为footerView*/
        if (getItemCount() >= 20 && position + 1 == getItemCount() && (PullToRefreshRecyclerView.Mode.BOTH == mode || PullToRefreshRecyclerView.Mode.PULL_FROM_END == mode) && !isRefresh && isLoadMore) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return onCreateBaseViewHolder(parent, viewType);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(inflater.inflate(R.layout.item_base_recycler_view_adapter_foot, parent, false));
        } else {
            return new FooterViewHolder(inflater.inflate(R.layout.item_base_recycler_view_adapter_default, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_ITEM) {
            onBindBaseViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        int count = getList().size();
        if (count >= 20 && (PullToRefreshRecyclerView.Mode.BOTH == mode || PullToRefreshRecyclerView.Mode.PULL_FROM_END == mode) && !isRefresh && isLoadMore) {
            return ++count;
        } else if (count > 0) {
            return count;
        } else {
            return 0;
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public abstract RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position);

    /**
     * 添加资源
     *
     * @param list
     */
    public void addItems(List<T> list) {
        //别忘了将新数据给Adapter
        mLists.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 添加资源
     *
     * @param list
     * @param position
     */
    public void addItems(List<T> list, int position) {
        mLists.addAll(position, list);
        notifyDataSetChanged();
    }

    /**
     * 添加资源
     *
     * @param t
     */
    public void addItem(T t) {
        mLists.add(t);
        notifyDataSetChanged();
    }

    /**
     * 添加资源
     *
     * @param t
     * @param position
     */
    public void addItem(T t, int position) {
        mLists.add(position, t);
        notifyDataSetChanged();
    }

    /**
     * 返回资源
     *
     * @return
     */
    public List<T> getList() {
        return mLists;
    }

    /**
     * 清除资源
     */
    public void clearList() {
        mLists.clear();
        notifyDataSetChanged();
    }

    /**
     * 设置刷新模式
     *
     * @param mode
     */
    public void setMode(PullToRefreshRecyclerView.Mode mode) {
        this.mode = mode;
    }

    /**
     * 设置加载模式
     *
     * @param loadState
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    /**
     * 获取加载模式
     *
     * @return
     */
    public int getLoadState() {
        return loadState;
    }

    /**
     * 设置是否正在下拉刷新
     *
     * @param refresh
     */
    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
        notifyDataSetChanged();
    }

    /**
     * 设置是否加载更多
     *
     * @param loadMore
     */
    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
        notifyDataSetChanged();
    }

    /**
     * 设置点击事件监听器
     *
     * @param listener
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 设置长按事件监听器
     *
     * @param longListener
     */
    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener longListener) {
        this.longListener = longListener;
    }

    /**
     * @author hezhiWu
     * @version V1.0
     * @Package com.yunwei.water.ui.biz.interfac
     * @Description:RecyclerView 点击事件
     * @date 2016/9/12 16:43
     */
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data, int position);
    }

    /**
     * @author hezhiWu
     * @version V1.0
     * @Package com.yunwei.water.ui.biz.interfac
     * @Description:RecyclerView 长按事件
     * @date 2016/9/12 16:43
     */
    public interface OnRecyclerViewItemLongClickListener {
        void onLongItemClick(View view, Object data, int position);
    }
}
