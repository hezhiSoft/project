package com.jinggan.library.service.download;

import java.io.File;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/4/17 11:51
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public interface DownloadDataSource {
    interface DownloadCallBack {
        void onDownloadProgress(int percent);

        void onDownloadComplete(File file);

        void onDownloadDataNotAvailable();

        void onDownloadProgress(long bytesRead, long contentLength, boolean done);
    }

    void downloadAction(String domain,String url, String filePath, String fileName, DownloadCallBack downloadCallBack);
}
