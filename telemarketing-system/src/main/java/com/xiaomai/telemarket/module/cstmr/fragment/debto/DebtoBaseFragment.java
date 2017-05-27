package com.xiaomai.telemarket.module.cstmr.fragment.debto;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.ui.date.DatePickDialog;
import com.jinggan.library.ui.date.OnSureLisener;
import com.jinggan.library.ui.date.bean.DateBean;
import com.jinggan.library.ui.date.bean.DateType;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.IStringUtils;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryDialog;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.List;

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
public class DebtoBaseFragment extends BaseFragment {

    @BindView(R.id.Debto_TypeDept)
    FormSelectTopTitleView DebtoTypeDept;
    @BindView(R.id.Debto_LoanAmount)
    FormWriteTopTitleView DebtoLoanAmount;
    @BindView(R.id.Debto_LoanDate)
    FormSelectTopTitleView DebtoLoanDate;
    @BindView(R.id.Debto_LoanBank)
    FormSelectTopTitleView DebtoLoanBank;
    @BindView(R.id.Debto_MonthlyPayments)
    FormWriteTopTitleView DebtoMonthlyPayments;
    @BindView(R.id.Debto_RemainingLoanAmount)
    FormWriteTopTitleView DebtoRemainingLoanAmount;
    @BindView(R.id.Debto_LoanMonth)
    FormWriteTopTitleView DebtoLoanMonth;
    @BindView(R.id.Debto_RepaymentMode)
    FormSelectTopTitleView DebtoRepaymentMode;
    @BindView(R.id.Debto_DelayDays)
    FormWriteTopTitleView DebtoDelayDays;
    @BindView(R.id.Debto_DelayAccount)
    FormWriteTopTitleView DebtoDelayAccount;
    @BindView(R.id.Debto_DelayNum)
    FormWriteTopTitleView DebtoDelayNum;
    @BindView(R.id.Debto_loanProduct)
    FormSelectTopTitleView DebtoLoanProduct;
    @BindView(R.id.Debto_Remark)
    FormWriteTopTitleView DebtoRemark;
    Unbinder unbinder;

    private String typeDeptCod, bankCode, repaymentModeCode;

    protected Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_debto, null);
        unbinder = ButterKnife.bind(this, rootView);
        setListener();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setListener() {
        /*负债类型*/
        DebtoTypeDept.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getDeptData();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setItemData(list)
                        .setSelectContent(DebtoTypeDept.getContentText())
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    typeDeptCod = entity.getCode();
                                    DebtoTypeDept.setContentText(entity.getName());
                                }
                            }
                        });
                dictionaryDialog.show(getFragmentManager(), getClass().getSimpleName());
            }
        });
        /*贷款日期*/
        DebtoLoanDate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DatePickDialog dialog = new DatePickDialog(getContext());
                dialog.setTitle("选择日期");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        String month = "";
                        if (date.getMoth() < 10) {
                            month = "0" + date.getMoth();
                        } else {
                            month = date.getMoth() + "";
                        }
                        String day = "";
                        if (date.getDay() < 10) {
                            day = "0" + date.getDay();
                        } else {
                            day = date.getDay() + "";
                        }
                        DebtoLoanDate.setContentText(date.getYear() + "-" + month + "-" + day);
                    }
                });
                dialog.show();
            }
        });
        /*机构银行*/
        DebtoLoanBank.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getBankData();
                if (list == null || list.size() <= 0) {
                    showToast("数据为空");
                    return;
                }
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setTitle("选择机构银行")
                        .setSelectContent(DebtoLoanBank.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    bankCode = entity.getCode();
                                    DebtoLoanBank.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(), getClass().getSimpleName());
            }
        });
        /*还款方式*/
        DebtoRepaymentMode.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getRepaymentMode();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setSelectContent(DebtoRepaymentMode.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    repaymentModeCode = entity.getCode();
                                    DebtoRepaymentMode.setContentText(entity.getName());
                                }
                            }
                        });
                dictionaryDialog.show(getFragmentManager(), getClass().getSimpleName());
            }
        });
    }

    protected void initUI(DebtoEntity entity) {
        if (entity == null) {
            return;
        }
        typeDeptCod = entity.getTypeDept();
        bankCode = entity.getLoanBank();
        repaymentModeCode = entity.getRepaymentMode();

        DebtoTypeDept.setContentText(DictionaryHelper.ParseDept(entity.getTypeDept() + ""));
        DebtoLoanAmount.setContentText(entity.getLoanAmount() + "");
        DebtoLoanDate.setContentText(entity.getLoanDate().replace("T", " "));
        DebtoLoanBank.setContentText(DictionaryHelper.ParseBank(entity.getLoanBank()));
        DebtoMonthlyPayments.setContentText(entity.getMonthlyPayments() + "");
        DebtoRemainingLoanAmount.setContentText(entity.getRemainingLoanAmount() + "");
        DebtoLoanMonth.setContentText(entity.getLoanMonth() + "");
        DebtoRepaymentMode.setContentText(DictionaryHelper.ParseRepaymentMode(entity.getLoanMonth() + ""));
        DebtoDelayDays.setContentText(entity.getDelayDays() + "");
        DebtoDelayAccount.setContentText(entity.getDelayAccount() + "");
        DebtoDelayNum.setContentText(entity.getDelayNum() + "");
        DebtoLoanProduct.setContentText("无");
        DebtoRemark.setContentText(entity.getRemark());
    }

    /**
     * 获取实体对象
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 17:26
     */
    protected DebtoEntity getDebtoEntity() {
        DebtoEntity entity = new DebtoEntity();
        entity.setTypeDept(typeDeptCod);
        entity.setLoanAmount(IStringUtils.toInt(DebtoLoanAmount.getContentText()));
        entity.setLoanDate(DebtoLoanDate.getContentText());
        entity.setLoanBank(bankCode);
        entity.setMonthlyPayments(IStringUtils.toInt(DebtoMonthlyPayments.getContentText()));
        entity.setLoanMonth(IStringUtils.toInt(DebtoLoanMonth.getContentText()));
        entity.setRepaymentMode(repaymentModeCode);
        entity.setDelayDays(IStringUtils.toInt(DebtoDelayDays.getContentText()));
        entity.setDelayAccount(IStringUtils.toInt(DebtoDelayAccount.getContentText()));
        entity.setDelayNum(IStringUtils.toInt(DebtoDelayNum.getContentText()));
//        entity.setLo
        entity.setRemark(DebtoRemark.getContentText());
        return entity;
    }

    @Subscribe
    public void onEventBusSubmit(EventBusValues values) {
        if (values.getWhat() == 0x1001) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
