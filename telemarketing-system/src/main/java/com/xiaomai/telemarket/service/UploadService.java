package com.xiaomai.telemarket.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * @description 后台上传服务 录音文件
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 04/06/2017 4:23 PM
 **/
public class UploadService extends IntentService {

    public UploadService(){
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }


}
