package com.easydear.user.module.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.R;
import com.jinggan.library.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by LJH on 2017/1/15.
 */

public class MessageActivity extends ChaoPuBaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_message);
        setToolbarTitle("消息");
        ButterKnife.bind(this);

        addFragment();
    }

    private void addFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.message_container_FrameLayout, MessageFragment.newInstance());
        transaction.commit();
    }
}
