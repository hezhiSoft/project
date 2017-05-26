package com.jinggan.library.media.picture;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jinggan.library.R;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.media.picture.data.PictureEntity;
import com.jinggan.library.ui.widget.photoView.PhotoView;
import com.jinggan.library.ui.widget.photoView.PhotoViewAttacher;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.IUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片查看类
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:30
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class ShowPictureActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private TextView mIndexTextView;

    private static List<PictureEntity> images;
    private static int position;

    /**
     * 跳转查看图片
     *
     * @param context
     * @param url
     */
    public static void startIntent(Context context, String url) {
        List<PictureEntity> list = new ArrayList<>();
        PictureEntity entity = new PictureEntity();
        entity.setUrl(url);
        list.add(entity);
        startIntent(context, list, 0);
    }

    /**
     * 图片查看
     *
     * @param content
     * @param paths
     */
    public static void startIntent(Context content, ArrayList<String> paths) {
        List<PictureEntity> list = new ArrayList<>();
        if (paths != null) {
            for (String path : paths) {
                PictureEntity entity = new PictureEntity();
                entity.setUrl(path);
                list.add(entity);
            }
            startIntent(content, list, 0);
        }
    }

    /**
     * 图片查看
     *
     * @param content
     * @param paths
     */
    public static void startIntent(Context content, ArrayList<String> paths, int currentPosion) {
        List<PictureEntity> list = new ArrayList<>();
        if (paths != null) {
            for (String path : paths) {
                PictureEntity entity = new PictureEntity();
                entity.setUrl(path);
                list.add(entity);
            }
            startIntent(content, list, currentPosion);
        }
    }

    /**
     * 跳转查看图片
     *
     * @param context
     * @param entity
     */
    public static void startIntent(Context context, PictureEntity entity) {
        List<PictureEntity> list = new ArrayList<>();
        list.add(entity);
        startIntent(context, list, 0);
    }

    /**
     * 跳转查看图片
     *
     * @param context
     * @param list
     */
    public static void startIntent(Context context, List<PictureEntity> list, int currentPosion) {
        images = list;
        position = currentPosion;
        ISkipActivityUtil.startIntent(context, ShowPictureActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_show_picture);
        setSwipeEnabled(false);
        setToolbarVisibility(View.GONE);
        findViewById();
        initViewPager();
    }

    private void findViewById() {
        mViewPager = (ViewPager) findViewById(R.id.ShowImage_viewPager);
        mIndexTextView = (TextView) findViewById(R.id.ShowImage_index_textView);
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mViewPager.setAdapter(new ShowImagePagerAdapter(images));
        mViewPager.addOnPageChangeListener(this);
        mIndexTextView.setText((position + 1) + "/" + images.size());
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mIndexTextView.setText((position + 1) + "/" + images.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ShowImagePagerAdapter extends PagerAdapter {

        private List<PictureEntity> list;

        public ShowImagePagerAdapter(List<PictureEntity> list) {
            if (list == null) {
                this.list = new ArrayList<>();
            } else
                this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View imageLayout = getLayoutInflater().inflate(R.layout.item_show_picture, container, false);
            PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.item_showPicture_imageView);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.item_showPicture_progressBar);
            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    ShowPictureActivity.this.finish();
                }

                @Override
                public void onOutsidePhotoTap() {
                    ShowPictureActivity.this.finish();
                }
            });
            Glide.with(ShowPictureActivity.this).load(IUtil.fitterUrl(list.get(position).getUrl())).placeholder(R.mipmap.main_img_defaultpic_small).error(R.mipmap.main_img_defaultpic_small).into(new GlideDrawableImageViewTarget(imageView) {
                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                    super.onResourceReady(resource, animation);
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadStarted(Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onStop() {
                    super.onStop();
                    spinner.setVisibility(View.GONE);
                }
            });
            PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
            attacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    ShowPictureActivity.this.finish();
                }
            });
            container.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
