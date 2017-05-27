package com.xiaomai.telemarket.module.cstmr.fragment.follow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryDialog;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_follow, null);
        unbinder = ButterKnife.bind(this, rootView);
        setListener();
        return rootView;
    }

    private void setListener(){
        FollowFollowType.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list= DictionaryHelper.getFollowType();
                DictionaryDialog dictionaryDialog=new DictionaryDialog();
                dictionaryDialog.setSelectContent(FollowFollowType.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity!=null){
                                    FollowFollowType.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(),getClass().getSimpleName());
            }
        });
        FollowFollowDate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DictionaryHelper.showSelectDialog(getContext(),FollowFollowDate.getTextView(),FollowFollowDate.getContentText());
            }
        });
        FollowInterestedStatus.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list=DictionaryHelper.getInterestedStatus();
                DictionaryDialog dictionaryDialog=new DictionaryDialog();
                dictionaryDialog.setSelectContent(FollowInterestedStatus.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity!=null){
                                    FollowInterestedStatus.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(),getClass().getSimpleName());
            }
        });
        FollowLoanType.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list=DictionaryHelper.getLoanType();
                DictionaryDialog dictionaryDialog=new DictionaryDialog();
                dictionaryDialog.setSelectContent(FollowLoanType.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity!=null){
                                    FollowLoanType.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(),getClass().getSimpleName());
            }
        });
        FollowNextFollowType.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list= DictionaryHelper.getFollowType();
                DictionaryDialog dictionaryDialog=new DictionaryDialog();
                dictionaryDialog.setSelectContent(FollowNextFollowType.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity!=null){
                                    FollowNextFollowType.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(),getClass().getSimpleName());
            }
        });
        FollowNextFollowDate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DictionaryHelper.showSelectDialog(getContext(),FollowNextFollowDate.getTextView(),FollowNextFollowDate.getContentText());
            }
        });
    }

    protected void initUI(FollowEntity entity){
        if (entity==null){
            return;
        }
        FollowFollowType.setContentText(DictionaryHelper.ParseFollowType(entity.getFollowType()+""));
        FollowFollowDate.setContentText(entity.getFollowDate().replaceAll("T"," "));
        FollowInterestedStatus.setContentText(DictionaryHelper.ParseInterestedStatus(entity.getInterestedStatus()+""));
        FollowLoanType.setContentText(DictionaryHelper.ParseLoanType(entity.getLoanType()+""));
        FollowAmount.setContentText(entity.getAmount()+"");
        FollowNextFollowType.setContentText(DictionaryHelper.ParseFollowType(entity.getNextFollowType()+""));
        FollowNextFollowDate.setContentText(entity.getNextFollowDate().replaceAll("T"," "));
        FollowNextFollowTime.setContentText(entity.getNextFollowTime()+"");
        FollowRemark.setContentText(entity.getRemark());
        FollowFollowPerson.setContentText(DataApplication.getInstance().getUserInfoEntity().getDisplayName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
