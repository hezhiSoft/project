package com.easydear.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LJH on 2017/7/3.
 */

public class TextGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mTextList;

    public TextGridAdapter(Context context) {
        this.mContext = context;
    }

    public TextGridAdapter(Context context, List<String> textList) {
        this.mContext = context;
        this.mTextList = textList;
    }

    @Override
    public int getCount() {
        return mTextList == null ? 0 : mTextList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTextList == null ? null : mTextList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mTextList == null) {
            return convertView;
        }
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mTextList.get(position));
        return convertView;
    }

    class ViewHolder {
        public TextView textView;
    }
}
