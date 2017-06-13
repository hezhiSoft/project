package com.easydear.user.module.dynamic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-06-11
 * Time: 02:38
 * Version:1.0
 */

public class DynamicListAdapter extends BaseRecyclerViewAdapter<Void> {

    public DynamicListAdapter(Context context){
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
