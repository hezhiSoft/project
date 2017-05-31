package com.jinggan.library.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinggan.library.R;

import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/4/24 16:23
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FormWriteTopTitleView extends LinearLayout {

    private TextView mTitleTextView;
    private EditText mContentEditText;
    private ImageView arrowDropImageView;
    private View lineView;

    public FormWriteTopTitleView(Context context) {
        super(context, null);
    }

    public FormWriteTopTitleView(Context context, AttributeSet attri) {
        super(context, attri);
        initView();
        parseAttributeset(context, attri, 0);
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.form_write_top_title, null);
        mTitleTextView = ButterKnife.findById(rootView, R.id.form_writeV2_title);
        mContentEditText = ButterKnife.findById(rootView, R.id.form_writeV2_content);
        arrowDropImageView = ButterKnife.findById(rootView, R.id.form_writeV2_arrow_drop_down);
        lineView = ButterKnife.findById(rootView, R.id.form_writeV2_line);
//        mContentEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b) {
//                    lineView.setBackgroundResource(R.color.badgeItem_color);
//                    mTitleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.badgeItem_color));
//                } else {
//                    lineView.setBackgroundResource(R.color.line);
//                    mTitleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.line));
//                }
//            }
//        });
        addView(rootView);
    }

    private void parseAttributeset(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FormWriteTopTitleView, defStyle, 0);
        String name = typedArray.getString(R.styleable.FormWriteTopTitleView_wvItemName);
        /*Title Name*/
        if (!TextUtils.isEmpty(name)) {
            mTitleTextView.setText(name);
        } else {
            mTitleTextView.setText("Title");
        }

        /*Hint*/
        String hint = typedArray.getString(R.styleable.FormWriteTopTitleView_wvHint);
        if (!TextUtils.isEmpty(hint)) {
            mContentEditText.setHint(hint);
        } else {
            mContentEditText.setHint("请输入");
        }

        /*Enabled*/
        boolean enabled = typedArray.getBoolean(R.styleable.FormWriteTopTitleView_wvEnabled, true);
        mContentEditText.setEnabled(enabled);
        mContentEditText.setFocusable(enabled);

        /*Arrow Drop*/
        Drawable drawable = typedArray.getDrawable(R.styleable.FormWriteTopTitleView_wvArrowDrop);
        if (drawable != null) {
            arrowDropImageView.setImageDrawable(drawable);
        }

        /*parse inputType*/
        int inputType = typedArray.getInt(R.styleable.FormWriteTopTitleView_wvInputType, 0);
        parseInputType(inputType);
        typedArray.recycle();
    }

    private void parseInputType(int inputType) {
        switch (inputType) {
            case 0:
                mContentEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 1:
                mContentEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case 2:
                mContentEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
        }
    }

    /**
     *设置内容
     * 
     *author: hezhiWu
     *created at 2017/5/20 18:05
     */
    public FormWriteTopTitleView setContentText(String text) {
        if (TextUtils.isEmpty(text)) {
            mContentEditText.setHint("无");
        } else
            mContentEditText.setText(text);
        return this;
    }

    public String getContentText(){
        return mContentEditText.getText().toString().trim();
    }

    /**
     *设置Enabled
     * 
     *author: hezhiWu
     *created at 2017/5/20 18:36
     */
    public FormWriteTopTitleView setItemEnabled(boolean enabled) {
        mContentEditText.setEnabled(enabled);
        return this;
    }
}
