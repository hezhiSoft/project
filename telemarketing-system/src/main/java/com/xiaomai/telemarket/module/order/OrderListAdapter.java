package com.xiaomai.telemarket.module.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.order.data.OrderEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/14 18:51
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class OrderListAdapter extends BaseRecyclerViewAdapter<OrderEntity> {



    public OrderListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHoldr viewHoldr=(ViewHoldr)holder;
        viewHoldr.OrdrListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoldr(inflater.inflate(R.layout.item_order_list, parent, false));
    }

    class ViewHoldr extends RecyclerView.ViewHolder {
        @BindView(R.id.Order_code_TextView)
        TextView OrderCodeTextView;
        @BindView(R.id.Order_status_TextView)
        TextView OrderStatusTextView;
        @BindView(R.id.Order_name_TextView)
        TextView OrderNameTextView;
        @BindView(R.id.Order_type_TextView)
        TextView OrderTypeTextView;
        @BindView(R.id.Order_IntentionQuota_TextView)
        TextView OrderIntentionQuotaTextView;
        @BindView(R.id.Order_ActualQuota_TextView)
        TextView OrderActualQuotaTextView;
        @BindView(R.id.OrdrList_layout)
        LinearLayout OrdrListLayout;

        public ViewHoldr(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
