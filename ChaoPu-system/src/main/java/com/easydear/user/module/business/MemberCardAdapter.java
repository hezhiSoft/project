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
import com.easydear.user.module.business.data.CardItemEntity;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LJH on 2017/7/3.
 */

public class MemberCardAdapter extends BaseRecyclerViewAdapter<CardItemEntity> {

    private Context mContext;
    private String mBusinessLogo;

    public MemberCardAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        MemberCardAdapter.ItemViewHolder viewHolder = new MemberCardAdapter.ItemViewHolder(inflater.inflate(R.layout.item_card_layout, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        final CardItemEntity entity = mLists.get(position);
        if (entity != null) {
            viewHolder.cardNameText.setText(entity.getCardName());
            viewHolder.cardSerialNoText.setText("序列号:  " + entity.getCardNo());
        if (entity==null){
            return;
        }
        viewHolder.cardNameText.setText(entity.getCardName());
        viewHolder.cardSerialNoText.setText("序列号:  " + entity.getCardNo());
//        viewHolder.cardValidDateText.setText(entity.get);
//        Glide.with(mContent).load(BuildConfig.DOMAI + entity.get()).into(viewHolder.logoView);
        }
        Glide.with(mContext).load(BuildConfig.DOMAIN + mBusinessLogo)
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .into(viewHolder.logoView);
    }

    public void setBusinessLogo(String businessLogo) {
        mBusinessLogo = businessLogo;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.business_card_logo)
        ImageView logoView;
        @BindView(R.id.business_card_name)
        TextView cardNameText;
        @BindView(R.id.business_card_business_name)
        TextView cardBusinessNameText;
        @BindView(R.id.business_card_serialNo)
        TextView cardSerialNoText;
        @BindView(R.id.business_card_validity)
        TextView cardValidDateText;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(ItemViewHolder.this, view);
        }
    }
}
