package com.xiaomai.telemarket.appCheck;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.service.download.DownloadDataSource;
import com.jinggan.library.service.download.DownloadManager;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.utils.IFileUtils;
import com.jinggan.library.utils.ISystemUtil;
import com.xiaomai.telemarket.BuildConfig;
import com.xiaomai.telemarket.appCheck.data.VersionEntity;

import java.io.File;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/31 18:05
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class AppCheckHelper implements RemetoRepoCallback<VersionEntity>, DownloadDataSource.DownloadCallBack {

    private boolean isShowDialog;
    private Dialog dialog;
    private ProgressDialog loadDialog;
    private Activity activity;
    private String domain;

    private static AppCheckHelper instance;

    public static AppCheckHelper getInstance() {
        if (instance == null) {
            instance = new AppCheckHelper();
        }
        return instance;
    }

    public void checkVersion(Activity activity, boolean isShowDialog) {
        this.activity = activity;
        this.isShowDialog = isShowDialog;
        if (isShowDialog) {
            dialog = DialogFactory.createLoadingDialog(activity, "检测...");
        }
        AppCheckReto.getInstance().appCheck(this);
    }

    @Override
    public void onSuccess(final VersionEntity data) {
        if (data == null) {
            return;
        }
        if (data.getVersionCode() > BuildConfig.VERSION_CODE) {
            DialogFactory.showMsgDialog(activity, "版本更新", data.getSummary(), "更新", "取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDialog = DialogFactory.createLoadingDialog(activity, "下载", false);
                    DownloadManager.getDefault(activity)
                            .setDownloadUrl(data.getFilePath())
                            .setFileName("maimain.apk")
                            .setDomain(BuildConfig.DOMAIN)
                            .setFilePath(IFileUtils.getDownloadDir())
                            .startDownload(AppCheckHelper.this);
                }
            }, null);
        } else {
            if (isShowDialog) {
                ToastUtil.showToast(activity, "目前已最新版本!");
            }
        }
    }

    @Override
    public void onFailure(int code, String msg) {

    }

    @Override
    public void onThrowable(Throwable t) {

    }

    @Override
    public void onUnauthorized() {

    }

    @Override
    public void onFinish() {
        DialogFactory.dimissDialog(dialog);
    }

    @Override
    public void onDownloadProgress(int percent) {
        loadDialog.setMessage("下载中.." + percent + "%");
    }

    @Override
    public void onDownloadComplete(File file) {
        DialogFactory.dimissDialog(loadDialog);
        ISystemUtil.installAPK(activity, file);
    }

    @Override
    public void onDownloadDataNotAvailable() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogFactory.dimissDialog(loadDialog);
                ToastUtil.showToast(activity, "下载失败");
            }
        });
    }

    @Override
    public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

    }
}
