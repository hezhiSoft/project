package com.xiaomai.telemarket.module.cstmr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.CusrometEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/15 22:04
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometManagementAdapter extends BaseRecyclerViewAdapter<CusrometEntity> {


    public CusrometManagementAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_cusromet_list, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder)holder;
        viewHolder.ItemCusrometLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ISkipActivityUtil.startIntent(mContent,CusrometDetailsActivity.class);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ItemCusromet_UserName)
        TextView ItemCusrometUserName;
        @BindView(R.id.ItemCusromet_status)
        TextView ItemCusrometStatus;
        @BindView(R.id.ItemCusromet_lastTime)
        TextView ItemCusrometLastTime;
        @BindView(R.id.ItemCusromet_remark)
        TextView ItemCusrometRemark;
        @BindView(R.id.ItemCusromet_layout)
        LinearLayout ItemCusrometLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
