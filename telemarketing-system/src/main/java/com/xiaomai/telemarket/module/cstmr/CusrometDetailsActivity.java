package com.xiaomai.telemarket.module.cstmr;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.ui.widget.WaytoTabLayout;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.IStringUtils;
import com.jinggan.library.utils.ISystemUtil;
import com.jinggan.library.utils.PermissionHelper;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.fragment.car.CarActivity;
import com.xiaomai.telemarket.module.cstmr.fragment.car.CusrometCarFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.company.CompanyActivity;
import com.xiaomai.telemarket.module.cstmr.fragment.company.CusrometCompanyFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.CusrometDebtoFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.DebtoActivity;
import com.xiaomai.telemarket.module.cstmr.fragment.file.CusrometFileFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.file.FileActivity;
import com.xiaomai.telemarket.module.cstmr.fragment.follow.CusrometFolloFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.follow.FollowActivity;
import com.xiaomai.telemarket.module.cstmr.fragment.info.CusrometInfoActivity;
import com.xiaomai.telemarket.module.cstmr.fragment.info.CusrometInfoShowFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.insurance.CusrometInsurancePolicyFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.insurance.InsuranceActivity;
import com.xiaomai.telemarket.module.cstmr.fragment.property.CusrometPropertyFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.property.PropertyActivity;
import com.xiaomai.telemarket.module.home.dial.data.source.local.CustomerLocalDataSource;
import com.xiaomai.telemarket.utils.ContactsUtils;

import org.greenrobot.eventbus.EventBus;

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

public class CusrometDetailsActivity extends XiaoMaiBaseActivity {

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
    private CusrometFileFragment fileFragment;
    private CusrometFolloFragment folloFragment;

    public static CusrometListEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusromet_details);
        ButterKnife.bind(this);
        setToolbarVisibility(View.GONE);
        entity = (CusrometListEntity) getIntent().getSerializableExtra("entity");
        tabNames = getResources().getStringArray(R.array.cusromet_details_tab_array);
        initTabLayout();
        ISharedPreferencesUtils.setValue(this, Constant.IS_IN_CUSTOMER_DETAIL_UI, true);
        Log.i("DUY", "customer detail onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            entity = (CusrometListEntity) intent.getSerializableExtra("entity");
            if (entity != null) {
                // TODO: 07/06/2017 singletask 需要在这里重新更新每个fragment的UI数据
                EventBusValues values=new EventBusValues();
                values.setWhat(0x890);
                values.setObject(entity);
                EventBus.getDefault().post(values);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ISharedPreferencesUtils.setValue(this, Constant.IS_IN_CUSTOMER_DETAIL_UI, false);
        Log.i("DUY", "customer detail activity onDestroy() ");
         /*判断当前是否处于群呼状态,通知群呼下一个号码*/
        boolean isCall = IStringUtils.toBool(ISharedPreferencesUtils.getValue(this, Constant.IS_DIALING_GROUP_FINISHED, false).toString());
        if (isCall){
            EventBusValues busValues = new EventBusValues();
            busValues.setWhat(0x10101);
            busValues.setObject(true);
            EventBus.getDefault().post(busValues);
        }
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

        fileFragment = new CusrometFileFragment();
        fileFragment.setArguments(bundle);
        fragments.add(fileFragment);

        folloFragment = new CusrometFolloFragment();
        folloFragment.setArguments(bundle);
        fragments.add(folloFragment);

        CusrometDetailsTabLayout.initTabLayout(getSupportFragmentManager(), fragments, tabNames);

    }

    @OnClick({R.id.CusrometDetails_Back_ImageView, R.id.CusrometDetails_phone_ImageView, R.id.CusrometDetails_Edit_ImageView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CusrometDetails_Back_ImageView:
                finish();
                break;
            case R.id.CusrometDetails_phone_ImageView:
                if (PermissionHelper.checkPermission(this, Manifest.permission.CALL_PHONE, 0x999)) {
                    ISharedPreferencesUtils.setValue(this, Constant.NOT_SEND_DIALING_MSG, true);
                    ContactsUtils.getINSTANCE().saveCustomerToContacts(DataApplication.getInstance().getApplicationContext(), entity.getCustomerName(), entity.getCustomerTel(), new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            //拨出
                            CustomerLocalDataSource.getInstance().setPreCustomer(entity);
                            ISystemUtil.makeCall(CusrometDetailsActivity.this, entity.getCustomerTel(), true);
                        }
                    });
                }
                break;
            case R.id.CusrometDetails_Edit_ImageView:
                int currentItem = CusrometDetailsTabLayout.getViewPager().getCurrentItem();
                if (currentItem == 0) {/*客户信息*/
                    CusrometInfoActivity.startIntentToEdit(this, InfoFragment.getCusromentEntity());
                } else if (currentItem == 1) {/*负债*/
                    if (debtoFragment.getEntity() == null) {
                        showToast("选择编辑明细");
                        return;
                    }
                    DebtoActivity.startIntentToEdit(this, debtoFragment.getEntity());
                } else if (currentItem == 2) {/*房产*/
                    if (propertyFragment.getEntity() == null) {
                        showToast("选择编辑明细");
                        return;
                    }
                    PropertyActivity.startIntentToEdit(this, propertyFragment.getEntity());
                } else if (currentItem == 3) {/*保单*/
                    if (insurancePolicyFragment.getEntity() == null) {
                        showToast("选择编辑明细");
                        return;
                    }
                    InsuranceActivity.startIntentToEdit(this, insurancePolicyFragment.getEntity());
                } else if (currentItem == 4) {/*汽车*/
                    if (carFragment.getEntity() == null) {
                        showToast("选择编辑明细");
                        return;
                    }
                    CarActivity.startIntentToEdit(this, carFragment.getEntity());
                } else if (currentItem == 5) {/*公司*/
                    if (companyFragment.getEntity() == null) {
                        showToast("选择编辑明细");
                        return;
                    }
                    CompanyActivity.startIntentToEdit(this, companyFragment.getEntity());
                } else if (currentItem == 6) {/*文件资料*/
                    if (fileFragment.getEntity() == null) {
                        showToast("选择编辑明细");
                        return;
                    }
                    FileActivity.startIntentToEdit(this, fileFragment.getEntity());
                } else if (currentItem == 7) {/*跟进明细*/
                    if (folloFragment.getEntity() == null) {
                        showToast("选择编辑明细");
                        return;
                    }
                    FollowActivity.startIntentToEdit(this, folloFragment.getEntity());
                }
                break;
        }
    }

    public WaytoTabLayout getTabLayout() {
        return CusrometDetailsTabLayout;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x999) {
            ISharedPreferencesUtils.setValue(this, Constant.NOT_SEND_DIALING_MSG, true);
            ContactsUtils.getINSTANCE().saveCustomerToContacts(DataApplication.getInstance().getApplicationContext(), entity.getCustomerName(), entity.getCustomerTel(), new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    //拨出
                    CustomerLocalDataSource.getInstance().setPreCustomer(entity);
                    ISystemUtil.makeCall(CusrometDetailsActivity.this, entity.getCustomerTel(), true);
                }
            });
        }
    }
}
