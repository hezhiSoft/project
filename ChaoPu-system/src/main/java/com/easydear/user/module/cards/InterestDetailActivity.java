package com.easydear.user.module.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.R;
import com.easydear.user.module.cards.data.InterestDetailEntity;
import com.easydear.user.module.cards.data.source.CardRepo;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;
import com.jinggan.library.utils.ILogcat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LJH on 2017/7/15.
 */

public class InterestDetailActivity extends ChaoPuBaseActivity {

    @BindView(R.id.interest_logo)
    ImageView mInterestLogo;
    @BindView(R.id.interest_business_name)
    TextView mBusinessName;
    @BindView(R.id.interest_name)
    TextView mInterestName;
    @BindView(R.id.interest_bg)
    ImageView mInterestBg;
    @BindView(R.id.interest_endTime)
    TextView mInterestEndTime;
    @BindView(R.id.interest_price)
    TextView mInterestPrice;
    @BindView(R.id.interest_receive)
    Button mInterestReceive;

    private InterestDetailEntity mInterestDetailEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_detail);
        ButterKnife.bind(this);
        setToolbarTitle("权益详情");

        requestInterestDetail();
    }

    private void requestInterestDetail() {
        String cardNo = getIntent().getStringExtra("cardNo");
        CardRepo.getInstance().queryInterestDetail(cardNo, new RemetoRepoCallbackV2<InterestDetailEntity>() {
            @Override
            public void onReqStart() {

            }

            @Override
            public void onSuccess(InterestDetailEntity entity) {
                mInterestDetailEntity = entity;
                if (mInterestDetailEntity != null) {
                    mBusinessName.setText(mInterestDetailEntity.getBusinessName());
                    mInterestName.setText(mInterestDetailEntity.getCardName());
                    mInterestEndTime.setText("可用时间: " + mInterestDetailEntity.getCardStartTime() + "~" + mInterestDetailEntity.getCardEndTime());
                    mInterestPrice.setText("¥" + mInterestDetailEntity.getCardPrice());

                    /*商家Logo*/
                    Glide.with(InterestDetailActivity.this).load(BuildConfig.DOMAIN + mInterestDetailEntity.getLogo())
                            .asBitmap()
                            .centerCrop()
                            .placeholder(R.mipmap.default_head_img)
                            .error(R.mipmap.default_head_img)
                            .into(new RoundedBitmapImageViewTarget(InterestDetailActivity.this, mInterestLogo));

                    /*商家宣传图片*/
                    Glide.with(InterestDetailActivity.this).load(BuildConfig.DOMAIN + mInterestDetailEntity.getCardImg())
                            .placeholder(R.mipmap.default_image)
                            .error(R.mipmap.main_img_defaultpic_small)
                            .into(mInterestBg);

                    if ("1".equals(mInterestDetailEntity.getIsHaveCard())) {
                        mInterestReceive.setBackgroundColor(getResources().getColor(R.color.gray, null));
                        mInterestReceive.setClickable(false);
                    }
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                showToast(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @OnClick({R.id.interest_receive})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.interest_receive:
                showToast(mInterestDetailEntity.getIsHaveCard());
                receiveInterest();
                break;
            default:
                break;
        }
    }

    private void receiveInterest() {
        String cardNo = mInterestDetailEntity.getCardNo();
        String businessNo = mInterestDetailEntity.getBusinessNo();
        CardRepo.getInstance().receiveInterestCard(cardNo, businessNo, new RemetoRepoCallbackV2<String>() {
            @Override
            public void onReqStart() {

            }

            @Override
            public void onSuccess(String msg) {
                mInterestReceive.setBackgroundColor(getResources().getColor(R.color.gray, null));
                mInterestReceive.setClickable(false);
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
