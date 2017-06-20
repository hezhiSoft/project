package com.easydear.user.module.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.R;
import com.easydear.user.module.message.data.MessageDetailEntity;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LJH on 2017/1/15.
 */

public class MessageDetailAdapter extends BaseRecyclerViewAdapter<MessageDetailEntity> implements BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener {

    public MessageDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        MessageDetailAdapter.ItemViewHolder viewHolder = new MessageDetailAdapter.ItemViewHolder(inflater.inflate(R.layout.item_message_detail_layout, parent, false));
        setOnItemClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.messageContentText.setText(mLists.get(position).getBusinessName());
            viewHolder.messageTimeText.setText(mLists.get(position).getCreateTime());
            viewHolder.messageConsumeInfoText.setText(mLists.get(position).getContent());
            Glide.with(mContent).load(BuildConfig.DOMAIN+mLists.get(position).getLogo()).into(viewHolder.headView);
    }

    @Override
    public void onItemClick(View view, Object data, int position) {

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.message_detail_business_head)
        ImageView headView;
        @BindView(R.id.message_detail_content)
        TextView messageContentText;
        @BindView(R.id.message_detail_time)
        TextView messageTimeText;
        @BindView(R.id.message_detail_consume_info)
        TextView messageConsumeInfoText;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(ItemViewHolder.this, view);
        }
    }
}
