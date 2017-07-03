package com.easydear.user.module.order;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.R;
import com.easydear.user.module.order.data.OrderEntity;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.IStringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-07-01
 * Time: 08:54
 * Version:1.0
 */

public class OrderListAdapter extends BaseRecyclerViewAdapter<OrderEntity> {




    public OrderListAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

         /*商家Logo*/
        Glide.with(mContent).load(BuildConfig.DOMAIN + mLists.get(position).getLogo())
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.default_head_img)
                .error(R.mipmap.default_head_img)
                .into(new RoundedBitmapImageViewTarget(mContent, viewHolder.OrderItemBuinessLogo));

        viewHolder.ItemOrderBusinessName.setText(mLists.get(position).getBusinessName());

        viewHolder.ItemOrderCardName.setText(mLists.get(position).getCardName());

        viewHolder.ItemOrderBuyAmount.setText("￥ "+mLists.get(position).getBuyAmount());

        viewHolder.ItemOrderValidityCardEndTime.setText("有效期至"+mLists.get(position).getCardEndTime());

        viewHolder.ItemOrderCardSize.setText(mLists.get(position).getCardSize());

        int totalPirce= IStringUtils.toInt(mLists.get(position).getBuyAmount())*IStringUtils.toInt(mLists.get(position).getCardSize());
        viewHolder.ItemOrderTotal.setText("合计：￥ "+totalPirce);

        String status=mLists.get(position).getStatus();
        if ("待付款".equals(status)){
            viewHolder.ItemOrderTuiKuanButton.setVisibility(View.GONE);
        }else {
            viewHolder.ItemOrderTuiKuanButton.setVisibility(View.VISIBLE);
        }

        viewHolder.ItemOrderStatusButton.setText(status);

        viewHolder.ItemOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("OrderNo",mLists.get(position).getOrderNo());
                ISkipActivityUtil.startIntent(mContent,OrderDetailsActivity.class,bundle);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_order_list, parent, false));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Item_Order_BusinessName)
        TextView ItemOrderBusinessName;
        @BindView(R.id.Order_Item_Buiness_Logo)
        ImageView OrderItemBuinessLogo;
        @BindView(R.id.Item_Order_CardName)
        TextView ItemOrderCardName;
        @BindView(R.id.Item_Order_BuyAmount)
        TextView ItemOrderBuyAmount;
        @BindView(R.id.Item_top_layout)
        LinearLayout ItemTopLayout;
        @BindView(R.id.Item_Order_validity_CardEndTime)
        TextView ItemOrderValidityCardEndTime;
        @BindView(R.id.Item_Order_CardSize)
        TextView ItemOrderCardSize;
        @BindView(R.id.Item_Order_total)
        TextView ItemOrderTotal;
        @BindView(R.id.Item_Order_Status_Button)
        Button ItemOrderStatusButton;
        @BindView(R.id.Item_Order_tuiKuan_Button)
        Button ItemOrderTuiKuanButton;
        @BindView(R.id.Item_Order_Layout)
        LinearLayout ItemOrderLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
