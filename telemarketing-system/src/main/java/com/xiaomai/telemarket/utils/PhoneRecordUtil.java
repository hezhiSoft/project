package com.xiaomai.telemarket.utils;

import android.media.MediaRecorder;
import android.util.Log;

import com.jinggan.library.utils.IFileUtils;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.home.dial.data.source.local.CustomerLocalDataSource;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 录音工具类
 * @createtime 14/04/2017 8:36 PM
 **/
public class PhoneRecordUtil {

    private static final String TAG = "PhoneRecordUtil";

    private String phoneNumber;
    private MediaRecorder mrecorder;
    private boolean started = false; //录音机是否已经启动
    private boolean isCommingNumber = false;//是否是来电
    private CustomerLocalDataSource mLocalCustomerDataSource;

    private static PhoneRecordUtil INSTANCE;

    public static PhoneRecordUtil getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (PhoneRecordUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PhoneRecordUtil();
                }
            }
        }
        return INSTANCE;
    }

    private PhoneRecordUtil() { }

    public void start() {
        started = true;
        mrecorder = new MediaRecorder();

        String callDir = "呼出";
        if (isCommingNumber) {
            callDir = "呼入";
        }
        String directoryName = IFileUtils.getRecordDirectory();
        if (mLocalCustomerDataSource==null) {
            mLocalCustomerDataSource = CustomerLocalDataSource.getInstance();
        }
        CusrometListEntity customerEntity = mLocalCustomerDataSource.getPreCustomer();
        if (customerEntity!=null) {
            directoryName +=File.separator+customerEntity.getExtend();
            File file = new File(directoryName);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        String fileName = callDir + "-" + phoneNumber + "-"+ new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date(System.currentTimeMillis())) + ".mp3";//实际是3gp
        File recordName = new File(directoryName, fileName);
        try {
            recordName.createNewFile();
            Log.d("recorder", "创建文件" + recordName.getName());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("recorder", "创建文件" + recordName.getName());
        }

        mrecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mrecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mrecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        mrecorder.setOutputFile(recordName.getAbsolutePath());

        try {
            mrecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        mrecorder.start();
        started = true;
        Log.d(TAG, "录音开始");
    }

    public void stop() {
        try {
            started = false;
            if (mrecorder != null) {
                mrecorder.stop();
                mrecorder.release();
                mrecorder = null;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "录音结束");
    }

    public void pause() {

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean hasStarted) {
        this.started = hasStarted;
    }

    public boolean isCommingNumber() {
        return isCommingNumber;
    }

    public void setIsCommingNumber(boolean isCommingNumber) {
        this.isCommingNumber = isCommingNumber;
    }
}
