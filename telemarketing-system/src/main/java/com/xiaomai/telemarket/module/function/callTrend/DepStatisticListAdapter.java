package com.xiaomai.telemarket.module.function.callTrend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.function.data.CallOutDepStaticsEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 部门外呼趋势统计
 * @createtime 20/05/2017 5:20 PM
 **/
public class DepStatisticListAdapter extends BaseRecyclerViewAdapter<CallOutDepStaticsEntity> {

    public DepStatisticListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_statistic_month_layout, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        CallOutDepStaticsEntity entity = mLists.get(position);
        if (entity!=null) {
            viewHolder.tvMonthLabel.setText(entity.getDateMonth()+"月");
            viewHolder.progressbar.setProgress(entity.getCnt()/1000);
            viewHolder.tvNumber.setText(entity.getCnt()+"");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_month_label)
        TextView tvMonthLabel;
        @BindView(R.id.progressbar)
        ProgressBar progressbar;
        @BindView(R.id.tv_number)
        TextView tvNumber;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
