package com.xiaomai.telemarket.module.function.callOut;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.function.data.CallOutStaticsEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 用户外呼统计
 * @createtime 20/05/2017 5:20 PM
 **/
public class UserStatisticListAdapter extends BaseRecyclerViewAdapter<CallOutStaticsEntity> {

    public UserStatisticListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_user_statistic_list, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvUsername.setText(mLists.get(position).getCustomerName());
        viewHolder.userTel.setText(mLists.get(position).getCustomerTel());
        viewHolder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.layoutDetail.setVisibility(viewHolder.layoutDetail.getVisibility() == View.VISIBLE?View.GONE:View.VISIBLE);
            }
        });
        if (viewHolder.layoutDetail.getChildCount() <= 0) {
            View statisticView = inflater.inflate(R.layout.item_user_statistic_detail, null);
            setDetailLayout(statisticView, mLists.get(position));
            viewHolder.layoutDetail.addView(statisticView);
        } else {
            setDetailLayout(viewHolder.layoutDetail, mLists.get(position));
        }
    }

    private void setDetailLayout(View view, CallOutStaticsEntity entity) {
        if (entity == null) {
            return;
        }
        final TextView tvOutCount = ButterKnife.findById(view, R.id.tv_out_count);
        final TextView tvConnectCount = ButterKnife.findById(view, R.id.tv_connect_count);
        final TextView tvRate = ButterKnife.findById(view, R.id.tv_rate);
        final TextView tvDuration = ButterKnife.findById(view, R.id.tv_duration);
        final TextView tvIntentCount = ButterKnife.findById(view, R.id.tv_intent_count);
        final TextView tvReservation = ButterKnife.findById(view, R.id.tv_reservation_count);
        tvOutCount.setText(entity.getCntCall()+"");
        tvConnectCount.setText(entity.getCntConnect()+"");
        tvRate.setText(entity.getRateConnect()+"");
        tvDuration.setText(entity.getCntDuration()+"");
        tvIntentCount.setText(entity.getCntIntent()+"");
        tvReservation.setText(entity.getCntAppoint() + "");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.user_tel)
        TextView userTel;
        @BindView(R.id.layout_detail)
        LinearLayout layoutDetail;
        @BindView(R.id.layout_item)
        LinearLayout layoutItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
