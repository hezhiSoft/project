package com.jinggan.library.service.download;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.jinggan.library.ui.dialog.ToastUtil;

/**
 * 下载管理类
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/4/17 11:59
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DownloadManager {
    /*下载保存文件路径*/
    private static String filePath;
    /*下载保存文件名*/
    private static String fileName;
    /*下载文件地址*/
    private static String downloadUrl;
    /*域名*/
    private static String DOMAIN;

    private static Context context;

    public static Builder getDefault(Context context) {
        DownloadManager.context = context;
        return new Builder();
    }

    private static void download(DownloadDataSource.DownloadCallBack callBack) {
        if (TextUtils.isEmpty(filePath)) {
            ToastUtil.showToast(context, "下载保存文件路径为空 ");
            return;
        }
        if (TextUtils.isEmpty(fileName)) {
            ToastUtil.showToast(context, "保存文件名为空");
            return;
        }
        if (TextUtils.isEmpty(downloadUrl)) {
            ToastUtil.showToast(context, "下载文件路径为空");
            return;
        }
        new DownloadRemoteRepo().downloadAction(DOMAIN,downloadUrl, filePath, fileName, callBack);
    }

    public static class Builder {

        public Builder setFilePath(String filePath) {
            DownloadManager.filePath = filePath;
            return this;
        }

        public Builder setFileName(String fileName) {
            DownloadManager.fileName = fileName;
            return this;
        }

        public Builder setDownloadUrl(String url) {
            DownloadManager.downloadUrl = url;
            return this;
        }

        public Builder setDomain(String domain){
            DownloadManager.DOMAIN=domain;
            return this;
        }

        public void startDownload(DownloadDataSource.DownloadCallBack callBack) {
            download(callBack);
        }
    }
}
