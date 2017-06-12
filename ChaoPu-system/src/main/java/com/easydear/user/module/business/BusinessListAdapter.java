package com.easydear.user.module.business;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessEntity;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-06-11
 * Time: 02:05
 * Version:1.0
 */

public class BusinessListAdapter extends BaseRecyclerViewAdapter<BusinessEntity> {



    public BusinessListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_business_list, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder)holder;
        Glide.with(mContent).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497129496743&di=d362e939313ee2ddf07566dd465ec3ce&imgtype=0&src=http%3A%2F%2Fp1.55tuanimg.com%2Fg1%2FM01%2FE5%2F7F%2FrBAZIlWeIBWAN3rNAAE5REe_e8c952.jpg")
                .into(viewHolder.BusinessListBgImageView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.Business_List_logo_ImageView)
        ImageView BusinessListLogoImageView;
        @BindView(R.id.Business_List_Name_TextView)
        TextView BusinessListNameTextView;
        @BindView(R.id.Business_list_Bg_ImageView)
        ImageView BusinessListBgImageView;
        @BindView(R.id.Business_list_introduce_TextView)
        TextView BusinessListIntroduceTextView;
        @BindView(R.id.Business_List_Subtilelogo_ImageView)
        ImageView BusinessListSubtilelogoImageView;
        @BindView(R.id.Business_List_Subtilelogo_Name_TextView)
        TextView BusinessListSubtilelogoNameTextView;
        @BindView(R.id.Business_List_action_TextView)
        TextView BusinessListActionTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
