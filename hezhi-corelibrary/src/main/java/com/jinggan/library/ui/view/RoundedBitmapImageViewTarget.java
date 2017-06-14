package com.jinggan.library.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * 将bitmap切割成圆形的Target,适用于Glide框架中的into()
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:17
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class RoundedBitmapImageViewTarget extends BitmapImageViewTarget {

    private Context context;

    public RoundedBitmapImageViewTarget(ImageView view) {
        super(view);
    }

    public RoundedBitmapImageViewTarget(Context context, ImageView imageView) {
        super(imageView);
        this.context = context;
    }

    @Override
    protected void setResource(Bitmap resource) {
        if (context == null) {
            return;
        }
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
        circularBitmapDrawable.setCircular(true);
        view.setImageDrawable(circularBitmapDrawable);
    }
}
