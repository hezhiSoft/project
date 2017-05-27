package com.xiaomai.telemarket.receiver;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.jinggan.library.ui.dialog.ToastUtil;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.utils.PhoneRecordUtil;


/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 来电状态监听
 * @createtime 14/04/2017 8:47 PM
 **/
public class TelListener extends PhoneStateListener {

    private static final String TAG = "TelListener";

    PhoneRecordUtil mRecord;

    public TelListener(PhoneRecordUtil record) {
        this.mRecord = record;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE: // 空闲状态，即无来电也无去电
                Log.i(TAG, "onCallStateChanged #CALL_STATE_IDLE" + "拨出");
                if (mRecord.isStarted()) {
                    mRecord.stop();
                    ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(),"挂断电话! time:"+ System.currentTimeMillis());
                }
                //此处添加一系列功能代码
                break;
            case TelephonyManager.CALL_STATE_RINGING: // 来电响铃
                Log.i(TAG, "onCallStateChanged #CALL_STATE_RINGING"+",接到"+incomingNumber+"来电！");
                ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(),"接到"+incomingNumber+"来电！");
                //此处添加一系列功能代码
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK: // 摘机，即接通
                Log.i(TAG, "onCallStateChanged #CALL_STATE_OFFHOOK");
                if (!mRecord.isStarted()) {
                    mRecord.start();
                    ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(),""+incomingNumber+"接通 ！");
                }
                //此处添加一系列功能代码
                break;
        }
        Log.i(TAG, String.valueOf(incomingNumber));
    }
}
