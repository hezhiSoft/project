package com.easydear.user.module.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easydear.user.R;
import com.easydear.user.module.search.data.SearchEntity;

import java.util.ArrayList;

/**
 * Created by LJH on 2017/3/4.
 */
public class SearchHotAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<SearchEntity> mSearchHotList;

    public SearchHotAdapter(Context context) {
        mContext = context;
        mSearchHotList = new ArrayList<SearchEntity>();
    }

    public void setSearchHotList(ArrayList<SearchEntity> searchHotList) {
        this.mSearchHotList = searchHotList;
    }

    @Override
    public int getCount() {
        return mSearchHotList == null ? 0 : mSearchHotList.size();
    }

    @Override
    public Object getItem(int i) {
        return mSearchHotList == null ? null : mSearchHotList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_hot_layout, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_search_hot_textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mSearchHotList != null && mSearchHotList.size() > 0) {
            holder.textView.setText(mSearchHotList.get(position).getMsg());
        }
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
