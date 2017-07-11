package com.easydear.user.module.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.R;
import com.jinggan.library.base.BaseActivity;


/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-02-26
 * Time: 15:52
 * Version:1.0
 */

public class AboutActivity extends ChaoPuBaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setToolbarTitle("关于易兑");
    }
}
