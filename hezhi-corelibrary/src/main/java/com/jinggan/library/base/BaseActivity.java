package com.jinggan.library.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinggan.library.R;
import com.jinggan.library.base.handler.WaytoHandler;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.ui.widget.SwipeBackLayout;
import com.jinggan.library.utils.IActivityManage;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;

/**
 * BaseActivity
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 12:01
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    /**
     * Toolbar
     */
    private Toolbar mToolbar;
    /**
     * ActionBar
     */
    private ActionBar mActionBar;
    /**
     * ToolBar中间标题
     */
    public TextView mTitleCenterTextView;
    /**
     * ToolBar右边按钮Layout
     */
    private LinearLayout mToolbarMoreLayout;
    /**
     * ToolBar右边TextView
     */
    private TextView mToolbarRightText;
    /**
     * ToolBar右边ImageView
     */
    private ImageView mToolbarRightIV;
    /**
     * 布局实例器
     */
    protected LayoutInflater mLayoutInflater;
    /**
     * 核心内容
     */
    private FrameLayout mLinearLayoutContent;

    /**
     * 侧滑finish
     */
    private SwipeBackLayout swipeBackLayout;

    /**
     * 消息处理Handler
     */
    protected WaytoHandler mHandler;
    /**
     * 软件盘管理类
     */
    protected InputMethodManager mInput;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        IActivityManage.getInstance().addActivity(this);
        init();
    }

    private void init() {
        /*实例化Activity侧滑Finish()*/
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.replaceLayer(this);

        mInput = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);

        findViewById();
        initHandler();
        setToolbarLiftLayoutListener();
    }

    /**
     * 初始化控件
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:21
     */
    private void findViewById() {
        mLayoutInflater = LayoutInflater.from(this);
        mLinearLayoutContent = ButterKnife.findById(this, R.id.WaytoBase_activity_container);
        mToolbar = ButterKnife.findById(this, R.id.WaytoBase_toolbar);
        mToolbarMoreLayout = ButterKnife.findById(this, R.id.WaytoBase_toolbar_more_layout);
        mToolbarRightIV = ButterKnife.findById(this, R.id.WaytoBase_toolbar_more_add_icon);
        mToolbarRightText = ButterKnife.findById(this, R.id.WaytoBase_toolbar_more_text);
        mTitleCenterTextView = ButterKnife.findById(this, R.id.WaytoBase_toolbar_center_title_tv);
        appBarLayout = ButterKnife.findById(this, R.id.WaytoBase_app_bar_layout);

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = mLayoutInflater.inflate(layoutResID, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLinearLayoutContent.removeAllViews();
        mLinearLayoutContent.addView(view, lp);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mLinearLayoutContent.removeAllViews();
        mLinearLayoutContent.addView(view, params);
    }

    @Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLinearLayoutContent.removeAllViews();
        mLinearLayoutContent.addView(view, lp);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, android.R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        if (swipeBackLayout.isSwipeFinished()) {
            super.finish();
            overridePendingTransition(0, 0);
        } else {
            swipeBackLayout.cancelPotentialAnimation();
            super.finish();
            overridePendingTransition(0, R.anim.slide_out_right);
        }
    }

    /**
     * 初始化Handler
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 20:23
     */
    private void initHandler() {
        mHandler = new WaytoHandler(BaseActivity.this) {
            @Override
            public void handleMessage(Message msg) {
                BaseActivity.this.dispatchMessage(msg);
            }
        };
    }

    /**
     * Handler事件分发处理
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 20:23
     */
    protected void dispatchMessage(Message msg) {
    }

    /**
     * 设置Toolbar左边监听器
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 20:25
     */
    private void setToolbarLiftLayoutListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToolbarLeftButton();
            }
        });

    }

    /**
     * 设置左边Toolbar监听器
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:28
     */
    private void setToolbarRightLayoutListener() {
        mToolbarMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToolbarRightLayout();
            }
        });
    }

    /**
     * Toolbar返回Btn监听
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:28
     */
    public void onClickToolbarLeftButton() {
        this.finish();
    }

    /**
     * Toolbar右边Layout监听
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:28
     */
    public void onClickToolbarRightLayout() {
    }

    /**
     * 设置Toolbar标题
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:28
     *
     * @param resid
     */
    public void setToolbarTitle(int resid) {
        mToolbar.setTitle(resid);
        mToolbarRightText.setVisibility(View.GONE);
        mToolbarRightIV.setVisibility(View.GONE);
    }

    /**
     * 设置Toolbar标题
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:23
     *
     * @param str
     */
    public void setToolbarTitle(String str) {
        mToolbar.setTitle(str);
        mToolbarRightText.setVisibility(View.GONE);
        mToolbarRightIV.setVisibility(View.GONE);
    }

    /**
     * 设置中间标题
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:23
     *
     * @param resId
     */
    public void setToolbarCenterTitle(int resId) {
        mToolbar.setTitle("");
        mTitleCenterTextView.setText(resId);
        mTitleCenterTextView.setVisibility(View.VISIBLE);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
    }

    /**
     * 设置中间标题
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:23
     */
    public void setToolbarCenterTitle(String title) {
        mToolbar.setTitle("");
        mTitleCenterTextView.setText(title);
        mTitleCenterTextView.setVisibility(View.VISIBLE);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
    }

    /**
     * 设置Toolbar显示状态
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:24
     *
     * @param visibility
     */
    public void setToolbarVisibility(int visibility) {
        mToolbar.setVisibility(visibility);
//        mToolbarRightText.setVisibility(View.VISIBLE);
//        mToolbarRightIV.setVisibility(View.GONE);
    }


    /**
     * 设置Toolbar右边Layout文字
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:24
     *
     * @param resid
     */
    public void setToolbarRightText(int resid) {
        setToolbarRightLayoutListener();
        mToolbarRightText.setText(resid);
        mToolbarRightText.setVisibility(View.VISIBLE);
        mToolbarRightIV.setVisibility(View.GONE);
    }

    /**
     * 设置Toolbar右边Layout是否可见
     * <p>
     * author: zls
     * created at 2017/4/14 17:24
     *
     * @param visiable
     */
    public void setToolbarRightLayoutVisibility(boolean visiable) {
        if (visiable) {
            mToolbarRightIV.setVisibility(View.VISIBLE);
            mToolbarRightText.setVisibility(View.VISIBLE);
        } else {
            mToolbarRightIV.setVisibility(View.GONE);
            mToolbarRightText.setVisibility(View.GONE);
        }
    }

    /**
     * 设置Toolbar右边Layout文字
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:24
     *
     * @param str
     */
    public void setToolbarRightText(String str) {
        setToolbarRightLayoutListener();
        mToolbarRightText.setText(str);
        mToolbarRightText.setVisibility(View.VISIBLE);
        mToolbarRightIV.setVisibility(View.GONE);
    }

    /**
     * 置空
     * <p>
     * author: hezhiWu
     * created at 2017/5/5 23:11
     */
    public void setToolbarRightEmtity() {
        mToolbarMoreLayout.setOnClickListener(null);
        mToolbarRightText.setText("");
        mToolbarRightText.setVisibility(View.VISIBLE);
        mToolbarRightIV.setVisibility(View.GONE);
    }

    /**
     * 设置右边是否可点击
     * <p>
     * author: hezhiWu
     * created at 2017/4/17 22:44
     */
    public void setRightLayoutEnable(boolean enable) {
        mToolbarMoreLayout.setEnabled(enable);
    }

    /**
     * 设置Toolbar右边Layout图片资源
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 17:26
     *
     * @param resid
     */
    public void setToolbarRightImage(int resid) {
        setToolbarRightLayoutListener();
        mToolbarRightIV.setImageResource(resid);
        mToolbarRightIV.setVisibility(View.VISIBLE);
        mToolbarRightText.setVisibility(View.GONE);
    }

    /**
     * 设置Activity是否支持滑动Finsh
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 20:28
     *
     * @param enabled
     */
    public void setSwipeEnabled(boolean enabled) {
        swipeBackLayout.setSwipeEnabled(enabled);
    }

    /**
     * 显示软键盘
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 20:31
     */
    public void showSoftInput(final EditText edittext) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                edittext.requestFocus();
                if (mInput != null) mInput.showSoftInput(edittext, 0);
            }
        }, 700);
    }

    /**
     * 隐藏软键盘
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 20:31
     */
    public void hideSoftInput(EditText et) {
        if (null != mInput && mInput.isActive()) {
            mInput.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Toast
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 20:33
     */
    public void showToast(int resid) {
        ToastUtil.showToast(this, resid);
    }

    /**
     * Toast
     * <p>
     * author: hezhiWu
     * created at 2017/3/14 20:33
     */
    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    /**
     * 显示进度对话框
     * du yang
     * @param msg
     */
    public void showProgressDlg(String msg){
        if (progressDialog==null) {
            progressDialog= DialogFactory.createLoadingDialog(this,msg);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
        /*在Fragment直接回调处理*/
            FragmentManager fm = getSupportFragmentManager();
            List<Fragment> fragments = fm.getFragments();
            if (fragments != null && fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*在Fragment直接回调处理*/
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
