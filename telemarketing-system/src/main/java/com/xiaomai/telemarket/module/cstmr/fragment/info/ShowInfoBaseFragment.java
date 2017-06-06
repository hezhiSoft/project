package com.xiaomai.telemarket.module.cstmr.fragment.info;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.jinggan.library.utils.IStringUtils;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryDialog;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;
import com.xiaomai.telemarket.module.cstmr.fragment.follow.FollowActivity;
import com.xiaomai.telemarket.view.widget.SelecteConditionTileView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/28$ 下午3:14$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class ShowInfoBaseFragment extends BaseFragment {

    @BindView(R.id.Info_CustomerName)
    FormWriteTopTitleView InfoCustomerName;
    @BindView(R.id.Info_CustomerTel)
    FormWriteTopTitleView InfoCustomerTel;
    @BindView(R.id.Info_IsSZHukou)
    SelecteConditionTileView InfoIsSZHukou;
    @BindView(R.id.Info_Sex)
    FormSelectTopTitleView InfoSex;
    @BindView(R.id.Info_MaritalStatus)
    FormSelectTopTitleView InfoMaritalStatus;
    @BindView(R.id.Info_Payroll)
    FormWriteTopTitleView InfoPayroll;
    @BindView(R.id.Info_BankFlow)
    FormWriteTopTitleView InfoBankFlow;
    @BindView(R.id.Info_AccumulationFundAccount)
    FormWriteTopTitleView InfoAccumulationFundAccount;
    @BindView(R.id.Info_SocialSecurityAccount)
    FormWriteTopTitleView InfoSocialSecurityAccount;
    @BindView(R.id.Info_Tel_status)
    SelecteConditionTileView InfoTelStatus;
    @BindView(R.id.Info_intention_status)
    SelecteConditionTileView InfoIntentionStatus;

    private String SexCode, MaritalStatusCode;

    @BindView(R.id.Info_Remark)
    FormWriteTopTitleView InfoRemark;

    protected CusrometListEntity entity;

    protected Dialog dialog;
    protected boolean isShowDialog=true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_cusromet_info, null);
        ButterKnife.bind(this, rootView);
        setListener();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void setListener() {
//        InfoIsSZHukou.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
//            @Override
//            public void onClick(TextView textView) {
//                DictionaryHelper.showSelectDialog(getContext(), InfoIsSZHukou.getTextView(), InfoIsSZHukou.getContentText());
//            }
//        });
//
//        InfoTelStatus.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
//            @Override
//            public void onClick(TextView textView) {
//                final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
//                View rootView = LayoutInflater.from(getContext()).inflate(R.layout.select_layout, null);
//                RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.Dialog_RadioGroup);
//                RadioButton isButton = ButterKnife.findById(rootView, R.id.Dialog_is);
//                RadioButton noButton = ButterKnife.findById(rootView, R.id.Dialog_no);
//                rootView.findViewById(R.id.Select_close_ImageView).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                if ("是".equals(InfoTelStatus.getContentText())) {
//                    isButton.setChecked(true);
//                } else if ("否".equals(InfoTelStatus.getContentText())) {
//                    noButton.setChecked(true);
//                }
//                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                        if (checkedId == R.id.Dialog_is) {
//                            InfoTelStatus.getTextView().setText("是");
//                            //TODO 設置電話為空號，接口調用
//                            CusrometRemoteRepo.getInstance().setEmptyTel(entity.getID(), null);
//                        } else if (checkedId == R.id.Dialog_no) {
//                            InfoTelStatus.getTextView().setText("否");
//                        }
//                        dialog.dismiss();
//                    }
//                });
//                dialog.setContentView(rootView);
//                dialog.show();
//            }
//        });

//        InfoIntentionStatus.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
//            @Override
//            public void onClick(TextView textView) {
//                final BottomSheetDialog dialog = new BottomSheetDialog(getContext());
//                View rootView = LayoutInflater.from(getContext()).inflate(R.layout.select_layout, null);
//                RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.Dialog_RadioGroup);
//                RadioButton isButton = ButterKnife.findById(rootView, R.id.Dialog_is);
//                RadioButton noButton = ButterKnife.findById(rootView, R.id.Dialog_no);
//                rootView.findViewById(R.id.Select_close_ImageView).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                if ("是".equals(InfoIntentionStatus.getContentText())) {
//                    isButton.setChecked(true);
//                } else if ("否".equals(InfoIntentionStatus.getContentText())) {
//                    noButton.setChecked(true);
//                }
//                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                        if (checkedId == R.id.Dialog_is) {
//                            InfoIntentionStatus.getTextView().setText("是");
//                            DialogFactory.showMsgDialog(getContext(), "", "是否设置下次跟进时间", "设置", "关闭", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    FollowActivity.startIntentToAdd(getActivity());
//                                }
//                            }, null);
//                        } else if (checkedId == R.id.Dialog_no) {
//                            InfoIntentionStatus.getTextView().setText("否");
//                        }
//                        dialog.dismiss();
//                    }
//                });
//                dialog.setContentView(rootView);
//                dialog.show();
//            }
//        });
        InfoSex.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getSex();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setSelectContent(InfoSex.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    SexCode = entity.getCode();
                                    InfoSex.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(), getClass().getSimpleName());
            }
        });
        InfoMaritalStatus.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getMaritalStatus();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setSelectContent(InfoMaritalStatus.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    MaritalStatusCode = entity.getCode();
                                    InfoMaritalStatus.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(), getClass().getSimpleName());
            }
        });

    }

    protected void initUI(CusrometListEntity entity) {
        if (entity == null) {
            return;
        }
        SexCode = entity.getSex() + "";
        MaritalStatusCode = entity.getMaritalStatus() + "";

        InfoCustomerName.setContentText(entity.getCustomerName()).setItemEnabled(false);
        InfoCustomerTel.setContentText(entity.getCustomerTel()).setItemEnabled(false);
        InfoIsSZHukou.setStatus(entity.getIsSZHukou());
        InfoSex.setContentText(DictionaryHelper.ParseSex(entity.getSex() + ""));
        InfoMaritalStatus.setContentText(DictionaryHelper.ParseMaritalStatus(entity.getMaritalStatus() + ""));
        InfoPayroll.setContentText(entity.getWage() + "");
        InfoBankFlow.setContentText(entity.getAccountWater() + "");
        InfoAccumulationFundAccount.setContentText(entity.getAccumulationFundAccount() + "");
        InfoSocialSecurityAccount.setContentText(entity.getSocialSecurityAccount() + "");
        InfoRemark.setContentText(entity.getRemark());
    }

    protected CusrometListEntity getCusrometEntity() {
        if (entity == null) {
            entity = new CusrometListEntity();
        }
        entity.setCustomerName(InfoCustomerName.getContentText());
        entity.setCustomerTel(InfoCustomerTel.getContentText());
        entity.setIsSZHukou(InfoIsSZHukou.getStatus());
        entity.setSex(IStringUtils.toInt(SexCode));
        entity.setMaritalStatus(IStringUtils.toInt(MaritalStatusCode));
        entity.setWage(IStringUtils.toInt(InfoPayroll.getContentText()));
        entity.setAccountWater(IStringUtils.toInt(InfoBankFlow.getContentText()));
        entity.setAccumulationFundAccount(IStringUtils.toInt(InfoAccumulationFundAccount.getContentText()));
        entity.setSocialSecurityAccount(IStringUtils.toInt(InfoSocialSecurityAccount.getContentText()));
        entity.setRemark(InfoRemark.getContentText());

        return entity;
    }

    @Subscribe
    public void onEventBusSubmit(EventBusValues values) {
        if (values.getWhat() == 0x10010) {
            if (TextUtils.isEmpty(InfoCustomerName.getContentText())){
                showToast("客户名字不能为空");
                return;
            }
            if (TextUtils.isEmpty(InfoCustomerTel.getContentText())){
                showToast("客户电话号码不能为空");
                return;
            }
            if (InfoIntentionStatus.getStatus() == 1) {
                DialogFactory.showMsgDialog(getContext(), "设置提示", "是否设置下次跟进时间?", "设置", "提交", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        FollowActivity.startIntentToAdd(getActivity());
                       isShowDialog=false;
                        FollowActivity.startIntentToQuery(getActivity(),entity.getCustomerTel(),entity.getID());
                        onSubmit();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isShowDialog=true;
                        onSubmit();
                    }
                });
            }else {
                isShowDialog=true;
                onSubmit();
            }
        }
    }

    public void onSubmit() {

    }
}
