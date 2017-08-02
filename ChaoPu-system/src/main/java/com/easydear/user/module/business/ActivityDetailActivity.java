package com.easydear.user.module.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.R;
import com.easydear.user.module.business.data.ActivityDetailEntity;
import com.easydear.user.module.business.data.soruce.BussinessRepo;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LJH on 2017/7/22.
 */

public class ActivityDetailActivity extends ChaoPuBaseActivity implements RemetoRepoCallbackV2<ActivityDetailEntity> {

    @BindView(R.id.activity_detail_name)
    TextView mActivityName;
    @BindView(R.id.activity_detail_remark)
    TextView mActivityRemark;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);
        ButterKnife.bind(this);
        setToolbarTitle("活动详情");
        requestActivityDetail();
    }

    private void requestActivityDetail() {
        int activityId = getIntent().getIntExtra("activity_id", 0);
        BussinessRepo.getInstance().queryActivityDetail(activityId, this);
    }

    @Override
    public void onReqStart() {

    }

    @Override
    public void onSuccess(ActivityDetailEntity entity) {
        if (entity == null) {
            return;
        }
        mActivityName.setText(entity.getActivityName());
        mActivityRemark.setText(entity.getRemark());
    }

    @Override
    public void onFailure(int code, String msg) {

    }

    @Override
    public void onFinish() {

    }
}
