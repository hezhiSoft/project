package com.easydear.user.module.dynamic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.R;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:hezhiWu <wuhezhi007@gmail.com>
 * version:V1.0
 * created at 2017/6/12 下午12:07
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved
 */
public class DynamicListAdapter extends BaseRecyclerViewAdapter<Void> {



    public DynamicListAdapter(Context context) {
        super(context);
    }


    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_dynamic_lista, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder)holder;

        Glide.with(mContent).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497129496743&di=d362e939313ee2ddf07566dd465ec3ce&imgtype=0&src=http%3A%2F%2Fp1.55tuanimg.com%2Fg1%2FM01%2FE5%2F7F%2FrBAZIlWeIBWAN3rNAAE5REe_e8c952.jpg")
                .into(viewHolder.DynamicListImageView);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.DynamicList_Logo_ImageView)
        ImageView DynamicListLogoImageView;
        @BindView(R.id.DynamicList_Title_TextView)
        TextView DynamicListTitleTextView;
        @BindView(R.id.DynamicList_Content_TextView)
        TextView DynamicListContentTextView;
        @BindView(R.id.DynamicList_ImageView)
        ImageView DynamicListImageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
