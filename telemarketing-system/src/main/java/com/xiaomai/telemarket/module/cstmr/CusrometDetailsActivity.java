package com.xiaomai.telemarket.module.cstmr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.widget.WaytoTabLayout;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.fragment.car.CusrometCarFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.company.CusrometCompanyFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.CusrometDebtoFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.DebtoActivity;
import com.xiaomai.telemarket.module.cstmr.fragment.info.CusrometInfoEditActivity;
import com.xiaomai.telemarket.module.cstmr.fragment.info.CusrometInfoShowFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.insurance.CusrometInsurancePolicyFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.property.CusrometPropertyFragment;

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
    private List<BaseFragment> fragments = new ArrayList<>();

    private CusrometInfoShowFragment InfoFragment;
    private CusrometDebtoFragment debtoFragment;
    private CusrometPropertyFragment propertyFragment;
    private CusrometInsurancePolicyFragment insurancePolicyFragment;
    private CusrometCarFragment carFragment;
    private CusrometCompanyFragment companyFragment;

    private CusrometListEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusromet_details);
        ButterKnife.bind(this);
        setToolbarVisibility(View.GONE);
        entity = (CusrometListEntity) getIntent().getSerializableExtra("entity");
        tabNames = getResources().getStringArray(R.array.cusromet_details_tab_array);
        initTabLayout();
    }

    private void initTabLayout() {
        InfoFragment = new CusrometInfoShowFragment();
        InfoFragment.setArguments(getIntent().getExtras());
        fragments.add(InfoFragment);

        debtoFragment = new CusrometDebtoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", entity.getID());
        debtoFragment.setArguments(bundle);
        fragments.add(debtoFragment);

        propertyFragment = new CusrometPropertyFragment();
        propertyFragment.setArguments(bundle);
        fragments.add(propertyFragment);

        insurancePolicyFragment = new CusrometInsurancePolicyFragment();
        insurancePolicyFragment.setArguments(bundle);
        fragments.add(insurancePolicyFragment);

        carFragment = new CusrometCarFragment();
        carFragment.setArguments(bundle);
        fragments.add(carFragment);

        companyFragment = new CusrometCompanyFragment();
        companyFragment.setArguments(bundle);
        fragments.add(companyFragment);

        CusrometDetailsTabLayout.initTabLayout(getSupportFragmentManager(), fragments, tabNames);
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
                int currentItem = CusrometDetailsTabLayout.getViewPager().getCurrentItem();
                if (currentItem == 0) {/*客户信息*/
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("entiy", entity);
                    ISkipActivityUtil.startIntent(this, CusrometInfoEditActivity.class, bundle);
                } else if (currentItem == 1) {/*负债*/
                    DebtoActivity.startIntentToEdit(this, debtoFragment.getEntity());
                }
                break;
        }
    }
}
