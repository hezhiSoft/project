package com.jinggan.library.media.picture;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jinggan.library.R;
import com.jinggan.library.media.picture.data.PictureEntity;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.ui.view.PhotoAlbumImageView;
import com.jinggan.library.utils.IUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 选择图片适配器
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:30
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SelectPictrueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /*拍照Key*/
    public final static String KEY_TAKE_PICTURE = "tabk_picture";

    private List<PictureEntity> mLists;
    private List<String> selectePics;
    private Context context;
    private Point mPoint = new Point(0, 0);// 用来封装ImageView的宽和高的对象
    private int total = 10;
    private int currentTotal = 0;

    private OnClickSelectePictureListener listener;

    public SelectPictrueAdapter(Context context, int total) {
        this.context = context;
        this.total = total;
        this.mLists = new ArrayList<>();
        this.selectePics = new ArrayList<>();
    }

    /**
     * 添加资源
     *
     * @param list
     */
    public void setData(List<PictureEntity> list) {
        this.mLists.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 添加资源
     *
     * @param entity
     */
    public void setData(PictureEntity entity) {
        this.mLists.add(entity);
        notifyDataSetChanged();
    }

    /**
     * 添加已选中的资源
     *
     * @param list
     */
    public void setSelecteData(List<String> list) {
        this.selectePics = list;
        this.currentTotal = list.size();
    }

    /**
     * 设置监听回调
     *
     * @param listener
     */
    public void setListener(OnClickSelectePictureListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder viewHolder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_picture_selecte_layout, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        // 用来监听ImageView的宽和高
        itemViewHolder.imageView.setOnMeasureListener(new PhotoAlbumImageView.OnMeasureListener() {

            @Override
            public void onMeasureSize(int width, int height) {
                mPoint.set(width, height);
            }
        });
        if (mLists.get(position).getUrl().equals(KEY_TAKE_PICTURE)) {
            itemViewHolder.imageView.setImageResource(R.mipmap.icon_tack_picture);
            itemViewHolder.checkBox.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(IUtil.fitterUrl(mLists.get(position).getUrl())).into(itemViewHolder.imageView);
            itemViewHolder.checkBox.setVisibility(View.VISIBLE);
        }

        itemViewHolder.checkBox.setOnCheckedChangeListener(null);

//        if (selectePics!=null&&selectePics.size() > 0) {
//            for (String url : selectePics) {
//                if (url.equals(mLists.get(position).getUrl())) {
//                    itemViewHolder.checkBox.setChecked(true);
//                    itemViewHolder.shadowTextView.setVisibility(View.VISIBLE);
//                    if (listener != null) {
//                        listener.onSelectePicture(mLists.get(position));
//                    }
//                }
//            }
//        }

//        if (mLists.get(position).isSelecte()){
        itemViewHolder.checkBox.setChecked(mLists.get(position).isSelecte());
//        }

        itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (KEY_TAKE_PICTURE.equals(mLists.get(position).getUrl())) {
                    if (listener != null) {
                        listener.onTackPicture();
                    }
                } else {
                    ShowPictureActivity.startIntent(context, mLists.get(position).getUrl());
                }
            }
        });
        itemViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (currentTotal == total) {
                    itemViewHolder.checkBox.setChecked(false);
                    itemViewHolder.shadowTextView.setVisibility(View.GONE);
                    if (!b) {
                        currentTotal--;
                        mLists.get(position).setSelecte(b);
                        if (listener != null) {
                            listener.onClickPicture(b, mLists.get(position));
                        }
                    } else {
                        ToastUtil.showToast(context, "最多选择" + total + "张图片");
                    }
                    return;
                }
                if (b) {
                    currentTotal++;
                    itemViewHolder.shadowTextView.setVisibility(View.VISIBLE);
                } else {
                    currentTotal--;
                    itemViewHolder.shadowTextView.setVisibility(View.GONE);
                }
                mLists.get(position).setSelecte(b);
                if (listener != null) {
                    listener.onClickPicture(b, mLists.get(position));
                }
            }
        });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        PhotoAlbumImageView imageView;
        CheckBox checkBox;
        TextView shadowTextView;

        public ItemViewHolder(View view) {
            super(view);
            imageView = (PhotoAlbumImageView) view.findViewById(R.id.ItemSelectePicture_imageView);
            checkBox = (CheckBox) view.findViewById(R.id.ItemSelectePicture_CheckBox);
            shadowTextView = (TextView) view.findViewById(R.id.ItemSelectePicture_shadow);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnClickSelectePictureListener {
        void onClickPicture(boolean isSelecte, PictureEntity entity);

        void onTackPicture();

        void onSelectePicture(PictureEntity entity);
    }
}
