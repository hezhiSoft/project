package com.xiaomai.telemarket.module.cstmr.fragment.car;

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
import com.xiaomai.telemarket.module.cstmr.data.CarEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 19:35
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometCarAdapter extends BaseRecyclerViewAdapter<CarEntity> {
    private OnClickItemLisenter listenter;

    public CusrometCarAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHodler(inflater.inflate(R.layout.item_cusromet_edtails_layout, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        viewHodler.DetailsTileTextView.setText("汽车明细" + (position + 1));
        viewHodler.DetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHodler.DetailsContentLayout.getVisibility() == View.VISIBLE) {
                    viewHodler.ExpandImageView.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    viewHodler.DetailsContentLayout.setVisibility(View.GONE);
                    viewHodler.DetailsLine.setVisibility(View.VISIBLE);
                    if (listenter != null) {
                        listenter.onSeleceItemPosition(null);
                    }
                } else {
                    viewHodler.ExpandImageView.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    viewHodler.DetailsContentLayout.setVisibility(View.VISIBLE);
                    viewHodler.DetailsLine.setVisibility(View.GONE);
                    if (listenter != null) {
                        listenter.onSeleceItemPosition(mLists.get(position));
                    }
                }
            }
        });
        View carView = inflater.inflate(R.layout.cusromet_car_layout, null);
        setDetailsData(carView, mLists.get(position));
        viewHodler.DetailsContentLayout.addView(carView);
    }

    class ViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.icDetails)
        ImageView icDetails;
        @BindView(R.id.Details_tile_TextView)
        TextView DetailsTileTextView;
        @BindView(R.id.Details_expand_iamgeView)
        ImageView ExpandImageView;
        @BindView(R.id.Details_layout)
        RelativeLayout DetailsLayout;
        @BindView(R.id.Details_content_layout)
        RelativeLayout DetailsContentLayout;
        @BindView(R.id.Details_line)
        View DetailsLine;

        public ViewHodler(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void setDetailsData(View view, CarEntity entity) {
        if (entity == null) {
            return;
        }
        FormWriteTopTitleView NakedCarPrice = ButterKnife.findById(view, R.id.Car_NakedCarPrice);
        FormSelectTopTitleView BuyDate = ButterKnife.findById(view, R.id.Car_BuyDate);
        FormWriteTopTitleView Mileage = ButterKnife.findById(view, R.id.Car_Mileage);
        FormWriteTopTitleView Brand = ButterKnife.findById(view, R.id.Car_Barnd);
        FormWriteTopTitleView CarModel = ButterKnife.findById(view, R.id.Car_CarModel);
        FormSelectTopTitleView IsMortgage = ButterKnife.findById(view, R.id.Car_IsMortgage);
        FormSelectTopTitleView IsRegistrationCertificate = ButterKnife.findById(view, R.id.Car_IsRegistrationCertificate);
        FormWriteTopTitleView Remark = ButterKnife.findById(view, R.id.Car_Remark);
        /*裸车价*/
        NakedCarPrice.setContentText(entity.getNakedCarPrice() + "").setItemEnabled(false);
        /*购车日期*/
        BuyDate.setContentText(entity.getBuyDate().replaceAll("T", " ")).setArrowDropVisibility(View.GONE);
        /*行驶里程*/
        Mileage.setContentText(entity.getMileage() + "").setItemEnabled(false);
        /*品牌*/
        Brand.setContentText(entity.getBrand()).setItemEnabled(false);
        /*车型*/
        CarModel.setContentText(entity.getCarModel()).setItemEnabled(false);
        /*是否按揭*/
        IsMortgage.setContentText(entity.getIsMortgage() == 0 ? "否" : "是").setArrowDropVisibility(View.GONE);
        /*有登记证*/
        IsRegistrationCertificate.setContentText(entity.getIsRegistrationCertificate() == 0 ? "否" : "是").setArrowDropVisibility(View.GONE);
        /*备注*/
        Remark.setContentText(entity.getRemark()).setItemEnabled(false);

    }

    public void setListenter(OnClickItemLisenter listenter) {
        this.listenter = listenter;
    }

    public interface OnClickItemLisenter {
        void onSeleceItemPosition(CarEntity entity);
    }
}
