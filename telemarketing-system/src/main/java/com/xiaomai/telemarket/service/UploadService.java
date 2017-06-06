package com.xiaomai.telemarket.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.utils.IFileUtils;
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;
import com.xiaomai.telemarket.module.home.dial.data.source.remote.CustomerDialingRecordRemoteRepo;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * @description 后台上传服务 录音文件
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 04/06/2017 4:23 PM
 **/
public class UploadService extends IntentService {

    private static final String TAG = "UploadService";

    private CustomerDialingRecordRemoteRepo mRepo;
    private MediaPlayer mediaPlayer;
    private UploadHandler uploadHandler;

    public static final int MSG_UPLOAD_RECORD = 0x001;


    public UploadService(){
        super("UploadService");
    }

    public static void stopService(Context context) {
        Intent intent = new Intent(context, UploadService.class);
        context.stopService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (mRepo == null) {
            mRepo = CustomerDialingRecordRemoteRepo.getInstance();
        }
        try {
            if (uploadHandler ==null) {
                uploadHandler = new UploadHandler(UploadService.this);
            }
            uploadHandler.sendEmptyMessage(MSG_UPLOAD_RECORD);
        } catch (Exception e) {

        }
    }

    public static class UploadHandler extends Handler{

        private WeakReference<UploadService> mService;
        public UploadHandler(UploadService service) {
            mService = new WeakReference<UploadService>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_UPLOAD_RECORD) {
                //delete null 文件夹
                mService.get().uploadRecordFiles();
            }
        }
    }

    private void uploadRecordFiles(){
        String recordDirectory = IFileUtils.getRecordDirectory();//record根目录
        File fileRoot = new File(recordDirectory);
        if (fileRoot != null && fileRoot.exists()) {
            if (fileRoot.isDirectory()) {
                File[] childFileDirectory = fileRoot.listFiles();//customerId子目录
                if (childFileDirectory!=null&&childFileDirectory.length>0) {
                    for (int i = 0; i < childFileDirectory.length; i++) {
                        File fileCustomer = childFileDirectory[i];//customer
                        String customerId = fileCustomer.getName();
                        if (fileCustomer!=null&&fileCustomer.isDirectory()) {
                            File[] recordFiles = fileCustomer.listFiles();//customer 录音文件子目录
                            if (recordFiles!=null&&recordFiles.length>0) {
                                for (int j = 0; j < recordFiles.length; j++) {
                                    //这里文件单个上传
                                    final File recordFile = recordFiles[j];
                                    if (recordFile != null&&recordFile.exists()) {
                                        Log.i(TAG, "start upload..." + recordFile.getAbsolutePath());
                                        mRepo.addRecordFile(recordFile.getName(), customerId,getRecordDuration(mediaPlayer,recordFile), recordFile, new RemetoRepoCallback<FileEntity>() {
                                            @Override
                                            public void onSuccess(FileEntity data) {
                                                try {
                                                    Log.i(TAG, "onSuccess: FileName="+data.getFileName()+"  Url="+data.getFileUrl());
                                                    //Url=/Files/20170607/38e01abd-e4f3-4675-ac63-6a1099aaaf7a/20170607032728_1251.mp3 非标准格式
                                                    if (recordFile != null && recordFile.exists()) {
                                                        recordFile.delete();//上传完成删除文件
                                                        Log.i(TAG, "delete success#: FileName=" + data.getFileName() + "  Url=" + recordFile.getAbsolutePath());
                                                    } else {
                                                        Log.i(TAG, "delete failed # not exits");
                                                    }
                                                }catch (Exception e){
                                                    Log.i(TAG, "error #" + e.getMessage());
                                                }
                                            }

                                            @Override
                                            public void onFailure(int code, String msg) {
                                                Log.i(TAG, "onFailure: Error#" + "code=" + code + ",msg=" + msg);
                                            }

                                            @Override
                                            public void onThrowable(Throwable t) {
                                                Log.i(TAG, "onThrowable: Error#");
                                            }

                                            @Override
                                            public void onUnauthorized() {
                                                Log.i(TAG, "onUnauthorized: Error#");
                                            }

                                            @Override
                                            public void onFinish() {

                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private int getRecordDuration(MediaPlayer mediaPlayer, File file) {
        if (mediaPlayer==null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());  //recordingFilePath（）为音频文件的路径
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int duration= mediaPlayer.getDuration();//获取音频的时间
        if (duration>0) {
            duration = duration / 1000;
        }
        mediaPlayer.release();//记得释放资源
        return duration;
    }

}
