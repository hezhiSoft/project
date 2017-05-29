package com.xiaomai.telemarket.module.cstmr.fragment.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryDialog;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    FormSelectTopTitleView InfoIsSZHukou;
    @BindView(R.id.Info_Sex)
    FormSelectTopTitleView InfoSex;
    @BindView(R.id.Info_MaritalStatus)
    FormSelectTopTitleView InfoMaritalStatus;
    @BindView(R.id.Info_Payroll)
    FormWriteTopTitleView InfoPayroll;
    @BindView(R.id.Info_AccumulationFundAccount)
    FormWriteTopTitleView InfoAccumulationFundAccount;
    @BindView(R.id.Info_SocialSecurityAccount)
    FormWriteTopTitleView InfoSocialSecurityAccount;
    @BindView(R.id.Info_Remark)
    FormWriteTopTitleView InfoRemark;

    protected CusrometListEntity entity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_cusromet_info,null);
        ButterKnife.bind(this,rootView);
        setListener();
        return rootView;
    }

    private void setListener(){
        InfoIsSZHukou.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DictionaryHelper.showSelectDialog(getContext(),InfoIsSZHukou.getTextView(),InfoIsSZHukou.getContentText());
            }
        });
        InfoSex.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list=DictionaryHelper.getSex();
                DictionaryDialog dictionaryDialog=new DictionaryDialog();
                dictionaryDialog.setSelectContent(InfoSex.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity!=null){
                                    InfoSex.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(),getClass().getSimpleName());
            }
        });
        InfoMaritalStatus.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                List<DictionaryEntity> list=DictionaryHelper.getMaritalStatus();
                DictionaryDialog dictionaryDialog=new DictionaryDialog();
                dictionaryDialog.setSelectContent(InfoMaritalStatus.getContentText())
                        .setItemData(list)
                        .setClickListener(new DictionaryDialog.OnClickItemListener() {
                            @Override
                            public void onClickItem(DictionaryEntity entity) {
                                if (entity!=null){
                                    InfoMaritalStatus.setContentText(entity.getName());
                                }
                            }
                        }).show(getFragmentManager(),getClass().getSimpleName());
            }
        });

    }

    protected void initUI(CusrometListEntity entity){
        if (entity==null){
            return;
        }
        InfoCustomerName.setContentText(entity.getCustomerName()).setItemEnabled(false);
        InfoCustomerTel.setContentText(entity.getCustomerTel()).setItemEnabled(false);
        InfoIsSZHukou.setContentText(entity.getIsSZHukou()==0?"否":"是");
        InfoSex.setContentText(DictionaryHelper.ParseSex(entity.getSex()+""));
        InfoMaritalStatus.setContentText(DictionaryHelper.ParseMaritalStatus(entity.getMaritalStatus()+""));
        InfoPayroll.setContentText("无");
        InfoAccumulationFundAccount.setContentText(entity.getAccountWater()+"");
        InfoSocialSecurityAccount.setContentText(entity.getSocialSecurityAccount()+"");
        InfoRemark.setContentText(entity.getRemark());

           }


}
