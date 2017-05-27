package com.xiaomai.telemarket.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.xiaomai.telemarket.receiver.OutgoingCallReciver;
import com.xiaomai.telemarket.receiver.OutgoingCallState;
import com.xiaomai.telemarket.receiver.TelListener;
import com.xiaomai.telemarket.utils.PhoneRecordUtil;
import com.xiaomai.telemarket.utils.ReadPhoneLogThread;


/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 来去电状态监听服务
 * @createtime 14/04/2017 8:39 PM
 **/
public class PhoneCallStateService extends Service {

    private static final String TAG = "PhoneCallStateService";
    private OutgoingCallState outgoingCallState;
    private OutgoingCallReciver outgoingCallReciver;
    private PhoneRecordUtil recorder;
    private ReadPhoneLogThread readPhoneLogThread;

    public static void StartService(Context activity){
        Intent intent = new Intent(activity,PhoneCallStateService.class);
        activity.startService(intent);
    }

    public static void StopService(Context activity){
        Intent intent = new Intent(activity,PhoneCallStateService.class);
        activity.stopService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //------以下应放在onStartCommand中，但2.3.5以下版本不会因service重新启动而重新调用--------
        //监听电话状态，如果是打入且接听 或者 打出 则开始自动录音
        //通话结束，保存文件到外部存储器上
        Log.i(TAG, "启动去电监听服务，正在监听中...");
        outgoingCallState = new OutgoingCallState(this);
        outgoingCallState.startListen();
        recorder = PhoneRecordUtil.getINSTANCE();
        outgoingCallReciver = new OutgoingCallReciver(recorder);
//        ToastUtil.showToast(this,"服务已启动");
        //去电 注册
        IntentFilter outgoingCallFilter = new IntentFilter();
        outgoingCallFilter.addAction(OutgoingCallState.ForeGroundCallState.IDLE);
        outgoingCallFilter.addAction(OutgoingCallState.ForeGroundCallState.DIALING);
        outgoingCallFilter.addAction(OutgoingCallState.ForeGroundCallState.ALERTING);
        outgoingCallFilter.addAction(OutgoingCallState.ForeGroundCallState.ACTIVE);
        outgoingCallFilter.addAction(OutgoingCallState.ForeGroundCallState.DISCONNECTED);
        //系统电话状态
        outgoingCallFilter.addAction("android.intent.action.PHONE_STATE");
        outgoingCallFilter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);//"android.intent.action.NEW_OUTGOING_CALL"

        //注册接收者
        registerReceiver(outgoingCallReciver, outgoingCallFilter);

        //来电
        TelephonyManager telmgr = (TelephonyManager) getSystemService(
                Context.TELEPHONY_SERVICE);
        telmgr.listen(new TelListener(recorder), PhoneStateListener.LISTEN_CALL_STATE);

        //开启读取系统log线程
        startReadLogThread();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(outgoingCallReciver);
        stopReadLogThread();
        Log.i(TAG, "已关闭电话监听服务");
    }

    public class MsgBinder extends Binder {

        public PhoneCallStateService getService(){
            return PhoneCallStateService.this;
        }

    }

    public void startReadLogThread() {
//        if (readPhoneLogThread==null) {
//            readPhoneLogThread = new ReadPhoneLogThread();
//            readPhoneLogThread.start();
//            readPhoneLogThread.getLooper();
//        }
//        readPhoneLogThread.starWorking();
    }

    public ReadPhoneLogThread getReadPhoneLogThread() {
        return readPhoneLogThread;
    }

    public void stopReadLogThread(){
        if (readPhoneLogThread != null) {
            readPhoneLogThread.quit();
        }
    }
}
