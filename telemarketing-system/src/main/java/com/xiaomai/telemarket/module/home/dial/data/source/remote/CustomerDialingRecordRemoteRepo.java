package com.xiaomai.telemarket.module.home.dial.data.source.remote;

import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.net.retrofit.RetrofitCallback;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.api.XiaomaiRetrofitManager;
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @description 通话录音上传
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 07/06/2017 2:16 AM
 **/
public class CustomerDialingRecordRemoteRepo implements BaseDataSourse {

    private Call<Responese<FileEntity>> uploadCall;

    private static CustomerDialingRecordRemoteRepo instance;

    public static CustomerDialingRecordRemoteRepo getInstance() {
        if (instance == null) {
            synchronized (CustomerDialingRecordRemoteRepo.class) {
                if (instance == null) {
                    instance = new CustomerDialingRecordRemoteRepo();
                }
            }
        }
        return instance;
    }

    /**
     * 上传录音
     * @param fileName
     * @param cusrometId
     * @param file
     * @param callback
     */
    public void addRecordFile(String fileName, String cusrometId, int duration, File file, final RemetoRepoCallback<FileEntity> callback) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        if (file != null) {//做一个文件非空判断
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("record", file.getName(), requestBody);// TODO: 04/06/2017 record 这个名字不知什么参数
            parts.add(part);
        }
        String pamar = "api/file/upload?param={\"filename\":\"" + fileName + "\",\"BusinessID\":\"" + cusrometId + "\",\"Duration\":"+duration+"}";
        uploadCall = XiaomaiRetrofitManager.getAPIService().addFile(pamar, parts);
        uploadCall.enqueue(new RetrofitCallback<Responese<FileEntity>>() {
            @Override
            public void onSuccess(Responese<FileEntity> data) {
                if (data.getCode() == 213) {
                    callback.onSuccess(data.getData());
                } else {
                    callback.onFailure(data.getCode(), data.getMsg());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                callback.onThrowable(t);
            }

            @Override
            public void onUnauthorized() {
                callback.onUnauthorized();
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    @Override
    public void cancelRequest() {

    }
}
