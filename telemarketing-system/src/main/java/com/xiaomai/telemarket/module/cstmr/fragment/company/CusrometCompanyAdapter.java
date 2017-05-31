package com.xiaomai.telemarket.module.cstmr.fragment.company;

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
import com.xiaomai.telemarket.module.cstmr.data.CompanyEntity;
import com.xiaomai.telemarket.module.cstmr.data.InsuranceEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 9:32
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometCompanyAdapter extends BaseRecyclerViewAdapter<CompanyEntity> {

    private OnClickItemLisenter listenter;

    public CusrometCompanyAdapter(Context context){
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_cusromet_edtails_layout, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHodler = (ViewHolder) holder;
        viewHodler.DetailsTileTextView.setText("公司明细 "+(position+1));
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
        final View infoView = inflater.inflate(R.layout.cusromet_company_layout, null);
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

    private void setDetailsData(View view,CompanyEntity entity) {
        if (entity == null) {
            return;
        }
        FormWriteTopTitleView CompanyName = ButterKnife.findById(view, R.id.Company_CompanyName);
        FormWriteTopTitleView Industry = ButterKnife.findById(view, R.id.Company_Industry);
        FormSelectTopTitleView RegisterDate=ButterKnife.findById(view,R.id.Company_RegisterDate);
        FormWriteTopTitleView BusinessScope=ButterKnife.findById(view,R.id.Company_BusinessScope);
        FormWriteTopTitleView SharesProportion=ButterKnife.findById(view,R.id.Company_SharesProportion);
        FormWriteTopTitleView AccountWater=ButterKnife.findById(view,R.id.Company_AccountWater);
        FormWriteTopTitleView LocationRental=ButterKnife.findById(view,R.id.Company_LocationRental);
        FormSelectTopTitleView IsRentTransfer=ButterKnife.findById(view,R.id.Company_IsRentTransfer);
        FormWriteTopTitleView AmountDebt=ButterKnife.findById(view,R.id.Company_AmountDebt);
        FormWriteTopTitleView Remark=ButterKnife.findById(view,R.id.Company_Remark);
        /*公司名称*/
        CompanyName.setContentText(entity.getCompanyName()).setItemEnabled(false);
        /*所属行业*/
        Industry.setContentText(entity.getIndustry()).setItemEnabled(false);
        /*注册日期*/
        RegisterDate.setContentText(entity.getRegisterDate().replaceAll("T"," ")).setArrowDropVisibility(View.GONE);
        /*经营范围*/
        BusinessScope.setContentText(entity.getBusinessScope()).setItemEnabled(false);
        /*占股比例*/
        SharesProportion.setContentText(entity.getSharesProportion()+"").setItemEnabled(false);
        /*账户流水*/
        AccountWater.setContentText(entity.getAccountWater()+"").setItemEnabled(false);
        /*租金*/
        LocationRental.setContentText(entity.getLocationRental()+"").setItemEnabled(false);
        /*租金是否转账*/
        IsRentTransfer.setContentText(entity.getIsRentTransfer()==0?"否":"是").setArrowDropVisibility(View.GONE);
        /*负债金额*/
        AmountDebt.setContentText(entity.getAmountDebt()+"").setItemEnabled(false);
        /*备注*/
        Remark.setContentText(entity.getRemark()).setItemEnabled(false);
    }

    public void setListenter(OnClickItemLisenter listenter) {
        this.listenter = listenter;
    }

    public interface OnClickItemLisenter{
        void onSeleceItemPosition(CompanyEntity entity);
    }
}
