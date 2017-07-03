package com.easydear.user.module.business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easydear.user.R;
import com.easydear.user.module.business.data.ActivityItemEntity;

import java.util.List;

/**
 * Created by LJH on 2017/7/3.
 */

public class ActivityAdapter extends BaseAdapter {

    private Context mContext;
    private List<ActivityItemEntity> mActivityList;

    public ActivityAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public int getCount() {
        return mActivityList == null ? 0 : mActivityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mActivityList == null ? null : mActivityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<ActivityItemEntity> getActivityList() {
        return mActivityList;
    }

    public void setActivityList(List<ActivityItemEntity> mActivityList) {
        this.mActivityList = mActivityList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mActivityList == null) {
            return convertView;
        }
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_activity_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.typeTextView = (TextView) convertView.findViewById(R.id.business_shop_activity_type);
            viewHolder.contentTextView = (TextView) convertView.findViewById(R.id.business_shop_activity_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.typeTextView.setText(mActivityList.get(position).getType());
        viewHolder.contentTextView.setText(mActivityList.get(position).getContent());
        return convertView;
    }

    class ViewHolder {
        public TextView typeTextView;
        public TextView contentTextView;
    }
}
