package com.easydear.user.module.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LJH on 2017/7/12.
 */

public class PayActivity extends ChaoPuBaseActivity {

    @BindView(R.id.pay_business_logo)
    ImageView PayBusiLogo;
    @BindView(R.id.pay_amount)
    TextView PayAmount;
    @BindView(R.id.pay_is_privilege)
    TextView PayPriv;
    @BindView(R.id.pay_wechat_rb)
    RadioButton PayWeChat;
    @BindView(R.id.pay_alipay_rb)
    RadioButton PayAli;

    private BusinessDetailEntity entity;
    private float mAmount;
    private boolean mIsPriv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        entity = (BusinessDetailEntity) intent.getSerializableExtra("entity");
        mAmount = intent.getFloatExtra("amount", 0);
        mIsPriv = intent.getBooleanExtra("isPriv", false);
        setToolbarTitle("支付订单");
        initUI();
    }

    private void initUI() {

        Glide.with(this).load(BuildConfig.DOMAIN + entity.getLogo())
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.default_head_img)
                .error(R.mipmap.default_head_img)
                .into(new RoundedBitmapImageViewTarget(this, PayBusiLogo));

        PayAmount.setText("¥ " + mAmount);
        if (mIsPriv) {
            PayPriv.setText("优惠买单");
        }

        PayWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayWeChat.setSelected(true);
                PayAli.setSelected(false);
                PayAli.setChecked(false);
            }
        });

        PayAli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayAli.setSelected(true);
                PayWeChat.setSelected(false);
                PayWeChat.setChecked(false);
            }
        });
    }

    @OnClick(R.id.Pay_pay_button)
    public void onClick() {
        if (!PayWeChat.isSelected() && !PayAli.isSelected()) {
            showToast("请选择支付方式");
            return;
        } else if (PayWeChat.isSelected()) {
            showToast("微信支付暂未开通");
            return;
        } else if (PayAli.isSelected()) {
            // TODO
            AliPayService.getInstance().pay(PayActivity.this, mAmount + "");
        }
    }
}
