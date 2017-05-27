package com.xiaomai.telemarket.receiver;

import android.content.Context;
import android.util.Log;

/**
 * @description 去电状态
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 14/04/2017 8:43 PM
 **/
public class OutgoingCallState {
    Context ctx;
    public OutgoingCallState(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * 前台呼叫状态
     * @author sdvdxl
     *
     */
    public static final class ForeGroundCallState {
        //空闲状态
        public static final String IDLE ="com.xiaomai.telemarket.FORE_GROUND_IDLE";
        //正在拨号，等待建立连接
        public static final String DIALING ="com.xiaomai.telemarket.FORE_GROUND_DIALING";//
        //呼出成功，对方正在响铃声
        public static final String ALERTING ="com.xiaomai.telemarket.FORE_GROUND_ALERTING";
        //对话接通，通话建立
        public static final String ACTIVE ="com.xiaomai.telemarket.FORE_GROUND_ACTIVE";
        //已经断开，挂机
        public static final String DISCONNECTED ="com.xiaomai.telemarket.FORE_GROUND_DISCONNECTED";
    }

    /**
     * 开始监听呼出状态的转变，
     * 并在对应状态发送广播
     */
    public void startListen() {
        // TODO: 14/04/2017 线程管理
//        ReadPhoneLog.getInstance(ctx).start();
        Log.d("Recorder", "开始监听呼出状态的转变，并在对应状态发送广播");

    }
}
