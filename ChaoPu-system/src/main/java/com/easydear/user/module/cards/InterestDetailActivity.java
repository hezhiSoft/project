package com.easydear.user.module.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.R;
import com.easydear.user.module.cards.data.InterestDetailEntity;
import com.easydear.user.module.cards.data.source.CardRepo;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LJH on 2017/7/15.
 */

public class InterestDetailActivity extends ChaoPuBaseActivity {

    @BindView(R.id.interest_logo)
    ImageView mInterestLogo;
    @BindView(R.id.business_name)
    TextView mBusinessName;
    @BindView(R.id.interest_name)
    TextView mInterestName;
    @BindView(R.id.interest_bg)
    ImageView mInterestBg;
    @BindView(R.id.interest_endTime)
    TextView mInterestEndTime;
    @BindView(R.id.interest_price)
    TextView mInterestPrice;
    @BindView(R.id.interest_pay)
    Button mInterestPay;

    private InterestDetailEntity mInterestDetailEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_detail);
        ButterKnife.bind(this);
        setToolbarTitle("");

        requestInterestDetail();
    }

    private void requestInterestDetail() {
        String cardNo = getIntent().getStringExtra("cardNo");
        CardRepo.getInstance().queryInterestDetail(cardNo, new RemetoRepoCallbackV2<InterestDetailEntity>() {
            @Override
            public void onReqStart() {

            }

            @Override
            public void onSuccess(InterestDetailEntity data) {
                // TODO
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
