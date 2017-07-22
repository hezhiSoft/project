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
import com.easydear.user.module.cards.data.source.CardRepo;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LJH on 2017/7/12.
 */

public class DiscountPayActivity extends ChaoPuBaseActivity {

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

    private BusinessDetailEntity mEntity;
    private float mTotalAmount;
    private float mDisAmount;
    private float mRealAmount;
    private boolean mIsPriv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_pay);
        ButterKnife.bind(this);
        setToolbarTitle("支付订单");
        Intent intent = getIntent();
        mEntity = (BusinessDetailEntity) intent.getSerializableExtra("entity");
        mTotalAmount = intent.getFloatExtra("total_amount", 0);
        mDisAmount = intent.getFloatExtra("dis_amount", 0);
        mRealAmount = intent.getFloatExtra("real_amount", 0);
        mIsPriv = intent.getBooleanExtra("isPriv", false);
        initUI();
    }

    private void initUI() {

        Glide.with(this).load(BuildConfig.DOMAIN + mEntity.getLogo())
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.default_head_img)
                .error(R.mipmap.default_head_img)
                .into(new RoundedBitmapImageViewTarget(this, PayBusiLogo));

        PayAmount.setText("¥ " + mRealAmount);
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
            aliPay();
        }
    }

    private void aliPay() {
        if (mEntity != null) {
            CardRepo.getInstance().discountPay(mEntity.getBusinessNo(), String.valueOf(mTotalAmount),
                    String.valueOf(mDisAmount),String.valueOf( mRealAmount), new RemetoRepoCallbackV2<PayEntity>() {
                @Override
                public void onReqStart() {

                }

                @Override
                public void onSuccess(PayEntity payEntity) {
                    if (payEntity != null) {
                        AliPayService.getInstance()
                                .setPartner(payEntity.getAliNo())
                                .setSeller(payEntity.getAliAccount())
                                .setRsaPrivate(payEntity.getPriKey())
                                .pay(DiscountPayActivity.this, payEntity.getCardName(), "", payEntity.getPayment());
                    }
                }

                @Override
                public void onFailure(int code, String msg) {

                }

                @Override
                public void onFinish() {

                }
            });
        }
    }
}
