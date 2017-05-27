package com.xiaomai.telemarket.module.cstmr.fragment.property;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.ui.date.DatePickDialog;
import com.jinggan.library.ui.date.OnSureLisener;
import com.jinggan.library.ui.date.bean.DateBean;
import com.jinggan.library.ui.date.bean.DateType;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.jinggan.library.utils.IStringUtils;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
import com.xiaomai.telemarket.module.cstmr.data.PropertyEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryDialog;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

    private String LandUseCode, AreaCode, BandCode;

    protected Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_property, null);
        unbinder = ButterKnife.bind(this, rootView);
        setListener();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(true);
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
                                    LandUseCode = entity.getCode();
                                    PropertyLandUse.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(), getClass().getSimpleName());
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
                                    AreaCode = entity.getCode();
                                    PropertyArea.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(), getClass().getSimpleName());
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
                DictionaryHelper.showSelectDialog(getContext(), PropertyIsMortgage.getTextView(), PropertyIsMortgage.getContentText().toString());
            }
        });
        /*按揭银行*/
        PropertyMortgageBank.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getBankData();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setItemData(list)
                        .setSelectContent(PropertyMortgageBank.getContentText())
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    BandCode = entity.getCode();
                                    PropertyMortgageBank.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(), getClass().getSimpleName());
            }
        });
    }

    protected void initUI(PropertyEntity entity) {
        if (entity == null) {
            return;
        }
        LandUseCode = String.valueOf(entity.getLandUse());
        AreaCode = entity.getArea();
        BandCode = entity.getMortgageBank();

        PropertyLandUse.setContentText(DictionaryHelper.ParseLandUse(entity.getLandUse() + ""));
        PropertyAreaM.setContentText(entity.getAreaM() + "");
        PropertyArea.setContentText(DictionaryHelper.ParseSZAREA(entity.getArea() + ""));
        PropertyCompletionDate.setContentText(entity.getCompletionDate().replaceAll("T", " "));
        PropertyPropertyRightsYear.setContentText(entity.getPropertyRightsYear() + "");
        PropertyRegistrationPrice.setContentText(entity.getRegistrationPrice() + "");
        PropertyVillageName.setContentText(entity.getVillageName());
        PropertyDetailedAddress.setContentText(entity.getDetailedAddress());
        PropertyRemark.setContentText(entity.getRemark());
        PropertyIsMortgage.setContentText(entity.getIsMortgage() == 0 ? "否" : "是");
        PropertyMortgageBank.setContentText(DictionaryHelper.ParseBank(entity.getMortgageBank()));
        PropertyMonthlyPaymentLoan.setContentText(entity.getMonthlyPaymentLoan() + "");
        PropertyMortgageTimeLimit.setContentText(entity.getMortgageTimeLimit() + "");
        PropertyRemainingMortgage.setContentText(entity.getRemainingMortgage() + "");
    }

    protected PropertyEntity getPropertyEntity() {
        PropertyEntity entity = new PropertyEntity();
        entity.setLandUse(IStringUtils.toInt(LandUseCode));
        entity.setAreaM(IStringUtils.toInt(PropertyAreaM.getContentText()));
        entity.setArea(AreaCode);
        entity.setCompletionDate(PropertyCompletionDate.getContentText());
        entity.setPropertyRightsYear(IStringUtils.toInt(PropertyPropertyRightsYear.getContentText()));
        entity.setRegistrationPrice(IStringUtils.toInt(PropertyRegistrationPrice.getContentText()));
        entity.setVillageName(PropertyVillageName.getContentText());
        entity.setDetailedAddress(PropertyDetailedAddress.getContentText());
        entity.setRemark(PropertyRemark.getContentText());
        entity.setIsMortgage("是".equals(PropertyIsMortgage.getContentText()) ? 1 : 0);
        entity.setMortgageBank(BandCode);
        entity.setMonthlyPaymentLoan(IStringUtils.toInt(PropertyMonthlyPaymentLoan.getContentText()));
        entity.setMortgageTimeLimit(IStringUtils.toInt(PropertyMortgageTimeLimit.getContentText()));
        entity.setRemainingMortgage(IStringUtils.toInt(PropertyRemainingMortgage));
        return entity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Subscribe
    public void onEventBusSubmit(EventBusValues values) {
        if (values.getWhat() == 0x1002) {
            DialogFactory.showMsgDialog(getContext(), "提交", "确定提交当前记录?", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSubmit();
                }
            });
        }
    }

    public void onSubmit() {

    }
}
