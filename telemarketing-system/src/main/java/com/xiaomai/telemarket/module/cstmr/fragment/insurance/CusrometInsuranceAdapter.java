package com.xiaomai.telemarket.module.cstmr.fragment.insurance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.InsuranceEntity;
import com.xiaomai.telemarket.module.cstmr.data.PropertyEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 9:32
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometInsuranceAdapter extends BaseRecyclerViewAdapter<InsuranceEntity> {

    private OnClickItemLisenter listenter;

    public CusrometInsuranceAdapter(Context context){
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_cusromet_edtails_layout, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHodler = (ViewHolder) holder;
        viewHodler.DetailsTileTextView.setText("保单明细 "+(position+1));
        viewHodler.DetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHodler.DetailsContentLayout.getVisibility() == View.VISIBLE) {
                    viewHodler.ExpandImageView.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    viewHodler.DetailsContentLayout.setVisibility(View.GONE);
                    viewHodler.lineView.setVisibility(View.VISIBLE);
                    if (listenter!=null){
                        listenter.onSeleceItemPosition(null);
                    }
                } else {
                    viewHodler.ExpandImageView.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    viewHodler.DetailsContentLayout.setVisibility(View.VISIBLE);
                    viewHodler.lineView.setVisibility(View.GONE);
                    if (listenter!=null){
                        listenter.onSeleceItemPosition(mLists.get(position));
                    }
                }
            }
        });
        final View infoView = inflater.inflate(R.layout.cusromet_insurance_layout, null);
        setDetailsData(infoView, mLists.get(position));
        viewHodler.DetailsContentLayout.addView(infoView);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.icDetails)
        ImageView icDetails;
        @BindView(R.id.Details_tile_TextView)
        TextView DetailsTileTextView;
        @BindView(R.id.Details_layout)
        RelativeLayout DetailsLayout;
        @BindView(R.id.Details_content_layout)
        RelativeLayout DetailsContentLayout;
        @BindView(R.id.Details_line)
        View lineView;
        @BindView(R.id.Details_expand_iamgeView)
        ImageView ExpandImageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void setDetailsData(View view,InsuranceEntity entity){
        if (entity==null){
            return;
        }
        FormSelectTopTitleView InsuranceCompany=ButterKnife.findById(view,R.id.Insurance_InsuranceCompany);
        FormWriteTopTitleView InsuredAmount=ButterKnife.findById(view,R.id.Insurance_InsuredAmount);
        FormSelectTopTitleView PaymentMethods=ButterKnife.findById(view,R.id.Insurance_PaymentMethods);
        FormSelectTopTitleView BuyDate=ButterKnife.findById(view,R.id.Insurance_BuyDate);
        FormSelectTopTitleView DelayDate=ButterKnife.findById(view,R.id.Insurance_DelayDate);
        FormWriteTopTitleView DelayDays=ButterKnife.findById(view,R.id.Insurance_DelayDays);
        FormSelectTopTitleView FuXiaoDate=ButterKnife.findById(view,R.id.Insurance_FuXiaoDate);
        FormWriteTopTitleView Remark=ButterKnife.findById(view,R.id.Insurance_Remark);
        /*保险公司*/
        InsuranceCompany.setContentText(DictionaryHelper.ParseINSURANCECOMPANY(entity.getInsuranceCompany())).setArrowDropVisibility(View.GONE);
        /*保费金额*/
        InsuredAmount.setContentText(entity.getInsuredAmount()+"").setItemEnabled(false);
        /*缴费方式*/
        PaymentMethods.setContentText(DictionaryHelper.ParsePayCostType(entity.getPaymentMethods()+"")).setArrowDropVisibility(View.GONE);
        /*购买日期*/
        BuyDate.setContentText(entity.getBuyDate().replaceAll("T"," ")).setArrowDropVisibility(View.GONE);
        /*延期日期*/
        DelayDate.setContentText(entity.getBuyDate().replace("T"," ")).setArrowDropVisibility(View.GONE);
        /*延期天数 */
        DelayDays.setContentText(entity.getDelayDays()+"").setItemEnabled(false);
        /*复效日期*/
        FuXiaoDate.setContentText(entity.getFuXiaoDate().replaceAll("T"," ")).setArrowDropVisibility(View.GONE);
        /*备注*/
        Remark.setContentText(entity.getRemark()).setItemEnabled(false);
    }
    public void setListenter(OnClickItemLisenter listenter) {
        this.listenter = listenter;
    }

    public interface OnClickItemLisenter{
        void onSeleceItemPosition(InsuranceEntity entity);
    }
}
