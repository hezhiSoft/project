package com.xiaomai.telemarket.module.cstmr.fragment.follow;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;
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
 * created at 2017/5/20 18:58
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FollowBaseFragment extends BaseFragment {

    @BindView(R.id.Follow_FollowType)
    FormSelectTopTitleView FollowFollowType;
    @BindView(R.id.Follow_FollowDate)
    FormSelectTopTitleView FollowFollowDate;
    @BindView(R.id.Follow_InterestedStatus)
    FormSelectTopTitleView FollowInterestedStatus;
    @BindView(R.id.Follow_LoanType)
    FormSelectTopTitleView FollowLoanType;
    @BindView(R.id.Follow_Amount)
    FormWriteTopTitleView FollowAmount;
    @BindView(R.id.Follow_NextFollowType)
    FormSelectTopTitleView FollowNextFollowType;
    @BindView(R.id.Follow_NextFollowDate)
    FormSelectTopTitleView FollowNextFollowDate;
    @BindView(R.id.Follow_NextFollowTime)
    FormWriteTopTitleView FollowNextFollowTime;
    @BindView(R.id.Follow_Remark)
    FormWriteTopTitleView FollowRemark;
    @BindView(R.id.Follow_FollowPerson)
    FormWriteTopTitleView FollowFollowPerson;
    Unbinder unbinder;

    protected Dialog dialog;

    protected String FollowTypeCode, InterestedStatusCode, LoanTypeCode, NextFollowTypeCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_follow, null);
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
        FollowFollowType.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getFollowType();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setSelectContent(FollowFollowType.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    FollowTypeCode = entity.getCode();
                                    FollowFollowType.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(), getClass().getSimpleName());
            }
        });
        FollowFollowDate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DatePickDialog dialog = new DatePickDialog(getContext());
                dialog.setTitle("选择日期");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        FollowFollowDate.setContentText(date.getYear() + "-" + date.getMoth() + "-" + date.getDay());
                    }
                });
                dialog.show();
            }
        });
        FollowInterestedStatus.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getInterestedStatus();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setSelectContent(FollowInterestedStatus.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    InterestedStatusCode = entity.getCode();
                                    FollowInterestedStatus.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(), getClass().getSimpleName());
            }
        });
        FollowLoanType.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getLoanType();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setSelectContent(FollowLoanType.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    LoanTypeCode = entity.getCode();
                                    FollowLoanType.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(), getClass().getSimpleName());
            }
        });
        FollowNextFollowType.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list = DictionaryHelper.getFollowType();
                DictionaryDialog dictionaryDialog = new DictionaryDialog();
                dictionaryDialog.setSelectContent(FollowNextFollowType.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity != null) {
                                    NextFollowTypeCode = entity.getCode();
                                    FollowNextFollowType.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(), getClass().getSimpleName());
            }
        });
        FollowNextFollowDate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DatePickDialog dialog = new DatePickDialog(getContext());
                dialog.setTitle("选择日期");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        FollowNextFollowDate.setContentText(date.getYear() + "-" + date.getMoth() + "-" + date.getDay());
                    }
                });
                dialog.show();
            }
        });
    }

    protected void initUI(FollowEntity entity) {
        if (entity == null) {
            return;
        }
        FollowTypeCode = entity.getFollowType() + "";
        InterestedStatusCode = entity.getInterestedStatus() + "";
        LoanTypeCode = entity.getLoanType() + "";
        NextFollowTypeCode = entity.getNextFollowType() + "";

        FollowFollowType.setContentText(DictionaryHelper.ParseFollowType(entity.getFollowType() + ""));
        FollowFollowDate.setContentText(entity.getFollowDate().replaceAll("T", " ")).setArrowDropVisibility(View.GONE);
        FollowInterestedStatus.setContentText(DictionaryHelper.ParseInterestedStatus(entity.getInterestedStatus() + ""));
        FollowLoanType.setContentText(DictionaryHelper.ParseLoanType(entity.getLoanType() + ""));
        FollowAmount.setContentText(entity.getAmount() + "");
        FollowNextFollowType.setContentText(DictionaryHelper.ParseFollowType(entity.getNextFollowType() + ""));
        FollowNextFollowDate.setContentText(entity.getNextFollowDate().replaceAll("T", " "));
        FollowNextFollowTime.setContentText(entity.getNextFollowTime() + "");
        FollowRemark.setContentText(entity.getRemark());
        FollowFollowPerson.setContentText(DataApplication.getInstance().getUserInfoEntity().getDisplayName()).setItemEnabled(false);
    }

    protected FollowEntity getFollowEntity() {
        FollowEntity entity = new FollowEntity();
        entity.setFollowType(IStringUtils.toInt(FollowTypeCode));
        entity.setFollowDate(FollowFollowDate.getContentText());
        entity.setInterestedStatus(IStringUtils.toInt(InterestedStatusCode));
        entity.setLoanType(IStringUtils.toInt(LoanTypeCode));
        entity.setAmount(IStringUtils.toInt(FollowAmount.getContentText()));
        entity.setNextFollowType(IStringUtils.toInt(NextFollowTypeCode));
        entity.setNextFollowDate(FollowNextFollowDate.getContentText());
        entity.setNextFollowTime(IStringUtils.toInt(FollowNextFollowTime.getContentText()));
        entity.setRemark(FollowRemark.getContentText());
        FollowFollowPerson.setContentText(DataApplication.getInstance().getUserInfoEntity().getDisplayName());
        return entity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Subscribe
    public void onEventBusSubmit(EventBusValues values) {
        if (values.getWhat() == 0x1007) {
//            DialogFactory.showMsgDialog(getContext(), "提交", "确定提交当前记录?", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
                    onSubmit();
//                }
//            });
        }
    }

    public void onSubmit() {

    }
}
