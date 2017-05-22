package com.xiaomai.telemarket.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jinggan.library.ui.dialog.ToastUtil;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.utils.PhoneRecordUtil;


/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 去电广播接收器
 * @createtime 14/04/2017 8:41 PM
 **/
public class OutgoingCallReciver extends BroadcastReceiver {

    private static final String TAG = "OutgoingCallReciver";

    private PhoneRecordUtil recorder;

    public OutgoingCallReciver() {
        recorder = PhoneRecordUtil.getINSTANCE();
    }

    public OutgoingCallReciver(PhoneRecordUtil recorder) {
        this.recorder = recorder;
    }

    @Override
    public void onReceive(Context ctx, Intent intent) {
        String phoneState = intent.getAction();

        if (phoneState.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String phoneNum = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);//拨出号码
//            ReadPhoneLog.getInstance(ctx).start();
            recorder.setPhoneNumber(phoneNum);
            recorder.setIsCommingNumber(false);
            ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(),"拨出电话："+phoneNum+"！");
            Log.d(TAG, "设置为去电状态");
            Log.d(TAG, "去电状态 呼叫：" + phoneNum);
        }

        if (phoneState.equals(OutgoingCallState.ForeGroundCallState.DIALING)) {
            Log.d(TAG, "正在拨号...");
        }

        if (phoneState.equals(OutgoingCallState.ForeGroundCallState.ALERTING)) {
            Log.d(TAG, "正在呼叫...");
        }

        if (phoneState.equals(OutgoingCallState.ForeGroundCallState.ACTIVE)) {
            if (!recorder.isCommingNumber() && !recorder.isStarted()) {
                ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(),"拨出电话接通！");
                Log.d(TAG, "去电已接通 启动录音机");
                recorder.start();

            }
        }

        if (phoneState.equals(OutgoingCallState.ForeGroundCallState.DISCONNECTED)) {
            if (!recorder.isCommingNumber() && recorder.isStarted()) {
                Log.d(TAG, "已挂断 关闭录音机");
                ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(),"拨出电话挂断！");
                recorder.stop();
            }
        }
    }
}
