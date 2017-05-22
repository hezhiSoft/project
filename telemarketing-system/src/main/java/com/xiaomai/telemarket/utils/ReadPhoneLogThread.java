package com.xiaomai.telemarket.utils;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;


import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.receiver.OutgoingCallState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @description ""
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 18/04/2017 6:59 PM
 **/
public class ReadPhoneLogThread extends HandlerThread {

    private static final String TAG = "OutGoingCall";
    private int logCount;

    private Handler mHandler;
    private Handler mResponseHandler;

    public static final int MSG_READ_LOG=0x001;
    private boolean isStoped;

    public ReadPhoneLogThread() {
        super("ReadPhoneLogThread");
//        this.mResponseHandler = responseHandler;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        if (mHandler==null) {
            mHandler=new Handler(){

                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what==MSG_READ_LOG) {
                        isStoped = false;
                        startReadLog();
                    }
                }
            };
        }
    }

    private void startReadLog() {
        Log.d(TAG, "开始读取日志记录");

        String[] catchParams = {"logcat", "InCallScreen *:s"};
        String[] clearParams = {"logcat", "-c"};

        try {
            Process process = Runtime.getRuntime().exec(catchParams);
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            while ((line = reader.readLine()) != null&&!isStoped) {
                logCount++;
                //输出所有
                Log.v(TAG, line);

                //日志超过512条就清理
                if (logCount > 512) {
                    //清理日志
                    Runtime.getRuntime().exec(clearParams)
                            .destroy();//销毁进程，释放资源
                    logCount = 0;
                    Log.v(TAG, "-----------清理日志---------------");
                }

                /*---------------------------------前台呼叫-----------------------*/
                //空闲
                if (line.contains(ReadPhoneLogThread.CallViewState.FORE_GROUND_CALL_STATE)
                        && line.contains(ReadPhoneLogThread.CallState.IDLE)) {
                    Log.d(TAG, ReadPhoneLogThread.CallState.IDLE);
                }

                //正在拨号，等待建立连接，即已拨号，但对方还没有响铃，
                if (line.contains(ReadPhoneLogThread.CallViewState.FORE_GROUND_CALL_STATE)
                        && line.contains(ReadPhoneLogThread.CallState.DIALING)) {
                    //发送广播
                    Intent dialingIntent = new Intent();
                    dialingIntent.setAction(OutgoingCallState.ForeGroundCallState.DIALING);
                    DataApplication.getInstance().sendBroadcast(dialingIntent);

                    Log.d(TAG, ReadPhoneLogThread.CallState.DIALING);
                }

                //呼叫对方 正在响铃
                if (line.contains(ReadPhoneLogThread.CallViewState.FORE_GROUND_CALL_STATE)
                        && line.contains(ReadPhoneLogThread.CallState.ALERTING)) {
                    //发送广播
                    Intent dialingIntent = new Intent();
                    dialingIntent.setAction(OutgoingCallState.ForeGroundCallState.ALERTING);
                    DataApplication.getInstance().sendBroadcast(dialingIntent);

                    Log.d(TAG, ReadPhoneLogThread.CallState.ALERTING);
                }

                //已接通，通话建立
                if (line.contains(ReadPhoneLogThread.CallViewState.FORE_GROUND_CALL_STATE)
                        && line.contains(ReadPhoneLogThread.CallState.ACTIVE)) {
                    //发送广播
                    Intent dialingIntent = new Intent();
                    dialingIntent.setAction(OutgoingCallState.ForeGroundCallState.ACTIVE);
                    DataApplication.getInstance().sendBroadcast(dialingIntent);

                    Log.d(TAG, ReadPhoneLogThread.CallState.ACTIVE);
                }

                //断开连接，即挂机
                if (line.contains(ReadPhoneLogThread.CallViewState.FORE_GROUND_CALL_STATE)
                        && line.contains(ReadPhoneLogThread.CallState.DISCONNECTED)) {
                    //发送广播
                    Intent dialingIntent = new Intent();
                    dialingIntent.setAction(OutgoingCallState.ForeGroundCallState.DISCONNECTED);
                    DataApplication.getInstance().sendBroadcast(dialingIntent);

                    Log.d(TAG, ReadPhoneLogThread.CallState.DISCONNECTED);
                }

            } //END while

        } catch (IOException e) {
            e.printStackTrace();
        } //END try-catch
    }

    /**
     * start working
     */
    public void starWorking(){
        Log.v(TAG, "startWorking# mHandle==null?" + (mHandler == null));
        if (mHandler!=null) {
            mHandler.sendEmptyMessage(MSG_READ_LOG);
        }
    }

    /**
     * 前后台电话
     *
     * @author sdvdxl
     */
    private static class CallViewState {
        public static final String FORE_GROUND_CALL_STATE = "setCallState";//"mForeground";
    }

    /**
     * 呼叫状态
     *
     * @author sdvdxl
     */
    private static class CallState {
        public static final String DIALING = "正在拨号";//正在拨号
        public static final String ALERTING = "ALERTING";//正在拨号
        public static final String ACTIVE = "正在通话";//正在通话
        public static final String IDLE = "null";//null
        public static final String DISCONNECTED = "正在挂断";//正在挂断
//        public static final String DISCONNECTED = "DISCONNECTED正在挂断";//正在挂断 public static final String DIALING = "DIALING正在拨号";//正在拨号
//        public static final String ALERTING = "ALERTING";//正在拨号
//        public static final String ACTIVE = "ACTIVE正在通话";//正在通话
//        public static final String IDLE = "IDLEnull";//null
//        public static final String DISCONNECTED = "DISCONNECTED正在挂断";//正在挂断
    }
}
