package com.jinggan.library.media.picture;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jinggan.library.R;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.media.picture.data.PictureEntity;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.view.SpaceItemDecoration;
import com.jinggan.library.utils.IDateTimeUtils;
import com.jinggan.library.utils.IFileUtils;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.PermissionHelper;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 选择图片Activity
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:30
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class SelectPictureActivity extends BaseActivity implements PictureContract.View, SwipeRefreshLayout.OnRefreshListener, SelectPictrueAdapter.OnClickSelectePictureListener {
    private final String TAG = getClass().getSimpleName();
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1001;/*读取外部存储卡权限*/
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1002;/*写入外部存储卡权限*/
    private static final int REQUEST_CAMERA = 1003;


    private final int TACK_PICTURE_VALUE = 490;
    public static final int SELECT_PICTURE_RESULT_CODE = 491;
    public static final int SELECT_PICTURE_REQUEST_CODE = 492;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private SelectPictrueAdapter adapter;

    private PicturePresenter mPresenter;

    private List<PictureEntity> images;

    private String path;

    private static int totalImage = 10;

    private static List<String> selectePicLists;

    private static OnSeletePictureCallBack seletePictureCallBack;

    /**
     * 启动图片选择Activity
     *
     * @param activity
     * @param selectTotal 默认选择几张图片
     */
    public static void startIntent(Activity activity, int selectTotal) {
        if (selectTotal > 0) {
            totalImage = selectTotal;
        }
        selectePicLists=new ArrayList<>();
        ISkipActivityUtil.startIntentForResult(activity, SelectPictureActivity.class, SELECT_PICTURE_REQUEST_CODE);
    }

    /**
     * @param activity
     * @param selectePics 已经选择中的图片集合
     */
    public static void startIntent(Activity activity, List<String> selectePics, OnSeletePictureCallBack callBack) {
        seletePictureCallBack = callBack;
        if (selectePics.size() > 0) {
            selectePicLists = selectePics;
        } else {
            selectePicLists = new ArrayList<>();
        }
        ISkipActivityUtil.startIntentForResult(activity, SelectPictureActivity.class, SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_select_picture);
        setToolbarTitle(R.string.title_select_pictrue);
        ButterKnife.bind(this);
        if (selectePicLists.size() > 0) {
            setToolbarRightText(getString(R.string.dialog_defalut_confirm_text) + "  [ " + (selectePicLists.size() - 1) + " ]");
        }
        if (PermissionHelper.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_READ_EXTERNAL_STORAGE) && PermissionHelper.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_WRITE_EXTERNAL_STORAGE)) {
            init();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        images = new ArrayList<>();
        findViewById();
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
        startRefresh();
    }

    private void findViewById() {
        mRecyclerView = (RecyclerView) findViewById(R.id.SelectPiture_RecyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SelectPiture_swipeRefreshLayout);
    }

    /**
     * 初始化SwipeRefreshLayout
     */
    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        adapter = new SelectPictrueAdapter(this, totalImage);
        adapter.setListener(this);
        adapter.setSelecteData(selectePicLists);
        int spacingInPixels = getResources().getDimensionPixelOffset(R.dimen.recycler_item_space);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(manager);
    }

    /**
     * 初始化Presenter
     */
    private void initPresenter() {
        mPresenter = new PicturePresenter(this);
    }


    /**
     * 启动刷新
     */
    private void startRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();
    }

    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
        sendAction();
    }

    @Override
    public void onClickPicture(boolean isSelecte, PictureEntity entity) {
        if (isSelecte) {
            images.add(entity);
        } else {
            images.remove(entity);
            if (seletePictureCallBack != null) {
                seletePictureCallBack.onClickDelete(entity);
            }
        }
        if (images.size() > 0) {
            setToolbarRightText(getString(R.string.dialog_defalut_confirm_text) + "  [ " + images.size() + " ]");
        } else {
            if (selectePicLists != null && selectePicLists.size() > 0) {
                setToolbarRightText(R.string.dialog_defalut_confirm_text);
            } else
                setToolbarRightText("");
        }
    }

    @Override
    public void onTackPicture() {
        if (PermissionHelper.checkPermission(this, Manifest.permission.CAMERA, REQUEST_CAMERA)) {
            tackPictureAction();
        }
    }

    private void tackPictureAction() {
        if (IFileUtils.getSDFreeSize() < 1) {
            DialogFactory.warningDialog(this, "容量提示", "SD卡容量已满，请先清理SD卡没用的文件", "关闭", null);
            return;
        }
        path = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + "/IMG_" + System.currentTimeMillis() + ".png";
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, TACK_PICTURE_VALUE);
    }

    @Override
    public void onSelectePicture(PictureEntity entity) {
        images.add(entity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TACK_PICTURE_VALUE:/*拍照回调*/
                    PictureEntity entity = new PictureEntity();
                    entity.setUrl(path);
                    entity.setDate(IDateTimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                    images.add(entity);
                    sendAction();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE || requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            init();
        }
        if (requestCode == REQUEST_CAMERA) {
            tackPictureAction();
        }
    }

    /**
     * 确定返回
     */
    private void sendAction() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("result", (Serializable) images);
        intent.putExtras(bundle);
        setResult(SELECT_PICTURE_RESULT_CODE, intent);
        this.finish();
    }

    @Override
    public void onRefresh() {
        mPresenter.queryAlbum();
    }

    @Override
    public void onQueryAlbumStart() {

    }

    @Override
    public void queryAlbumSuccess(final List<PictureEntity> lists) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                PictureEntity entity = new PictureEntity();
                entity.setUrl(SelectPictrueAdapter.KEY_TAKE_PICTURE);
                lists.add(0, entity);
                if (selectePicLists != null && selectePicLists.size() > 0) {
                    for (int i = 0; i < selectePicLists.size(); i++) {
                        for (int j = 0; j < lists.size(); j++) {
                            if (selectePicLists.get(i).equals(lists.get(j).getUrl())) {
                                lists.get(j).setSelecte(true);
                                onClickPicture(true, lists.get(j));
                            }
                        }
                    }
                }
                adapter.setData(lists);
            }
        });
    }

    @Override
    public void queryAlbumFailure() {

    }

    @Override
    public void onQueryAlbumEnd() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(false);
            }
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    public interface OnSeletePictureCallBack {
        void onClickDelete(PictureEntity entity);
    }
}
