package com.easydear.user.module.business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.jinggan.library.base.ArrayListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJH on 2017/7/3.
 */

public class ActivityAdapter extends ArrayListAdapter<BusinessDetailEntity.CardListBean> {

    public ActivityAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_activity_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.CardName = (TextView) convertView.findViewById(R.id.Activity_CardName);
            viewHolder.CardPrice = (TextView) convertView.findViewById(R.id.Activity_price);
            viewHolder.CardEndTime=(TextView) convertView.findViewById(R.id.Activity_time);
            viewHolder.buyButton=(Button)convertView.findViewById(R.id.Activity_buy);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.CardName.setText(mList.get(position).getCardName());
        viewHolder.CardPrice.setText("￥ "+mList.get(position).getCardPrice());
        viewHolder.CardEndTime.setText(mList.get(position).getCardEndTime());
        return convertView;
    }

    class ViewHolder {
        public TextView CardName;
        public TextView CardPrice;
        public TextView CardEndTime;
        public Button buyButton;
    }
}