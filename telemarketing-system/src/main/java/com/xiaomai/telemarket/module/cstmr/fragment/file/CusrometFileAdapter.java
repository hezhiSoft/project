package com.xiaomai.telemarket.module.cstmr.fragment.file;

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
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 13:31
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometFileAdapter extends BaseRecyclerViewAdapter<DebtoEntity> {

    private OnClickItemLisenter listenter;

    public CusrometFileAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHodler(inflater.inflate(R.layout.item_cusromet_edtails_layout, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
//        viewHodler.DetailsTileTextView.setText("负债明细 "+(position+1));
        viewHodler.DetailsTileTextView.setVisibility(View.GONE);
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
        final View infoView = inflater.inflate(R.layout.cusromet_debto_layout, null);
        setDetailsData(infoView, mLists.get(position));
        viewHodler.DetailsContentLayout.addView(infoView);
    }

    class ViewHodler extends RecyclerView.ViewHolder {

        @BindView(R.id.icDetails)
        ImageView icDetails;
        @BindView(R.id.Details_tile_TextView)
        TextView DetailsTileTextView;
        @BindView(R.id.Details_layout)
        RelativeLayout DetailsLayout;
        @BindView(R.id.Details_content_layout)
        LinearLayout DetailsContentLayout;
        @BindView(R.id.Details_line)
        View lineView;
        @BindView(R.id.Details_expand_iamgeView)
        ImageView ExpandImageView;

        public ViewHodler(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void setDetailsData(View rootView, DebtoEntity entity) {
        if (entity == null) {
            return;
        }
        FormSelectTopTitleView TypeDept = ButterKnife.findById(rootView, R.id.Debto_TypeDept);
        FormWriteTopTitleView LoanAmount = ButterKnife.findById(rootView, R.id.Debto_LoanAmount);
        FormSelectTopTitleView LoanDate = ButterKnife.findById(rootView, R.id.Debto_LoanDate);
        FormSelectTopTitleView LoanBank = ButterKnife.findById(rootView, R.id.Debto_LoanBank);
        FormWriteTopTitleView MonthlyPayments = ButterKnife.findById(rootView, R.id.Debto_MonthlyPayments);
        FormWriteTopTitleView RemainingLoanAmount = ButterKnife.findById(rootView, R.id.Debto_RemainingLoanAmount);
        FormWriteTopTitleView LoanMonth = ButterKnife.findById(rootView, R.id.Debto_LoanMonth);
        FormSelectTopTitleView RepaymentMode = ButterKnife.findById(rootView, R.id.Debto_RepaymentMode);
        FormWriteTopTitleView DelayDays = ButterKnife.findById(rootView, R.id.Debto_DelayDays);
        FormWriteTopTitleView DelayAccount = ButterKnife.findById(rootView, R.id.Debto_DelayAccount);
        FormWriteTopTitleView DelayNum = ButterKnife.findById(rootView, R.id.Debto_DelayNum);
        FormSelectTopTitleView loanProduct = ButterKnife.findById(rootView, R.id.Debto_loanProduct);
        FormWriteTopTitleView Remark = ButterKnife.findById(rootView, R.id.Debto_Remark);
        /*贷款类型*/
        TypeDept.setContentText(entity.getTypeDept() + "").setArrowDropVisibility(View.GONE);
        /*负债金额*/
        LoanAmount.setContentText(entity.getLoanAmount() + "").setItemEnabled(false);
        /*贷款日期*/
        LoanDate.setContentText(entity.getLoanDate().replaceAll("T", " ")).setArrowDropVisibility(View.GONE);
        /*机构/银行*/
        LoanBank.setContentText(entity.getLoanBank() + "").setArrowDropVisibility(View.GONE);
        /*每月还款*/
        MonthlyPayments.setContentText(entity.getMonthlyPayments() + "").setItemEnabled(false);
        /*剩余还款*/
        RemainingLoanAmount.setContentText(entity.getRemainingLoanAmount() + "").setItemEnabled(false);
        /*贷款期数*/
        LoanMonth.setContentText(entity.getLoanMonth() + "").setItemEnabled(false);
        /*还款方式*/
        RepaymentMode.setContentText(entity.getRepaymentMode() + "").setArrowDropVisibility(View.GONE);
        /*延期月数*/
        DelayDays.setContentText(entity.getDelayDays() + "").setItemEnabled(false);
        /*延期金额*/
        DelayAccount.setContentText(entity.getDelayAccount() + "").setItemEnabled(false);
        /*延期次数*/
        DelayNum.setContentText(entity.getDelayNum() + "").setItemEnabled(false);
        // TODO: 2017/5/20 缺失字段
        /*贷款产品*/
        loanProduct.setContentText("无").setArrowDropVisibility(View.GONE);
        /*备注*/
        Remark.setContentText(entity.getRemark()).setItemEnabled(false);

    }


    public void setListenter(OnClickItemLisenter listenter) {
        this.listenter = listenter;
    }

    public interface OnClickItemLisenter{
        void onSeleceItemPosition(DebtoEntity entity);
    }
}
