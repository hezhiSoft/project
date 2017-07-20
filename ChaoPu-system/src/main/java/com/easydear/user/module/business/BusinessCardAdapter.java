package com.easydear.user.module.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.easydear.user.module.cards.InterestDetailActivity;
import com.easydear.user.module.cards.data.source.CardRepo;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.jinggan.library.utils.ILogcat;
import com.jinggan.library.utils.ISkipActivityUtil;

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
            viewHolder.cardName.setText(entity.getCardName());
            viewHolder.cardPrice.setText("¥" + entity.getCardPrice());
            viewHolder.cardDifference.setText("限时直降 ¥" + entity.getDifference());
            viewHolder.cardEndTime.setText("有效期至:  " + entity.getCardEndTime());
            viewHolder.cardReceive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("cardNo", entity.getCardNo());
                    ISkipActivityUtil.startIntent(mContext, InterestDetailActivity.class, bundle);
                }
            });
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.busi_shop_card_name)
        TextView cardName;
        @BindView(R.id.busi_shop_card_price)
        TextView cardPrice;
        @BindView(R.id.busi_shop_card_differ)
        TextView cardDifference;
        @BindView(R.id.busi_shop_card_endTime)
        TextView cardEndTime;
        @BindView(R.id.busi_shop_card_receive)
        TextView cardReceive;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(ItemViewHolder.this, view);
        }
    }
}
