package com.xiaomai.telemarket.module.cstmr;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.fragment.follow.FollowActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/15 22:04
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometManagementAdapter extends BaseRecyclerViewAdapter<CusrometListEntity> {

    private int flag;

    public CusrometManagementAdapter(Context context) {
        super(context);
    }

    public CusrometManagementAdapter(Context context,int flag) {
        super(context);
        this.flag=flag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_cusromet_list, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.ItemCusrometUserName.setText(mLists.get(position).getCustomerName());
        viewHolder.ItemCusrometLastTime.setText(mLists.get(position).getFollowDate().replaceAll("T", " "));
        viewHolder.ItemCusrometRemark.setText(mLists.get(position).getRemark());
        /*意向状态*/
        int InterestedStatus = mLists.get(position).getInterestedStatus();
        if (InterestedStatus == Constant.Description.NoInterested.getValue()) {
            Drawable drawable = mContent.getResources().getDrawable(R.mipmap.icon_order_stauts_not);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            viewHolder.ItemCusrometStatus.setCompoundDrawables(drawable, null, null, null);
            viewHolder.ItemCusrometStatus.setText("无意向");
        } else if (InterestedStatus == Constant.Description.YesInterested.getValue()) {
            Drawable drawable = mContent.getResources().getDrawable(R.mipmap.icon_order_finish_status);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            viewHolder.ItemCusrometStatus.setCompoundDrawables(drawable, null, null, null);
            viewHolder.ItemCusrometStatus.setText("有意向");
        } else {
            Drawable drawable = mContent.getResources().getDrawable(R.mipmap.icon_order_stauts_null);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            viewHolder.ItemCusrometStatus.setCompoundDrawables(drawable, null, null, null);
            viewHolder.ItemCusrometStatus.setText("未知意向");
        }
        viewHolder.ItemCusrometLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==1){
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("entity",mLists.get(position));
                    ISkipActivityUtil.startIntent(mContent, CusrometDetailsActivity.class,bundle);
                    ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_FROM_HOME_GROUP_DIALING, false);
                }else if (flag==2){
                    FollowActivity.startIntentToQuery((Activity)mContent,mLists.get(position).getCustomerTel(),mLists.get(position).getID());
                }

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
            ButterKnife.bind(this, view);
        }
    }
}
