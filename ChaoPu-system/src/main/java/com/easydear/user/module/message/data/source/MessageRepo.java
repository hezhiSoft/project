package com.easydear.user.module.message.data.source;

import com.easydear.user.api.ChaoPuRetrofitManamer;
import com.easydear.user.api.ResponseEntity;
import com.easydear.user.module.message.data.MessageDetailEntity;
import com.easydear.user.module.message.data.MessageItemEntity;
import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.net.retrofit.RetrofitCallbackV2;
import com.jinggan.library.utils.ILogcat;

import java.util.List;

import retrofit2.Call;

/**
 * Created by LJH on 2017/6/18.
 */

public class MessageRepo implements BaseDataSourse {

    private static String TAG = MessageRepo.class.getSimpleName();

    private Call<ResponseEntity<List<MessageItemEntity>>> mMessageCall;
    private Call<ResponseEntity<List<MessageDetailEntity>>> mMsgDetailCall;

    private static MessageRepo instance;

    public static MessageRepo getInstance() {
        if (instance == null) {
            instance = new MessageRepo();
        }
        return instance;
    }

    /**
     * 获取系统消息列表
     * @param userNo 用户码
     * @param callback
     */
    public void queryTuiMessages(String userNo, final RemetoRepoCallbackV2<List<MessageItemEntity>> callback) {
        callback.onReqStart();
        ILogcat.i(TAG, "queryTuiMessages, useNo = " + userNo);
        String url = "neweasydear-app/information/tuiList?userNo=" + userNo + "&pageSize=1" + "&pageCount=1";
        mMessageCall = ChaoPuRetrofitManamer.getAPIService().queryTuiMessages(url);
        mMessageCall.enqueue(new RetrofitCallbackV2<ResponseEntity<List<MessageItemEntity>>>() {
            @Override
            public void onSuccess(ResponseEntity<List<MessageItemEntity>> data) {
                if (data == null) {
                    ILogcat.e(TAG, "Response data is null!");
                    return;
                }
                if (data.getCode() == 200) {
                    callback.onSuccess(data.getData());
                } else {
                    callback.onFailure(data.getCode(), data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    /**
     * 获取商家消息列表
     * @param userNo 用户码
     * @param callback
     */
    public void queryBusMessages(String userNo, final RemetoRepoCallbackV2<List<MessageItemEntity>> callback) {
        callback.onReqStart();
        ILogcat.i(TAG, "queryBusMessages, useNo = " + userNo);
        String url = "neweasydear-app/information/busInforList?userNo=" + userNo + "&pageSize=1" + "&pageCount=1";
        mMessageCall = ChaoPuRetrofitManamer.getAPIService().queryBusMessages(url);
        mMessageCall.enqueue(new RetrofitCallbackV2<ResponseEntity<List<MessageItemEntity>>>() {
            @Override
            public void onSuccess(ResponseEntity<List<MessageItemEntity>> data) {
                if (data == null) {
                    ILogcat.e(TAG, "Response data is null!");
                    return;
                }
                if (data.getCode() == 200) {
                    callback.onSuccess(data.getData());
                } else {
                    callback.onFailure(data.getCode(), data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    /**
     * 获取单个商家信息详情
     *
     * @param userNo
     * @param businessNo
     * @param pageCount
     * @param pageSize
     * @return
     */
    public void reqMsgDetail(String userNo, String businessNo, int pageCount, int pageSize, final RemetoRepoCallbackV2<List<MessageDetailEntity>> callback) {
        ILogcat.i(TAG, "reqMsgDetail, userNo = " + userNo + ", businessNo = " + businessNo + ", pageCount = " + pageCount + ", pageSize = " + pageSize);
        String url = "neweasydear-app/information/busDetailInforList?userNo=" + userNo + "&businessNo=" + businessNo + "&pageSize=1" + "&pageCount=1";
        mMsgDetailCall = ChaoPuRetrofitManamer.getAPIService().queryMessageDetail(url);
        mMsgDetailCall.enqueue(new RetrofitCallbackV2<ResponseEntity<List<MessageDetailEntity>>>() {
            @Override
            public void onSuccess(ResponseEntity<List<MessageDetailEntity>> data) {
                if (data == null) {
                    ILogcat.e(TAG, "Response data is null!");
                    return;
                }
                if (data.getCode() == 200) {
                    callback.onSuccess(data.getData());
                } else {
                    callback.onFailure(data.getCode(), data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    @Override
    public void cancelRequest() {
        if (mMessageCall != null && !mMessageCall.isCanceled()) {
            mMessageCall.cancel();
        }
    }
}
