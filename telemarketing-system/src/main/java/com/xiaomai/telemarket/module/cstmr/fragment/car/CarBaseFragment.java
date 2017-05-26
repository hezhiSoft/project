package com.xiaomai.telemarket.module.cstmr.fragment.car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.date.DatePickDialog;
import com.jinggan.library.ui.date.OnSureLisener;
import com.jinggan.library.ui.date.bean.DateBean;
import com.jinggan.library.ui.date.bean.DateType;
import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.CarEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 22:10
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CarBaseFragment extends BaseFragment {

    @BindView(R.id.Car_NakedCarPrice)
    FormWriteTopTitleView CarNakedCarPrice;
    @BindView(R.id.Car_BuyDate)
    FormSelectTopTitleView CarBuyDate;
    @BindView(R.id.Car_Mileage)
    FormWriteTopTitleView CarMileage;
    @BindView(R.id.Car_Barnd)
    FormWriteTopTitleView CarBarnd;
    @BindView(R.id.Car_CarModel)
    FormWriteTopTitleView CarCarModel;
    @BindView(R.id.Car_IsMortgage)
    FormSelectTopTitleView CarIsMortgage;
    @BindView(R.id.Car_IsRegistrationCertificate)
    FormSelectTopTitleView CarIsRegistrationCertificate;
    @BindView(R.id.Car_Remark)
    FormWriteTopTitleView CarRemark;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car, null);
        unbinder = ButterKnife.bind(this, rootView);
        setLisener();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setLisener() {
        /*购车日期*/
        CarBuyDate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DatePickDialog dialog = new DatePickDialog(getContext());
                dialog.setTitle("选择日期");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        CarBuyDate.setContentText(date.getYear() + "-" + date.getMoth() + "-" + date.getDay());
                    }
                });
                dialog.show();
            }
        });
        /*是否按揭*/
        CarIsMortgage.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DictionaryHelper.showSelectDialog(getContext(), CarIsMortgage.getTextView(), CarIsMortgage.getContentText());
            }
        });
        /*是否有登记证*/
        CarIsRegistrationCertificate.setArrowDropListener(new FormSelectTopTitleView.onArrowDropClick() {
            @Override
            public void onClick(TextView textView) {
                DictionaryHelper.showSelectDialog(getContext(), CarIsRegistrationCertificate.getTextView(), CarIsRegistrationCertificate.getContentText());
            }
        });
    }

    protected void initUI(CarEntity entity) {
        if (entity == null) {
            return;
        }
        CarNakedCarPrice.setContentText(entity.getNakedCarPrice() + "");
        CarBuyDate.setContentText(entity.getBuyDate().replaceAll("T", " "));
        CarMileage.setContentText(entity.getMileage() + "");
        CarBarnd.setContentText(entity.getBrand());
        CarCarModel.setContentText(entity.getCarModel());
        CarIsMortgage.setContentText(entity.getIsMortgage() == 0 ? "否" : "是");
        CarIsRegistrationCertificate.setContentText(entity.getIsRegistrationCertificate() == 0 ? "否" : "是");
        CarRemark.setContentText(entity.getRemark());
    }
}
