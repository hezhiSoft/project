package com.easydear.user.module.business;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LJH on 2017/7/6.
 */

public class BusinessCardAdapter extends BaseRecyclerViewAdapter<BusinessDetailEntity.CardListBean> {

    private Context mContext;
    private List<BusinessDetailEntity.CardListBean> mCardList;

    public BusinessCardAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        BusinessCardAdapter.ItemViewHolder viewHolder = new BusinessCardAdapter.ItemViewHolder(inflater.inflate(R.layout.item_busi_card_layout, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        final BusinessDetailEntity.CardListBean entity = mLists.get(position);
        if (entity != null) {
            viewHolder.cardNameTV.setText(entity.getCardName());
            viewHolder.cardPriceTV.setText("¥" + entity.getCardPrice());
            viewHolder.cardDifferenceTV.setText("限时直降 ¥" + entity.getDifference());
            viewHolder.cardValidateTV.setText("有效期至:  " + entity.getCardEndTime());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.busi_shop_card_name)
        TextView cardNameTV;
        @BindView(R.id.busi_shop_card_price)
        TextView cardPriceTV;
        @BindView(R.id.busi_shop_card_differ)
        TextView cardDifferenceTV;
        @BindView(R.id.busi_shop_card_validate)
        TextView cardValidateTV;
        @BindView(R.id.busi_shop_card_receive)
        TextView cardReceiveTV;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(ItemViewHolder.this, view);
        }
    }
}
