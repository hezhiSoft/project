package com.xiaomai.telemarket.module.cstmr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.widget.WaytoTabLayout;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.fragment.CusrometCarFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.CusrometCompanyFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.CusrometDebtoFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.info.CusrometInfoEditActivity;
import com.xiaomai.telemarket.module.cstmr.fragment.info.CusrometInfoShowFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.CusrometInsurancePolicyFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.CusrometPropertyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/16$ 下午9:53$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CusrometDetailsActivity extends BaseActivity {

    @BindView(R.id.CusrometDetails_tabLayout)
    WaytoTabLayout CusrometDetailsTabLayout;

    private String[] tabNames;
    private List<BaseFragment> fragments=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusromet_details);
        ButterKnife.bind(this);
        setToolbarVisibility(View.GONE);
        tabNames=getResources().getStringArray(R.array.cusromet_details_tab_array);
        initTabLayout();
    }

    private void initTabLayout(){
        CusrometInfoShowFragment cusrometInfoFragment=new CusrometInfoShowFragment();
        cusrometInfoFragment.setArguments(getIntent().getExtras());
        fragments.add(cusrometInfoFragment);

        fragments.add(new CusrometDebtoFragment());
        fragments.add(new CusrometPropertyFragment());
        fragments.add(new CusrometInsurancePolicyFragment());
        fragments.add(new CusrometCarFragment());
        fragments.add(new CusrometCompanyFragment());
        CusrometDetailsTabLayout.initTabLayout(getSupportFragmentManager(),fragments,tabNames);
    }

    @OnClick({R.id.CusrometDetails_Back_ImageView, R.id.CusrometDetails_phone_ImageView, R.id.CusrometDetails_Edit_ImageView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CusrometDetails_Back_ImageView:
                finish();
                break;
            case R.id.CusrometDetails_phone_ImageView:
                break;
            case R.id.CusrometDetails_Edit_ImageView:
                if (CusrometDetailsTabLayout.getViewPager().getCurrentItem()==0){
                    ISkipActivityUtil.startIntent(this, CusrometInfoEditActivity.class);
                }
                break;
        }
    }
}
