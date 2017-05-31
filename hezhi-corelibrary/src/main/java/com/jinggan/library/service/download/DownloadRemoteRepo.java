package com.jinggan.library.service.download;

import com.jinggan.library.net.download.DownloadProgressHandler;
import com.jinggan.library.net.download.ProgressHelper;
import com.jinggan.library.utils.ILogcat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/4/17 11:53
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DownloadRemoteRepo implements DownloadDataSource {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void downloadAction(String domain,String url,final String filePath,final String fileName, final DownloadCallBack downloadCallBack) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(domain);
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        DownloadApiService retrofit = retrofitBuilder
                .client(builder.build())
                .build().create(DownloadApiService.class);

        Call<ResponseBody> downloadCall = retrofit.downloadFile(url);
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                downloadCallBack.onDownloadProgress(bytesRead, contentLength, done);
                ILogcat.e(TAG, String.format("%d%% done\n", (100 * bytesRead) / contentLength));
                downloadCallBack.onDownloadProgress((int) ((100 * bytesRead) / contentLength));
            }
        });
        downloadCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                InputStream is = response.body().byteStream();
                                File file = new File(filePath);
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                FileOutputStream fos = new FileOutputStream(file+File.separator + fileName);
                                BufferedInputStream bis = new BufferedInputStream(is);
                                byte[] buffer = new byte[1024];
                                int len;
                                while ((len = bis.read(buffer)) != -1) {
                                    fos.write(buffer, 0, len);
                                    fos.flush();
                                }
                                fos.close();
                                bis.close();
                                is.close();
                                downloadCallBack.onDownloadComplete(new File(file,fileName));
                            } catch (IOException e) {
                                e.printStackTrace();
                                downloadCallBack.onDownloadDataNotAvailable();
                            }
                        }
                    }).start();
                }else {
                    downloadCallBack.onDownloadDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                downloadCallBack.onDownloadDataNotAvailable();
            }
        });
    }
}
