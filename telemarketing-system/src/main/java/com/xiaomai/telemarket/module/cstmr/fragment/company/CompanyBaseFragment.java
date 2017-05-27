package com.xiaomai.telemarket.module.cstmr.fragment.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.date.DatePickDialog;
import com.jinggan.library.ui.date.OnSureLisener;
import com.jinggan.library.ui.date.bean.DateBean;
import com.jinggan.library.ui.date.bean.DateType;
import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.CompanyEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:58
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CompanyBaseFragment extends BaseFragment {

    @BindView(R.id.Company_CompanyName)
    FormWriteTopTitleView CompanyCompanyName;
    @BindView(R.id.Company_Industry)
    FormWriteTopTitleView CompanyIndustry;
    @BindView(R.id.Company_RegisterDate)
    FormSelectTopTitleView CompanyRegisterDate;
    @BindView(R.id.Company_BusinessScope)
    FormWriteTopTitleView CompanyBusinessScope;
    @BindView(R.id.Company_SharesProportion)
    FormWriteTopTitleView CompanySharesProportion;
    @BindView(R.id.Company_AccountWater)
    FormWriteTopTitleView CompanyAccountWater;
    @BindView(R.id.Company_IsRentTransfer)
    FormSelectTopTitleView CompanyIsRentTransfer;
    @BindView(R.id.Company_AmountDebt)
    FormWriteTopTitleView CompanyAmountDebt;
    @BindView(R.id.Company_Remark)
    FormWriteTopTitleView CompanyRemark;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_company, null);
        unbinder = ButterKnife.bind(this, rootView);
        setListener();
        return rootView;
    }

    private void setListener(){
        CompanyRegisterDate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DatePickDialog dialog = new DatePickDialog(getContext());
                dialog.setTitle("选择日期");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        CompanyRegisterDate.setContentText(date.getYear() + "-" + date.getMoth() + "-" + date.getDay());
                    }
                });
                dialog.show();
            }
        });
        CompanyIsRentTransfer.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DictionaryHelper.showSelectDialog(getContext(),CompanyIsRentTransfer.getTextView(),CompanyIsRentTransfer.getContentText());
            }
        });
    }

    protected void initUI(CompanyEntity entity) {
        if (entity == null) {
            return;
        }
        CompanyCompanyName.setContentText(entity.getCompanyName());
        CompanyIndustry.setContentText(entity.getIndustry());
        CompanyRegisterDate.setContentText(entity.getRegisterDate().replaceAll("T", " "));
        CompanyBusinessScope.setContentText(entity.getBusinessScope());
        CompanySharesProportion.setContentText(entity.getSharesProportion() + "");
        CompanyAccountWater.setContentText(entity.getAccountWater() + "");
        CompanyIsRentTransfer.setContentText(entity.getIsRentTransfer()==0?"否":"是");
        CompanyAmountDebt.setContentText(entity.getAmountDebt()+"");
        CompanyRemark.setContentText(entity.getRemark());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
