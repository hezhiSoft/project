package com.xiaomai.telemarket.module.cstmr.fragment.file;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.media.picture.SelectPictureActivity;
import com.jinggan.library.media.picture.attachment.ImageAttachmentView;
import com.jinggan.library.media.picture.data.PictureEntity;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:58
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FileBaseFragment extends BaseFragment {


    @BindView(R.id.File_FileName)
    FormWriteTopTitleView FileFileName;
    @BindView(R.id.File_Attachment)
    ImageAttachmentView FileAttachment;
    Unbinder unbinder;

    protected FileEntity entity;

    protected Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_file, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(true);
    }

    protected void initUI(FileEntity entity){
        if (entity==null){
            return;
        }
        FileFileName.setContentText(entity.getFileName());
        FileAttachment.setImageList(entity.getFileUrl());
    }

    public FileEntity getFileEntity(){
        if (entity==null){
            entity=new FileEntity();
        }
        entity.setFileName(FileFileName.getContentText());
        entity.setFileUrl(FileAttachment.getImageStrs());
        return entity;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== SelectPictureActivity.SELECT_PICTURE_RESULT_CODE){
            List<PictureEntity> pictureEntities=(List<PictureEntity>)data.getSerializableExtra("result");
            FileAttachment.setImageListToEntity(pictureEntities);
        }
    }


    @Subscribe
    public void onEventBusSubmit(EventBusValues values) {
        if (values.getWhat() == 0x1006) {
//            DialogFactory.showMsgDialog(getContext(), "提交", "确定提交当前记录?", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
                    onSubmit();
//                }
//            });
        }
    }

    public void onSubmit() {

    }
}
