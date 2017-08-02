package com.easydear.user.module.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.easydear.user.module.pay.DiscountPayActivity;
import com.jinggan.library.utils.ISkipActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/7/11 20:35
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class PurchaseActivity extends ChaoPuBaseActivity {

    @BindView(R.id.Purchase_Total)
    EditText PurchaseTotal;
    @BindView(R.id.Purchase_not_action_Total)
    EditText PurchaseNotActionTotal;
    @BindView(R.id.Purchase_dis_Total)
    TextView PurchaseDisTotal;
    @BindView(R.id.Purchase_Total_number)
    TextView PurchaseTotalNumber;

    private BusinessDetailEntity entity;

    private Float mTotalAmount = 0.0f;
    private Float mDisAmount = 0.0f;
    private Float mRealPayAmount = 0.0f;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        ButterKnife.bind(this);
        entity = (BusinessDetailEntity) getIntent().getSerializableExtra("entity");
        if (entity != null) {
            setToolbarTitle(entity.getBusinessName());
        }

        initUI();
    }

    private void initUI() {
        PurchaseTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    String total = charSequence.toString();
                    mTotalAmount = Float.parseFloat(total);
                    if (TextUtils.isEmpty(PurchaseNotActionTotal.getText())) {
                        PurchaseTotalNumber.setText(mTotalAmount + "元");
                    } else {
                        String real = PurchaseNotActionTotal.getText().toString();
                        mRealPayAmount = Float.parseFloat(real);
                        mDisAmount = mTotalAmount - mRealPayAmount;
                        PurchaseDisTotal.setText(mDisAmount + "元");
                        PurchaseTotalNumber.setText(mRealPayAmount + "元");
                    }
                } else {
                    PurchaseTotalNumber.setText("0.00元");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        PurchaseNotActionTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    String real = charSequence.toString();
                    mRealPayAmount = Float.parseFloat(real);
                    PurchaseTotalNumber.setText(mRealPayAmount + "元");
                    if (TextUtils.isEmpty(PurchaseTotal.getText())) {
                        PurchaseDisTotal.setText("0.00元");
                    } else {
                        String total = PurchaseTotal.getText().toString();
                        mTotalAmount = Float.parseFloat(total);
                        mDisAmount = mTotalAmount - mRealPayAmount;
                        PurchaseDisTotal.setText(mDisAmount + "元");
                    }
                } else {
                    PurchaseDisTotal.setText("0.00元");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.Purchase_pay_button)
    public void onClick() {
        if (TextUtils.isEmpty(PurchaseTotalNumber.getText().toString()) || TextUtils.isEmpty(PurchaseNotActionTotal.getText().toString())) {
            showToast("输入消费总额或不参与优惠金额");
            return;
        } else {
            startPayActivity();
        }

    }

    private void startPayActivity() {
        if (entity != null) {
            String totalNum = PurchaseTotal.getText().toString();
            String disNum = PurchaseDisTotal.getText().toString().replace("元", "");
            String realNum = PurchaseTotalNumber.getText().toString().replace("元", "");

            Float totalAmount = Float.parseFloat(totalNum);
            Float disAmount = Float.parseFloat(disNum);
            Float realAmount = Float.parseFloat(realNum);
            if (disAmount < 0) {
                showToast("优惠金额小于0");
                return;
            } else if (realAmount < 0) {
                showToast("实付金额小于0");
                return;
            }
            if (totalAmount != disAmount + realAmount) {
                showToast("Error");
                return;
            }

            boolean isPriv = false;
            if (disAmount == 0) {
                isPriv = false;
            } else if (disAmount > 0) {
                isPriv = true;
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("entity", entity);
            bundle.putFloat("total_amount", totalAmount);
            bundle.putFloat("dis_amount", disAmount);
            bundle.putFloat("real_amount", realAmount);
            bundle.putBoolean("isPriv", isPriv);
            ISkipActivityUtil.startIntent(this, DiscountPayActivity.class, bundle);
        }
    }
}
