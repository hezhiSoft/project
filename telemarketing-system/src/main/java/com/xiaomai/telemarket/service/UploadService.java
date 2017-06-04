package com.xiaomai.telemarket.service;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.utils.IFileUtils;
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import java.io.File;
import java.io.IOException;

/**
 * @description 后台上传服务 录音文件
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 04/06/2017 4:23 PM
 **/
public class UploadService extends IntentService {

    private CusrometRemoteRepo mRepo;
    private MediaPlayer mediaPlayer;


    public UploadService(){
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (mRepo == null) {
            mRepo = CusrometRemoteRepo.getInstance();
        }
        try {
            uploadRecordFiles();
        } catch (Exception e) {

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
                                    File recordFile = recordFiles[j];
                                    if (recordFile != null&&recordFile.exists()) {
                                        mRepo.addRecordFile(recordFile.getName(), customerId,getRecordDuration(mediaPlayer,recordFile), recordFile, new RemetoRepoCallback<FileEntity>() {
                                            @Override
                                            public void onSuccess(FileEntity data) {
                                                try {
                                                    File fileData = new File(data.getFileUrl());
                                                    if (fileData!=null&&fileData.exists()) {
                                                        fileData.delete();//上传完成删除文件
                                                    }
                                                }catch (Exception e){

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
