package com.easydear.user.module.pay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.R;
import com.easydear.user.module.cards.data.InterestsEntity;
import com.easydear.user.module.cards.data.source.CardRepo;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LJH on 2017/7/18.
 */

public class InterestPayActivity extends ChaoPuBaseActivity {

    @BindView(R.id.interest_name)
    TextView mInterestNameTextView;
    @BindView(R.id.interest_purchase_business)
    TextView mBusinessNameTextView;
    @BindView(R.id.interest_purchase_price)
    TextView mInterestPriceTextView;
    @BindView(R.id.interest_purchase_amount)
    TextView mPurchaseAmountTextView;
    @BindView(R.id.interest_purchase_subtotal)
    TextView mPurchaseSubTotalTextView;
    @BindView(R.id.interest_purchase_total)
    TextView mPurchaseTotalTextView;
    @BindView(R.id.interest_purchase_phone)
    TextView mPhoneNumberTextView;

    private float mInterestPrice;
    private int mPurchaseAmount;
    private float mPurchaseSubTotal;
    private float mPurchaseTotal;

    private InterestsEntity mInterestEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_pay);
        ButterKnife.bind(this);
        setToolbarTitle("权益买单");
        mInterestEntity = (InterestsEntity) getIntent().getSerializableExtra("interest_entity");
        initUI();
    }

    private void initUI() {
        if (mInterestEntity != null) {
            mInterestPrice = Float.valueOf(mInterestEntity.getCardPrice());
            mPurchaseAmount = 0;
            mPurchaseSubTotal = mInterestPrice * mPurchaseAmount;
            mPurchaseTotal = mInterestPrice * mPurchaseAmount;

            mInterestNameTextView.setText(mInterestEntity.getCardName());
            mBusinessNameTextView.setText(mInterestEntity.getBusinessName());
//            mPhoneNumberTextView.setText(mInterestEntity.getCardTransactionNo());
            mInterestPriceTextView.setText(mInterestPrice + "元");
            mPurchaseAmountTextView.setText("" + mPurchaseAmount);
            mPurchaseSubTotalTextView.setText("" + mPurchaseSubTotal);
            mPurchaseTotalTextView.setText("" + mPurchaseTotal);
        }
    }

    @OnClick({R.id.interest_purchase_amount_increase, R.id.interest_purchase_amount_reduce, R.id.interest_purchase_pay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.interest_purchase_amount_increase:
                ++mPurchaseAmount;
                updateAmountAndPrice();
                break;
            case R.id.interest_purchase_amount_reduce:
                if (mPurchaseAmount > 0) {
                    --mPurchaseAmount;
                    updateAmountAndPrice();
                }
                break;
            case R.id.interest_purchase_pay:
                cardOrderPurchase();
                break;
            default:
                break;
        }
    }

    private void updateAmountAndPrice() {
        mPurchaseSubTotal = mInterestPrice * mPurchaseAmount;
        mPurchaseTotal = mInterestPrice * mPurchaseAmount;
        mPurchaseAmountTextView.setText(String.valueOf(mPurchaseAmount));
        mPurchaseSubTotalTextView.setText("" + mPurchaseSubTotal);
        mPurchaseTotalTextView.setText("" + mPurchaseTotal);
    }

    private void cardOrderPurchase() {
        CardRepo.getInstance().cardOrderPay(mInterestEntity.getCardNo(), mInterestEntity.getBusinessName(),
                String.valueOf(mPurchaseAmount), String.valueOf(mPurchaseTotal), new RemetoRepoCallbackV2<PayEntity>() {
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
                                    .pay(InterestPayActivity.this, payEntity.getCardName(), "", payEntity.getPayment());
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
