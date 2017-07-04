package com.easydear.user.module.order;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.R;
import com.easydear.user.module.order.data.OrderDetailsEntity;
import com.easydear.user.module.order.data.source.OrderRepo;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.dialog.CommPopupWindow;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;
import com.jinggan.library.utils.ISystemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-07-01
 * Time: 11:17
 * Version:1.0
 */

public class OrderDetailsActivity extends BaseActivity implements RemetoRepoCallbackV2<OrderDetailsEntity> {

    @BindView(R.id.OrderDetails_BusinessName)
    TextView OrderDetailsBusinessName;
    @BindView(R.id.OrderDetails_Business_logo)
    ImageView OrderDetailsBusinessLogo;
    @BindView(R.id.OrderDetails_CardName)
    TextView OrderDetailsCardName;
    @BindView(R.id.OrderDetails_CardSizeV1)
    TextView OrderDetailsCardSizeV1;
    @BindView(R.id.OrderDetails_BuyAmount_total)
    TextView OrderDetailsBuyAmountTotal;
    @BindView(R.id.OrderDetails_Status)
    TextView OrderDetailsStatus;
    @BindView(R.id.OrderDetails_BuyAmount)
    TextView OrderDetailsBuyAmount;
    @BindView(R.id.OrderDetails_OrderNo)
    TextView OrderDetailsOrderNo;
    @BindView(R.id.OrderDetails_BuyTime)
    TextView OrderDetailsBuyTime;

    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        setToolbarTitle("订单详情");

        OrderRepo.getInstance().queryOrderDetails(getIntent().getStringExtra("OrderNo"), this);
    }

    @Override
    public void onReqStart() {
        dialog = DialogFactory.createLoadingDialog(this, "查询...");
    }

    @Override
    public void onSuccess(OrderDetailsEntity data) {
        initUI(data);
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(msg);
        finish();
    }

    @Override
    public void onFinish() {
        DialogFactory.dimissDialog(dialog);
    }

    private void initUI(OrderDetailsEntity entity) {
        if (entity == null) {
            return;
        }

        /*商家Logo*/
        Glide.with(this).load(BuildConfig.DOMAIN + entity.getLogo())
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.default_head_img)
                .error(R.mipmap.default_head_img)
                .into(new RoundedBitmapImageViewTarget(this, OrderDetailsBusinessLogo));

        OrderDetailsBusinessName.setText(entity.getBusinessName());

        OrderDetailsCardName.setText(entity.getCardName());

        OrderDetailsCardSizeV1.setText("数量：" + entity.getCardSize() + "张");

        OrderDetailsBuyAmountTotal.setText("合计：￥" + entity.getBuyAmount());

        OrderDetailsStatus.setText(entity.getStatus() + " (" + entity.getCardSize() + "张)");

        OrderDetailsBuyAmount.setText("￥ " + entity.getBuyAmount());

        OrderDetailsOrderNo.setText("订单号：" + entity.getOrderNo());

        OrderDetailsBuyTime.setText("下单时间：" + entity.getBuyTime());
    }


    @OnClick({R.id.OrderDetails_Doubt,R.id.OrderDetails_Card_Layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.OrderDetails_Doubt:
                showConstomerDialog(this, view);
                break;
            case R.id.OrderDetails_Card_Layout:
                break;
        }
    }

    private CommPopupWindow commPopupWindow;

    private void showConstomerDialog(final Context context, View view) {
        View businessView = LayoutInflater.from(context).inflate(R.layout.dialog_tontact_layout, null);
        final TextView tell1 = (TextView) businessView.findViewById(R.id.dialog_tontact_tel1);
        final TextView tell2 = (TextView) businessView.findViewById(R.id.dialog_tontact_tel2);
        businessView.findViewById(R.id.dialog_tontact_calcel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commPopupWindow.dismiss();
            }
        });
        tell1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ISystemUtil.makeCall(context, tell1.getText().toString(), true);
            }
        });
        tell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ISystemUtil.makeCall(context, tell1.getText().toString(), true);
            }
        });
        commPopupWindow = new CommPopupWindow(businessView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        commPopupWindow.showAtButton(view);
    }
}
