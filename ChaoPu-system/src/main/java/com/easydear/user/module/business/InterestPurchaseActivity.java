package com.easydear.user.module.business;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.R;

import butterknife.ButterKnife;

/**
 * Created by LJH on 2017/7/18.
 */

public class InterestPurchaseActivity extends ChaoPuBaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_purchase);
        ButterKnife.bind(this);
        setToolbarTitle("权益买单");

    }
}
