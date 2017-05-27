package com.xiaomai.telemarket.module.cstmr.fragment.insurance;

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
import com.xiaomai.telemarket.module.cstmr.data.InsuranceEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryDialog;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 保单
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:58
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class InsuranceBaseFragment extends BaseFragment {

    @BindView(R.id.Insurance_InsuranceCompany)
    FormSelectTopTitleView InsuranceInsuranceCompany;
    @BindView(R.id.Insurance_InsuredAmount)
    FormWriteTopTitleView InsuranceInsuredAmount;
    @BindView(R.id.Insurance_PaymentMethods)
    FormSelectTopTitleView InsurancePaymentMethods;
    @BindView(R.id.Insurance_BuyDate)
    FormSelectTopTitleView InsuranceBuyDate;
    @BindView(R.id.Insurance_DelayDate)
    FormSelectTopTitleView InsuranceDelayDate;
    @BindView(R.id.Insurance_DelayDays)
    FormWriteTopTitleView InsuranceDelayDays;
    @BindView(R.id.Insurance_FuXiaoDate)
    FormSelectTopTitleView InsuranceFuXiaoDate;
    @BindView(R.id.Insurance_Remark)
    FormWriteTopTitleView InsuranceRemark;
    Unbinder unbinder;

    private String PaymentMethodsCode,InsuranceCompanyCode;

    protected Dialog dialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_insurance, null);
        unbinder = ButterKnife.bind(this, rootView);
        setListener();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(true);
    }

    private void setListener(){
        /*保险公司*/
        InsuranceInsuranceCompany.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list=DictionaryHelper.getINSURANCECOMPANY();
                DictionaryDialog dictionaryDialog=new DictionaryDialog();
                dictionaryDialog.setItemData(list)
                        .setSelectContent(InsuranceInsuranceCompany.getContentText())
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity!=null){
                                    InsuranceCompanyCode=entity.getCode();
                                    InsuranceInsuranceCompany.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(),getClass().getSimpleName());
            }
        });
        /*缴费方式*/
        InsurancePaymentMethods.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
               List<DictionaryEntity> list=DictionaryHelper.getPayCostType();
                DictionaryDialog dictionaryDialog=new DictionaryDialog();
                dictionaryDialog.setItemData(list)
                        .setSelectContent(InsurancePaymentMethods.getContentText())
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity!=null){
                                    PaymentMethodsCode=entity.getCode();
                                    InsurancePaymentMethods.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(),getClass().getSimpleName());
            }
        });
        /*购卖日期 */
        InsuranceBuyDate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DatePickDialog dialog = new DatePickDialog(getContext());
                dialog.setTitle("选择日期");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        InsuranceBuyDate.setContentText(date.getYear() + "-" + date.getMoth() + "-" + date.getDay());
                    }
                });
                dialog.show();
            }
        });
        /*延期日期*/
        InsuranceDelayDate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DatePickDialog dialog = new DatePickDialog(getContext());
                dialog.setTitle("选择日期");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        InsuranceDelayDate.setContentText(date.getYear() + "-" + date.getMoth() + "-" + date.getDay());
                    }
                });
                dialog.show();
            }
        });
        /*复效日期*/
        InsuranceFuXiaoDate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DatePickDialog dialog = new DatePickDialog(getContext());
                dialog.setTitle("选择日期");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        InsuranceFuXiaoDate.setContentText(date.getYear() + "-" + date.getMoth() + "-" + date.getDay());
                    }
                });
                dialog.show();
            }
        });
    }

    protected void initUI(InsuranceEntity entity){
        if (entity==null){
            return;
        }
        InsuranceCompanyCode=entity.getInsuranceCompany();
        PaymentMethodsCode=String.valueOf(entity.getPaymentMethods());

        InsuranceInsuranceCompany.setContentText(DictionaryHelper.ParseINSURANCECOMPANY(entity.getInsuranceCompany()));
        InsuranceInsuredAmount.setContentText(entity.getInsuredAmount()+"");
        InsurancePaymentMethods.setContentText(DictionaryHelper.ParsePayCostType(entity.getPaymentMethods()+""));
        InsuranceBuyDate.setContentText(entity.getBuyDate().replaceAll("T"," "));
        InsuranceDelayDate.setContentText(entity.getDelayDate().replaceAll("T"," "));
        InsuranceDelayDays.setContentText(entity.getDelayDays()+"");
        InsuranceFuXiaoDate.setContentText(entity.getFuXiaoDate().replaceAll("T"," "));
        InsuranceRemark.setContentText(entity.getRemark());
    }

    protected InsuranceEntity getInsuranceEntity(){
        InsuranceEntity entity=new InsuranceEntity();
        entity.setInsuranceCompany(InsuranceCompanyCode);
        entity.setInsuredAmount(IStringUtils.toInt(InsuranceInsuredAmount.getContentText()));
        entity.setPaymentMethods(IStringUtils.toInt(PaymentMethodsCode));
        entity.setBuyDate(InsuranceBuyDate.getContentText());
        entity.setDelayDate(InsuranceDelayDate.getContentText());
        entity.setDelayDays(IStringUtils.toInt(InsuranceDelayDays.getContentText()));
        entity.setFuXiaoDate(InsuranceFuXiaoDate.getContentText());
        entity.setRemark(InsuranceRemark.getContentText());
        return entity;
    }

    @Subscribe
    public void onEventBusSubmit(EventBusValues values) {
        if (values.getWhat() == 0x1003) {
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
