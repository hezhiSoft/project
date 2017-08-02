package com.easydear.user.module.business;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.jinggan.library.base.ArrayListAdapter;
import com.jinggan.library.utils.ISkipActivityUtil;


/**
 * Created by LJH on 2017/7/3.
 */

public class ActivityAdapter extends ArrayListAdapter<BusinessDetailEntity.ActivityListBean> {

    public ActivityAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_activity_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.ActivityTitle = (TextView) convertView.findViewById(R.id.shop_activity_title);
            viewHolder.ActivityName = (TextView) convertView.findViewById(R.id.shop_activity_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ActivityTitle.setText(mList.get(position).getTitle());
        viewHolder.ActivityName.setText(mList.get(position).getActivityName());
        viewHolder.ActivityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int activityId = mList.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("activity_id", activityId);
                ISkipActivityUtil.startIntent(mContext, ActivityDetailActivity.class, bundle);
            }
        });
        return convertView;
    }

    class ViewHolder {
        public TextView ActivityTitle;
        public TextView ActivityName;
    }
}