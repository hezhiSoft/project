package com.xiaomai.telemarket.module.home.dial;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinggan.library.base.BaseActivity;
import com.xiaomai.telemarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 拨号界面
 * @createtime 26/05/2017 2:17 AM
 **/
public class DialingActivity extends BaseActivity {

    @BindView(R.id.img_dial_switch)
    ImageView imgDialSwitch;
    @BindView(R.id.tv_dial_name)
    TextView tvDialName;
    @BindView(R.id.tv_dial_state)
    TextView tvDialState;
    @BindView(R.id.img_dial_mute)
    ImageView imgDialMute;
    @BindView(R.id.img_dial_mark_empty)
    ImageView imgDialMarkEmpty;
    @BindView(R.id.img_dial_handoff)
    ImageView imgDialHandoff;
    @BindView(R.id.img_dial_stop)
    ImageView imgDialStop;
    @BindView(R.id.img_dial_cancel)
    ImageView imgDialCancel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialing);
        ButterKnife.bind(this);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(option);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setToolbarVisibility(View.GONE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //防止再次点击屏幕时恢复状态栏和导航栏
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus&&Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(option);
        }
    }
}
