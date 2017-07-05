package com.easydear.user.module.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easydear.user.R;
import com.easydear.user.module.location.data.LocationEntity;

import java.util.ArrayList;

/**
 * Created by LJH on 2017/3/4.
 */
public class LocationAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<LocationEntity> mLocationList;

    public LocationAdapter(Context context) {
        mContext = context;
    }

    public void setLocationList(ArrayList<LocationEntity> locationList) {
        this.mLocationList = locationList;
    }

    @Override
    public int getCount() {
        return mLocationList == null ? 0 : mLocationList.size();
    }

    @Override
    public Object getItem(int i) {
        return mLocationList == null ? null : mLocationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_location_layout, null);
            holder.textView = (TextView) convertView.findViewById(R.id.item_location_textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mLocationList.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
