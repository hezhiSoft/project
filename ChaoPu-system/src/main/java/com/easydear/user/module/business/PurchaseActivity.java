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
import com.easydear.user.module.pay.PayActivity;
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
                    PurchaseTotalNumber.setText(charSequence.toString() + "元");
                } else {
                    PurchaseTotalNumber.setText("0.00元");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.Purchase_pay_button)
    public void onClick() {
        if (TextUtils.isEmpty(PurchaseTotalNumber.getText().toString())) {
            showToast("输入支付金额");
            return;
        } else {
            gotoPayActivity();
        }

    }

    private void gotoPayActivity() {
        if (entity != null) {
            String totalNum = PurchaseTotalNumber.getText().toString().replace("元", "");
            float amount = Float.parseFloat(totalNum);

            String disNumTxt = PurchaseDisTotal.getText().toString();
            boolean isPriv = false;
            if (TextUtils.isEmpty(disNumTxt)) {
                isPriv = false;
            } else {
                float disNum = Float.parseFloat(disNumTxt.replace("元", ""));
                if (disNum == 0) {
                    isPriv = false;
                } else if (disNum > 0) {
                    isPriv = true;
                }
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("entity", entity);
            bundle.putFloat("amount", amount);
            bundle.putBoolean("isPriv", isPriv);
            ISkipActivityUtil.startIntent(this, PayActivity.class, bundle);
        }
    }
}
