package com.easydear.user.module.business;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.easydear.user.module.business.data.soruce.BussinessRepo;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;
import com.jinggan.library.ui.widget.WaytoTabLayout;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.IStringUtils;
import com.jinggan.library.utils.ISystemUtil;
import com.jinggan.library.utils.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/7/5 21:16
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class BusinessDetailsActivity extends BaseActivity implements RemetoRepoCallbackV2<BusinessDetailEntity> {

    @BindView(R.id.business_bg_img)
    ImageView businessBgImg;
    @BindView(R.id.business_logo)
    ImageView businessLogo;
    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.business_info)
    TextView businessInfo;
    @BindView(R.id.business_address)
    TextView businessAddress;
    @BindView(R.id.business_tab_layout)
    WaytoTabLayout businessTabLayout;

    private Dialog dialog;
    private String[] mTabNames;
    private List<BaseFragment> mFragments = new ArrayList<>();

    private BusinessDetailEntity businessDetailEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);
        ButterKnife.bind(this);
        setToolbarTitle("商家详情");

        mTabNames = getResources().getStringArray(R.array.business_tab_array);

        BussinessRepo.getInstance().queryBusinessDetail(getIntent().getStringExtra("businessNo"), this);
    }

    @Override
    public void onClickToolbarRightLayout() {
        BussinessRepo.getInstance().addVIP(businessDetailEntity.getBusinessNo(), new RemetoRepoCallbackV2<Void>() {
            @Override
            public void onReqStart() {
                dialog=DialogFactory.createLoadingDialog(BusinessDetailsActivity.this,"添加会员...");
            }

            @Override
            public void onSuccess(Void data) {
                showToast("添加成功");
                setToolbarRightLayoutVisibility(false);
            }

            @Override
            public void onFailure(int code, String msg) {
                showToast(msg);
            }

            @Override
            public void onFinish() {
                DialogFactory.dimissDialog(dialog);
            }
        });
    }

    @OnClick({R.id.business_location, R.id.business_phone, R.id.business_buy_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.business_location:
                if (businessDetailEntity == null) {
                    showToast("数据异常");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putDouble("lng", IStringUtils.toDouble(businessDetailEntity.getLongitude()));
                bundle.putDouble("lat", IStringUtils.toDouble(businessDetailEntity.getLatitude()));

                ISkipActivityUtil.startIntent(this, LocationActivity.class, bundle);
                break;
            case R.id.business_phone:
                if (businessDetailEntity==null){
                    showToast("数据异常");
                    return;
                }

                if (PermissionHelper.checkPermission(this, Manifest.permission.CALL_PHONE, 0x3001)) {
                    ISystemUtil.makeCall(this, businessDetailEntity.getTelephone(), false);
                }
                break;
            case R.id.business_buy_button:
                break;
        }
    }

    private void initUI(BusinessDetailEntity entity){
        if (entity==null){
            return;
        }

        businessName.setText(entity.getBusinessName());
        businessInfo.setText(entity.getBrandName());
        businessAddress.setText(entity.getAddress());

        String isVip=entity.getIsVip();
        if ("0".equals(isVip)){
            setToolbarRightText("添加会员");
        }

         /*商家宣传图片*/
        Glide.with(this).load(BuildConfig.DOMAIN + businessDetailEntity.getBusinessImages())
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.main_img_defaultpic_small)
                .into(businessBgImg);

        /*商家Logo*/
        Glide.with(this).load(BuildConfig.DOMAIN + businessDetailEntity.getLogo())
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.default_head_img)
                .error(R.mipmap.default_head_img)
                .into(new RoundedBitmapImageViewTarget(this, businessLogo));


    }

    private void initTab(){
        Bundle bundle=new Bundle();
        bundle.putSerializable("entity",businessDetailEntity);

        ShopFragment shopFragment = new ShopFragment();
        shopFragment.setArguments(bundle);

        BusinessDetailFragment businessDetailFragment = new BusinessDetailFragment();
        businessDetailFragment.setArguments(bundle);

        MemberFragment memberFragment = new MemberFragment();
        memberFragment.setArguments(bundle);

        mFragments.add(shopFragment);
        mFragments.add(businessDetailFragment);
        mFragments.add(memberFragment);

        businessTabLayout.initTabLayout(getSupportFragmentManager(), mFragments, mTabNames);
    }

    @Override
    public void onReqStart() {
        dialog = DialogFactory.createLoadingDialog(this, "加载...");
    }

    @Override
    public void onSuccess(BusinessDetailEntity data) {
        this.businessDetailEntity=data;
        initUI(data);
        initTab();
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(msg);
        finish();
    }

    @Override
    public void onFinish() {
        DialogFactory.dimissDialog(dialog);
    }
}
