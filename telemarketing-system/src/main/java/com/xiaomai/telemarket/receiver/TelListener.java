package com.xiaomai.telemarket.receiver;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.utils.PhoneRecordUtil;

import org.greenrobot.eventbus.EventBus;


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
                if (mRecord.isStarted()) {
                    mRecord.stop();
                    ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, false);
                    // TODO: 04/06/2017 挂断电话 通知到客户信息编辑界面
                    String dialingType = ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.DIALING_TYPE_KEY, "").toString();
                    if (!TextUtils.isEmpty(dialingType)) {
                        EventBusValues values=new EventBusValues();
                        values.setWhat(0x10102);
                        EventBus.getDefault().post(values);
                    }
//                    ContactsUtils.getINSTANCE().deleteContact(DataApplication.getInstance().getApplicationContext(), CustomerLocalDataSource.getInstance().getPreCustomer().getCustomerName());
                    Log.i(TAG, "onCallStateChanged #CALL_STATE_IDLE" + "挂断");
                }
                break;
            case TelephonyManager.CALL_STATE_RINGING: // 来电响铃
                Log.i(TAG, "onCallStateChanged #CALL_STATE_RINGING"+",接到"+incomingNumber+"来电！");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK: // 摘机，即接通
                Log.i(TAG, "onCallStateChanged #CALL_STATE_OFFHOOK" + "，接通" + incomingNumber);
                ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_KEY, true);
                if (!mRecord.isStarted()) {
                    mRecord.start();
                }
                break;
        }
        Log.i(TAG, String.valueOf(incomingNumber));
    }
}
