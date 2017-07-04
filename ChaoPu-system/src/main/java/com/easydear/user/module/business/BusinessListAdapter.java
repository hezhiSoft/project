package com.easydear.user.module.business;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.DataApplication;
import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessEntity;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.jinggan.library.utils.ILogcat;
import com.jinggan.library.utils.ISkipActivityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * author:hezhiWu <wuhezhi007@gmail.com>
 * version:V1.0
 * created at 2017/6/12 下午4:16
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved
 */
public class BusinessListAdapter extends BaseRecyclerViewAdapter<BusinessEntity> {


    public BusinessListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_business_list, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder)holder;

        /*商家Logo*/
        Glide.with(mContent).load(BuildConfig.DOMAIN+mLists.get(position).getLogo())
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.default_head_img)
                .error(R.mipmap.default_head_img)
                .into(new RoundedBitmapImageViewTarget(mContent, viewHolder.BusinessListLogoImageView));
        /*商家名称*/
        viewHolder.BusinessListNameTextView.setText(mLists.get(position).getBusinessName());
        /*商家宣传文字*/
        viewHolder.BusinessListIntroduceTextView.setText(mLists.get(position).getSlogan());
        /*商家宣传图片*/
        Glide.with(mContent).load(BuildConfig.DOMAIN+mLists.get(position).getSloganImages())
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.main_img_defaultpic_small)
                .into(viewHolder.BusinessListBgImageView);

        final List<BusinessEntity.ActivityQueryListBean> activityQueryListBean=mLists.get(position).getActivityQueryList();
        if (activityQueryListBean!=null&&activityQueryListBean.size()>0){
            viewHolder.BusinessListSubtilelogoImageView.setVisibility(View.VISIBLE);
            viewHolder.BusinessListSubtilelogoImageView.setText(activityQueryListBean.get(0).getTitle());
            viewHolder.BusinessListSubtilelogoNameTextView.setText(activityQueryListBean.get(0).getActivityName());
            viewHolder.BusinessListActionTextView.setText(activityQueryListBean.size()+"个活动");

            viewHolder.actionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        final BusinessEntity businessEntity = mLists.get(position);
        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle businessBundle = new Bundle();
                businessBundle.putString("businessNo", businessEntity.getBusinessNo());
                ISkipActivityUtil.startIntent(mContent, BusinessActivity.class, businessBundle);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.Business_List_Item_Layout)
        LinearLayout itemLayout;
        @BindView(R.id.Business_List_logo_ImageView)
        ImageView BusinessListLogoImageView;
        @BindView(R.id.Business_List_Name_TextView)
        TextView BusinessListNameTextView;
        @BindView(R.id.Business_list_Bg_ImageView)
        ImageView BusinessListBgImageView;
        @BindView(R.id.Business_list_introduce_TextView)
        TextView BusinessListIntroduceTextView;
        @BindView(R.id.Business_List_Subtilelogo_ImageView)
        TextView BusinessListSubtilelogoImageView;
        @BindView(R.id.Business_List_Subtilelogo_Name_TextView)
        TextView BusinessListSubtilelogoNameTextView;
        @BindView(R.id.Business_List_action_TextView)
        TextView BusinessListActionTextView;
        @BindView(R.id.Business_List_Action_Layout)
        LinearLayout actionLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
