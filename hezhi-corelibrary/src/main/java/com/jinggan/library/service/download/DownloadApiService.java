package com.jinggan.library.service.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/31 21:33
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public interface DownloadApiService {

    /**
     * 文件下载
     *
     * @param url
     * @return
     */
    @GET
    Call<ResponseBody> downloadFile(@Url String url);

}
