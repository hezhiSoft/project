package com.xiaomai.telemarket.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xiaomai.telemarket.R;

import butterknife.ButterKnife;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/4$ 下午2:50$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class SelecteConditionTileView extends LinearLayout{

    private RadioGroup radioGroup;
    private RadioButton yesRadioButton;
    private RadioButton noRadioButton;
    private TextView textView;

    private int status=-1;
    private OnCheckedChangeLisetener checkedChangeLisetener;

    public SelecteConditionTileView(Context context){
        super(context,null);
    }

    public SelecteConditionTileView(Context context, AttributeSet attri){
        super(context,attri);
        initView();
        parseAttributeset(context,attri,0);
    }

    private void initView(){
        View rootView= LayoutInflater.from(getContext()).inflate(R.layout.selecte_condition_view,null);
        radioGroup= ButterKnife.findById(rootView,R.id.Selecte_condition_radioGrop);
        textView=ButterKnife.findById(rootView,R.id.selecte_condition_title);
        yesRadioButton=ButterKnife.findById(rootView,R.id.Selecte_condition_yes_radioButton);
        noRadioButton=ButterKnife.findById(rootView,R.id.Selecte_condition_no_radioButton);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.Selecte_condition_yes_radioButton:
                        status=1;
                        break;
                    case R.id.Selecte_condition_no_radioButton:
                        status=0;
                        break;
                }
            }
        });
        addView(rootView);
    }

    private void parseAttributeset(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelecteConditionTileView, defStyle, 0);
        String title=typedArray.getString(R.styleable.SelecteConditionTileView_selectTitle);
        if (!TextUtils.isEmpty(title)){
            textView.setText(title);
        }
    }

    public int getStatus(){
        return status;
    }

    public SelecteConditionTileView setTitleText(String title){
        textView.setText(title);
        return this;
    }

    public SelecteConditionTileView setStatus(int status){
        this.status=status;
        if (status==0){
            noRadioButton.setChecked(true);
        }else if (status==1){
            yesRadioButton.setChecked(true);
        }
        return this;
    }

    public SelecteConditionTileView setRadioGropEnabled(boolean enabled){
        radioGroup.setEnabled(enabled);
        yesRadioButton.setEnabled(enabled);
        noRadioButton.setEnabled(enabled);
        return this;
    }

    public SelecteConditionTileView setCheckedChangeListener(OnCheckedChangeLisetener checkedChangeListener){
        this.checkedChangeLisetener=checkedChangeListener;
        return this;
    }

    public interface OnCheckedChangeLisetener{
        void onCheckChange(int status);
    }

}
