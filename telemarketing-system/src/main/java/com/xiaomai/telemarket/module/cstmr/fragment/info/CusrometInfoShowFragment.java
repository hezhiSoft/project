package com.xiaomai.telemarket.module.cstmr.fragment.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/16$ 下午10:38$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CusrometInfoShowFragment extends BaseFragment {

    @BindView(R.id.CusInfo_CustomerName)
    TextView CusInfoCustomerName;
    @BindView(R.id.CusInfo_CustomerTel)
    TextView CusInfoCustomerTel;
    @BindView(R.id.CusInfo_IsSZHukou)
    TextView CusInfoIsSZHukou;
    @BindView(R.id.CusInfo_Sex)
    TextView CusInfoSex;
    @BindView(R.id.CusInfo_MaritalStatus)
    TextView CusInfoMaritalStatus;
    @BindView(R.id.CusInfo_Payroll)
    TextView CusInfoPayroll;
    @BindView(R.id.CusInfo_BankFlow)
    TextView CusInfoBankFlow;
    @BindView(R.id.CusInfo_AccumulationFundAccount)
    TextView CusInfoAccumulationFundAccount;
    @BindView(R.id.CusInfo_SocialSecurityAccount)
    TextView CusInfoSocialSecurityAccount;
    @BindView(R.id.CusInfo_Remark)
    TextView CusInfoRemark;
    Unbinder unbinder;

    private CusrometListEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entity = (CusrometListEntity) getArguments().getSerializable("entity");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cusromet_info, null);
        unbinder = ButterKnife.bind(this, rootView);
        initUI(entity);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initUI(CusrometListEntity entity) {
        if (entity == null) {
            return;
        }
        CusInfoCustomerName.setText(entity.getCustomerName());
        CusInfoCustomerTel.setText(entity.getCustomerTel());
        CusInfoIsSZHukou.setText(entity.getIsSZHukou() == 0 ? "否" : "是");
        CusInfoSex.setText(entity.getSex() == 0 ? "男" : "女");
        CusInfoMaritalStatus.setText(entity.getMaritalStatus() == 0 ? "未婚" : "已婚");
        CusInfoPayroll.setText(entity.getWage() + "");
        CusInfoBankFlow.setText(entity.getAccountWater() + "");
        CusInfoAccumulationFundAccount.setText(entity.getAccumulationFundAccount() + "");
        CusInfoSocialSecurityAccount.setText(entity.getSocialSecurityAccount() + "");
        CusInfoRemark.setText(entity.getRemark());

    }

    /**
     * 客户ID
     * <p>
     * author: hezhiWu
     * created at 2017/5/20 14:09
     */
    public String getCusrometId() {
        if (entity != null) {
            return entity.getID();
        }
        return "";
    }
}
