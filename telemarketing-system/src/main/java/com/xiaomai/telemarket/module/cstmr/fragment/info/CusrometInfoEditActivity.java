package com.xiaomai.telemarket.module.cstmr.fragment.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryDialog;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 11:52
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometInfoEditActivity extends XiaoMaiBaseActivity {

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

    private CusrometListEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusromet_info);
        ButterKnife.bind(this);
        setToolbarTitle("编辑基本信息");
        setToolbarRightText("保存");
        entity=(CusrometListEntity)getIntent().getSerializableExtra("entity");
        initUI(entity);
    }

    private void initUI(CusrometListEntity entity){
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

        InfoIsSZHukou.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DictionaryHelper.showSelectDialog(CusrometInfoEditActivity.this,InfoIsSZHukou.getTextView(),InfoIsSZHukou.getContentText());
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
                        }).show(getSupportFragmentManager(),getClass().getSimpleName());
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
                        }).show(getSupportFragmentManager(),getClass().getSimpleName());
            }
        });
    }



    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
    }
}
