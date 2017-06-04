package com.xiaomai.telemarket.module.cstmr.fragment.property;

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
import com.xiaomai.telemarket.module.cstmr.data.PropertyEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;
import com.xiaomai.telemarket.view.widget.SelecteConditionTileView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 9:32
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometPropertyAdapter extends BaseRecyclerViewAdapter<PropertyEntity> {

    private OnClickItemLisenter listenter;

    public CusrometPropertyAdapter(Context context){
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_cusromet_edtails_layout, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHodler = (ViewHolder) holder;
        viewHodler.DetailsTileTextView.setText("房产明细 "+(position+1));
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
        if (position==0){
            viewHodler.ExpandImageView.setImageResource(R.drawable.ic_expand_less_black_24dp);
            viewHodler.DetailsContentLayout.setVisibility(View.VISIBLE);
            viewHodler.lineView.setVisibility(View.GONE);
            if (listenter!=null){
                listenter.onSeleceItemPosition(mLists.get(position));
            }
        }

        final View infoView = inflater.inflate(R.layout.cusromet_property_layout, null);
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

    public void setListenter(OnClickItemLisenter listenter) {
        this.listenter = listenter;
    }

    private void  setDetailsData(View view, PropertyEntity entity){
        if (entity==null){
            return;
        }
        FormSelectTopTitleView PropertyLandUse=ButterKnife.findById(view,R.id.Property_LandUse);
        FormWriteTopTitleView PropertyAreaM=ButterKnife.findById(view,R.id.Property_AreaM);
        FormSelectTopTitleView PropertyArea=ButterKnife.findById(view,R.id.Property_Area);
        FormSelectTopTitleView PropertyCompletionDate=ButterKnife.findById(view,R.id.Property_CompletionDate);
        FormWriteTopTitleView PropertyPropertyRightsYear=ButterKnife.findById(view,R.id.Property_PropertyRightsYear);
        FormWriteTopTitleView PropertyRegistrationPrice=ButterKnife.findById(view,R.id.Property_RegistrationPrice);
        FormWriteTopTitleView PropertyVillageName=ButterKnife.findById(view,R.id.Property_VillageName);
        FormWriteTopTitleView PropertyDetailedAddress=ButterKnife.findById(view,R.id.Property_DetailedAddress);
        FormWriteTopTitleView PropertyRemark=ButterKnife.findById(view,R.id.Property_Remark);
        SelecteConditionTileView PropertyIsMortgage=ButterKnife.findById(view,R.id.Property_IsMortgage);
        FormSelectTopTitleView PropertyMortgageBank=ButterKnife.findById(view,R.id.Property_MortgageBank);
        FormWriteTopTitleView PropertyMonthlyPaymentLoan=ButterKnife.findById(view,R.id.Property_MonthlyPaymentLoan);
        FormWriteTopTitleView PropertyMortgageTimeLimit=ButterKnife.findById(view,R.id.Property_MortgageTimeLimit);
        FormWriteTopTitleView PropertyRemainingMortgage=ButterKnife.findById(view,R.id.Property_RemainingMortgage);

        /*土地用途*/
        PropertyLandUse.setContentText(DictionaryHelper.ParseLandUse(entity.getLandUse()+"")).setArrowDropVisibility(View.GONE);
        /*面积*/
        PropertyAreaM.setContentText(entity.getAreaM()+"").setItemEnabled(false);
        /*区域*/
        PropertyArea.setContentText(DictionaryHelper.ParseSZAREA(entity.getArea())).setArrowDropVisibility(View.GONE);
        /*竣工日期*/
        PropertyCompletionDate.setContentText(entity.getCompletionDate().replaceAll("T"," ")).setArrowDropVisibility(View.GONE);
        /*产权年限*/
        PropertyPropertyRightsYear.setContentText(entity.getPropertyRightsYear()+"").setItemEnabled(false);
        /*登记价格*/
        PropertyRegistrationPrice.setContentText(entity.getRegistrationPrice()+"").setItemEnabled(false);
        /*小区名称*/
        PropertyVillageName.setContentText(entity.getVillageName()).setItemEnabled(false);
        /*详细地址*/
        PropertyDetailedAddress.setContentText(entity.getDetailedAddress()).setItemEnabled(false);
        /*备注*/
        PropertyRemark.setContentText(entity.getRemark()).setItemEnabled(false);
        /*是否按揭*/
        PropertyIsMortgage.setStatus(entity.getIsMortgage()).setRadioGropEnabled(false);
        /*按揭银行*/
        PropertyMortgageBank.setContentText(DictionaryHelper.ParseBank(entity.getMortgageBank())).setArrowDropVisibility(View.GONE);
        /*每月还款*/
        PropertyMonthlyPaymentLoan.setContentText(entity.getMonthlyPaymentLoan()+"").setItemEnabled(false);
        /*按揭期数*/
        PropertyMortgageTimeLimit.setContentText(entity.getMortgageTimeLimit()+"").setItemEnabled(false);
        /*剩余按揭*/
        PropertyRemainingMortgage.setContentText(entity.getRemainingMortgage()+"").setItemEnabled(false);

    }
    public interface OnClickItemLisenter{
        void onSeleceItemPosition(PropertyEntity entity);
    }
}
