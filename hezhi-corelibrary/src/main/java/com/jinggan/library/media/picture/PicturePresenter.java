package com.jinggan.library.media.picture;


import android.content.Context;

import com.jinggan.library.media.picture.data.PictureEntity;
import com.jinggan.library.media.picture.data.soure.PictureDateSource;
import com.jinggan.library.media.picture.data.soure.PictureRemoteRepo;

import java.util.List;

/**
 *
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:31
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
*/
public class PicturePresenter implements PictureDateSource.QueryAlbumCallBack, PictureContract.Presenter {

    private PictureContract.View mView;
    private PictureRemoteRepo mRemoteRepo;

    public PicturePresenter(PictureContract.View view) {
        this.mView = view;
        this.mRemoteRepo = PictureRemoteRepo.newInstance();
    }

    @Override
    public void queryAlbum() {
        mView.onQueryAlbumStart();
        mRemoteRepo.queryAlbumAction(this);
    }

    @Override
    public void getAlbumSuccess(List<PictureEntity> lists) {
        mView.queryAlbumSuccess(lists);
        mView.onQueryAlbumEnd();
    }

    @Override
    public void getAlbumFailure() {
        mView.queryAlbumFailure();
        mView.onQueryAlbumEnd();
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }
}
