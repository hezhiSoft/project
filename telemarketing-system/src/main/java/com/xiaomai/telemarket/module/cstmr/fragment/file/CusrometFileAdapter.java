package com.xiaomai.telemarket.module.cstmr.fragment.file;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinggan.library.media.picture.attachment.ImageAttachmentView;
import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 13:31
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometFileAdapter extends BaseRecyclerViewAdapter<FileEntity> {

    private OnClickItemLisenter listenter;

    public CusrometFileAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHodler(inflater.inflate(R.layout.item_cusromet_edtails_layout, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        viewHodler.DetailsTileTextView.setText("文件明细 "+(position+1));
        viewHodler.DetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHodler.DetailsContentLayout.getVisibility() == View.VISIBLE) {
                    viewHodler.ExpandImageView.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    viewHodler.DetailsContentLayout.setVisibility(View.GONE);
                    viewHodler.lineView.setVisibility(View.VISIBLE);
                    if (listenter!=null){
                        listenter.onSeleceItemPosition(null);
                    }
                } else {
                    viewHodler.ExpandImageView.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    viewHodler.DetailsContentLayout.setVisibility(View.VISIBLE);
                    viewHodler.lineView.setVisibility(View.GONE);
                    if (listenter!=null){
                        listenter.onSeleceItemPosition(mLists.get(position));
                    }
                }
            }
        });
        final View infoView = inflater.inflate(R.layout.cusromet_file_layout, null);
        setDetailsData(infoView, mLists.get(position));
        viewHodler.DetailsContentLayout.addView(infoView);
    }

    class ViewHodler extends RecyclerView.ViewHolder {

        @BindView(R.id.icDetails)
        ImageView icDetails;
        @BindView(R.id.Details_tile_TextView)
        TextView DetailsTileTextView;
        @BindView(R.id.Details_layout)
        RelativeLayout DetailsLayout;
        @BindView(R.id.Details_content_layout)
        RelativeLayout DetailsContentLayout;
        @BindView(R.id.Details_line)
        View lineView;
        @BindView(R.id.Details_expand_iamgeView)
        ImageView ExpandImageView;

        public ViewHodler(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void setDetailsData(View rootView, FileEntity entity) {
        if (entity == null) {
            return;
        }
        FormWriteTopTitleView fileName=ButterKnife.findById(rootView,R.id.File_FileName);
        FormSelectTopTitleView File_FileExtension=ButterKnife.findById(rootView,R.id.File_FileExtension);
        FormWriteTopTitleView File_FileUrl=ButterKnife.findById(rootView,R.id.File_FileUrl);
        ImageAttachmentView imageAttachmentView=ButterKnife.findById(rootView,R.id.File_Attachment);
        /*文件名*/
        fileName.setContentText(entity.getFileName()).setItemEnabled(false);
        /*文件类型*/
        File_FileExtension.setContentText(entity.getFileExtension()).setArrowDropVisibility(View.GONE);
        /*文件路径*/
        File_FileUrl.setContentText(entity.getFileUrl()).setItemEnabled(false);

        imageAttachmentView.setShowImage(entity.getFileUrl());

    }

    public void setListenter(OnClickItemLisenter listenter) {
        this.listenter = listenter;
    }

    public interface OnClickItemLisenter{
        void onSeleceItemPosition(FileEntity entity);
    }
}
