package com.easydear.user.module.cards;

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
import com.easydear.user.module.cards.data.InterestsEntity;
import com.easydear.user.module.pay.InterestPayActivity;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.jinggan.library.utils.ISkipActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-07-01
 * Time: 10:05
 * Version:1.0
 */

public class InterestsListaAdapter extends BaseRecyclerViewAdapter<InterestsEntity> {


    public InterestsListaAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_interests_list, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder)holder;

         /*商家Logo*/
        Glide.with(mContent).load(BuildConfig.DOMAIN + mLists.get(position).getLogo())
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.default_head_img)
                .error(R.mipmap.default_head_img)
                .into(new RoundedBitmapImageViewTarget(mContent, viewHolder.ItemInterestsLogo));

        viewHolder.ItemInterestsBusinessName.setText(mLists.get(position).getBusinessName());
        viewHolder.ItemInterestsCardName.setText(mLists.get(position).getCardName());

        viewHolder.ItemInterestsCardOldPrice.setText("限时直降￥ "+mLists.get(position).getCardOldPrice());
        viewHolder.ItemInterestsCardPrice.setText("￥ "+mLists.get(position).getCardPrice());

        viewHolder.ItemInterestsCardEndTime.setText("有效期至"+mLists.get(position).getCardEndTime());
        viewHolder.ItemInterestsCardTransactionNo.setText("序列号："+mLists.get(position).getCardTransactionNo());

        viewHolder.ItemInterestsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        final int pos = position;
        viewHolder.ItemInterestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final InterestsEntity entity = mLists.get(pos);
                Bundle bundle = new Bundle();
                bundle.putSerializable("interest_entity", entity);
                ISkipActivityUtil.startIntent(mContent, InterestPayActivity.class,bundle);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.Item_Interests_Logo)
        ImageView ItemInterestsLogo;
        @BindView(R.id.Item_Interests_BusinessName)
        TextView ItemInterestsBusinessName;
        @BindView(R.id.Item_Interests_CardName)
        TextView ItemInterestsCardName;
        @BindView(R.id.Item_Interests_CardOldPrice)
        TextView ItemInterestsCardOldPrice;
        @BindView(R.id.Item_Interests_CardPrice)
        TextView ItemInterestsCardPrice;
        @BindView(R.id.Item_Interests_CardEndTime)
        TextView ItemInterestsCardEndTime;
        @BindView(R.id.Item_Interests_Button)
        Button ItemInterestsButton;
        @BindView(R.id.Item_Interests_CardTransactionNo)
        TextView ItemInterestsCardTransactionNo;
        @BindView(R.id.Item_Interests_layout)
        LinearLayout ItemInterestsLayout;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
