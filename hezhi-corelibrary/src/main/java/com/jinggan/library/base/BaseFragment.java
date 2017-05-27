package com.jinggan.library.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.jinggan.library.base.handler.WaytoHandler;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.dialog.ToastUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * BaseFragment
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 12:03
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class BaseFragment extends Fragment {

    /**
     * 消息处理Handler
     */
    protected WaytoHandler mHandler;

    protected ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHandler();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /**
     * 初始化Handler
     */
    private void initHandler() {
        mHandler = new WaytoHandler(getActivity()) {
            @Override
            public void handleMessage(Message msg) {
                BaseFragment.this.dispatchMessage(msg);
            }
        };
    }

    /**
     * Handler事件分发处理
     *
     * @param msg
     */
    protected void dispatchMessage(Message msg) {
    }

    /**
     * Toast
     *
     * @param resid
     */
    public void showToast(int resid) {
        ToastUtil.showToast(getActivity(), resid);
    }

    /**
     * Toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        ToastUtil.showToast(getActivity(), msg);
    }

    /**
     * 显示进度对话框
     * du yang
     * @param msg
     */
    public void showProgressDlg(String msg){
        if (progressDialog==null) {
            progressDialog= DialogFactory.createLoadingDialog(getActivity(),msg);
        }else{
            progressDialog.setMessage(msg);
        }
        progressDialog.show();
    }

    /**
     * 隐藏进度框
     * du yang
     */
    public void dismissProgressDlg(){
        if (progressDialog!=null&&progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}
