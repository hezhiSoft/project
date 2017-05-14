package com.jinggan.library.ui.dialog;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * 公共引用PopupWindow
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:13
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CommPopupWindow extends PopupWindow {

    public CommPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height, true);

        /**
         * 设置 PopupWindow 可点击内部操作
         * 设置透明背景，让PopupWindow点击外部消失
         * 设置 PopupWindow's contentView 的可点击操作
         */

        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0x55000000));
        getContentView().setFocusableInTouchMode(true);
        getContentView().setFocusable(true);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);

    }

    public void showAtButton(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }
}
