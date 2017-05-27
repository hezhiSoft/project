package com.xiaomai.telemarket.module.cstmr.fragment.property;

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
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryDialog;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 22:10
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class PropertyBaseFragment extends BaseFragment {

    @BindView(R.id.Property_LandUse)
    FormSelectTopTitleView PropertyLandUse;
    @BindView(R.id.Property_AreaM)
    FormWriteTopTitleView PropertyAreaM;
    @BindView(R.id.Property_Area)
    FormSelectTopTitleView PropertyArea;
    @BindView(R.id.Property_CompletionDate)
    FormSelectTopTitleView PropertyCompletionDate;
    @BindView(R.id.Property_PropertyRightsYear)
    FormWriteTopTitleView PropertyPropertyRightsYear;
    @BindView(R.id.Property_RegistrationPrice)
    FormWriteTopTitleView PropertyRegistrationPrice;
    @BindView(R.id.Property_VillageName)
    FormWriteTopTitleView PropertyVillageName;
    @BindView(R.id.Property_DetailedAddress)
    FormWriteTopTitleView PropertyDetailedAddress;
    @BindView(R.id.Property_Remark)
    FormWriteTopTitleView PropertyRemark;
    @BindView(R.id.Property_IsMortgage)
    FormSelectTopTitleView PropertyIsMortgage;
    @BindView(R.id.Property_MortgageBank)
    FormSelectTopTitleView PropertyMortgageBank;
    @BindView(R.id.Property_MonthlyPaymentLoan)
    FormWriteTopTitleView PropertyMonthlyPaymentLoan;
    @BindView(R.id.Property_MortgageTimeLimit)
    FormWriteTopTitleView PropertyMortgageTimeLimit;
    @BindView(R.id.Property_RemainingMortgage)
    FormWriteTopTitleView PropertyRemainingMortgage;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_property, null);
        unbinder = ButterKnife.bind(this, rootView);
        setListener();
        return rootView;
    }

    private void setListener() {
        /*土地用地*/
        PropertyLandUse.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getLandUse();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setItemData(list)
                        .setSelectContent(PropertyLandUse.getContentText())
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    PropertyLandUse.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(),getClass().getSimpleName());
            }
        });
        /*所属区域*/
        PropertyArea.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getSZAREA();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setItemData(list)
                        .setSelectContent(PropertyArea.getContentText())
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    PropertyArea.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(),getClass().getSimpleName());
            }
        });
        /*竣工日期*/
        PropertyCompletionDate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DatePickDialog dialog = new DatePickDialog(getContext());
                dialog.setTitle("选择日期");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        PropertyCompletionDate.setContentText(date.getYear() + "-" + date.getMoth() + "-" + date.getDay());
                    }
                });
                dialog.show();
            }
        });
        /*是否按揭*/
        PropertyIsMortgage.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DictionaryHelper.showSelectDialog(getContext(),PropertyIsMortgage.getTextView(),PropertyIsMortgage.getContentText().toString());
            }
        });
        /*按揭银行*/
        PropertyMortgageBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DictionaryEntity> list = DictionaryHelper.getBankData();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setItemData(list)
                        .setSelectContent(PropertyMortgageBank.getContentText())
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    PropertyMortgageBank.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(),getClass().getSimpleName());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
