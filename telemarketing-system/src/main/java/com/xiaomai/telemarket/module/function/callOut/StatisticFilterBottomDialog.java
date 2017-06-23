package com.xiaomai.telemarket.module.function.callOut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jinggan.library.ui.date.DatePickDialog;
import com.jinggan.library.ui.date.OnSureLisener;
import com.jinggan.library.ui.date.bean.DateBean;
import com.jinggan.library.ui.date.bean.DateType;
import com.jinggan.library.utils.IDateTimeUtils;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.common.CommPopupWindow;
import com.xiaomai.telemarket.module.function.data.StatisticByUserParam;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yangdu on 12/06/2017.
 */

public class StatisticFilterBottomDialog {

    private final Context mContext;
    @BindView(R.id.edt_statistic_username)
    EditText edtStatisticUsername;
    @BindView(R.id.tv_statistic_date_start)
    TextView tvStatisticDateStart;
    @BindView(R.id.tv_statistic_date_end)
    TextView tvStatisticDateEnd;
    @BindView(R.id.edt_statistic_out_start)
    EditText edtStatisticOutStart;
    @BindView(R.id.edt_statistic_out_end)
    EditText edtStatisticOutEnd;
    @BindView(R.id.edt_statistic_connect_start)
    EditText edtStatisticConnectStart;
    @BindView(R.id.edt_statistic_connect_end)
    EditText edtStatisticConnectEnd;
    @BindView(R.id.edt_statistic_reservation_start)
    EditText edtStatisticReservationStart;
    @BindView(R.id.edt_statistic_reservation_end)
    EditText edtStatisticReservationEnd;
    @BindView(R.id.edt_statistic_duration_start)
    EditText edtStatisticDurationStart;
    @BindView(R.id.edt_statistic_duration_end)
    EditText edtStatisticDurationEnd;
    Unbinder unbinder;

    private CommPopupWindow commPopupWindow;

    private StatisticByUserParam userParamntity;

    private OnConfirmClickListener onConfirmClickListener;

    public StatisticFilterBottomDialog(Context context,StatisticByUserParam userParamntity) {
        mContext = context;
        this.userParamntity = userParamntity;
        View view=initView(context);
        commPopupWindow = new CommPopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private View initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.statistic_filter_user_dialog, null);
        unbinder = ButterKnife.bind(this, view);
        resetParams(userParamntity);
        return view;
    }

    // TODO: 12/06/2017 设置bottom sheet dialog 高度
//    private void setMaxHeight(View contentView) {
//        Dialog dialog = getDialog();
//        dialog.setContentView(contentView);
//        View view = dialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
//        BottomSheetBehavior.from(view).setState(BottomSheetBehavior.STATE_EXPANDED);
////        BottomSheetBehavior.from(view).setPeekHeight(ScreenUtils.getScreenHeight(getActivity()));
//    }

    @OnClick({R.id.DialogFilters_close_ImageView, R.id.tv_statistic_date_start, R.id.tv_statistic_date_end, R.id.DialogTaskFilters_replace, R.id.DialogTaskFilters_confirm})
    public void onViewClicked(View view) {
        DatePickDialog dialog = null;
        switch (view.getId()) {
            case R.id.DialogFilters_close_ImageView:
                dismiss();
                break;
            case R.id.tv_statistic_date_start:
                //开始日期
                dialog = new DatePickDialog(mContext);
                dialog.setTitle("选择开始日期");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        tvStatisticDateStart.setText(date.getYear() + "-" + date.getMoth() + "-" + date.getDay());
                    }
                });
                dialog.show();
                break;
            case R.id.tv_statistic_date_end:
                //结束日期
                dialog = new DatePickDialog(mContext);
                dialog.setTitle("选择结束日期");
                dialog.setType(DateType.TYPE_YMD);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        tvStatisticDateEnd.setText(date.getYear() + "-" + date.getMoth() + "-" + date.getDay());
                    }
                });
                dialog.show();
                break;
            case R.id.DialogTaskFilters_replace:
                //重置
                resetParams(null);
                break;
            case R.id.DialogTaskFilters_confirm:
                if (onConfirmClickListener != null) {
                    getParams();
                    onConfirmClickListener.onConfirm(userParamntity);
                }
                dismiss();
                break;
        }
    }

    private void resetParams(StatisticByUserParam paramEntity) {
        if (paramEntity != null) {
            edtStatisticUsername.setText(paramEntity.getName());
            tvStatisticDateStart.setText(paramEntity.getFromDate()+"");
            tvStatisticDateEnd.setText(paramEntity.getToDate()+"");
            edtStatisticOutStart.setText(paramEntity.getFromCall()+"");
            edtStatisticOutEnd.setText(paramEntity.getToCall()+"");
            edtStatisticConnectStart.setText(paramEntity.getFromConnect()+"");
            edtStatisticConnectEnd.setText(paramEntity.getToConnect()+"");
            edtStatisticReservationStart.setText(paramEntity.getFromAppoint()+"");
            edtStatisticReservationEnd.setText(paramEntity.getToAppoint()+"");
            edtStatisticDurationStart.setText(paramEntity.getFromDuration()+"");
            edtStatisticDurationEnd.setText(paramEntity.getToDuration()+"");
        } else {
            edtStatisticUsername.setText("");
            tvStatisticDateStart.setText(IDateTimeUtils.getCurrentDate(DateType.TYPE_YMD.getFormat()));
            tvStatisticDateEnd.setText(IDateTimeUtils.getCurrentDate(DateType.TYPE_YMD.getFormat()));
            edtStatisticOutStart.setText("");
            edtStatisticOutEnd.setText("");
            edtStatisticConnectStart.setText("");
            edtStatisticConnectEnd.setText("");
            edtStatisticReservationStart.setText("");
            edtStatisticReservationEnd.setText("");
            edtStatisticDurationStart.setText("");
            edtStatisticDurationEnd.setText("");
        }
        this.userParamntity = paramEntity;
    }

    private void getParams() {
        if (userParamntity == null) {
            userParamntity = new StatisticByUserParam();
        }
        userParamntity.setName(edtStatisticUsername.getText().toString());
        userParamntity.setFromDate(tvStatisticDateStart.getText().toString());
        userParamntity.setToDate(tvStatisticDateEnd.getText().toString());
        if (edtStatisticOutStart.getText().length() > 0) {
            userParamntity.setFromCall(Integer.valueOf(edtStatisticOutStart.getText().toString()));
        }
        if (edtStatisticOutEnd.getText().length() > 0) {
            userParamntity.setToCall(Integer.valueOf(edtStatisticOutEnd.getText().toString()));
        }

        if (edtStatisticConnectStart.getText().length() > 0) {
            userParamntity.setFromConnect(Integer.valueOf(edtStatisticConnectStart.getText().toString()));
        }
        if (edtStatisticConnectEnd.getText().length() > 0) {
            userParamntity.setToConnect(Integer.valueOf(edtStatisticConnectEnd.getText().toString()));
        }

        if (edtStatisticReservationStart.getText().length() > 0) {
            userParamntity.setFromAppoint(Integer.valueOf(edtStatisticReservationStart.getText().toString()));
        }
        if (edtStatisticReservationEnd.getText().length() > 0) {
            userParamntity.setToAppoint(Integer.valueOf(edtStatisticReservationEnd.getText().toString()));
        }

        if (edtStatisticDurationStart.getText().length() > 0) {
            userParamntity.setFromDuration(Integer.valueOf(edtStatisticDurationStart.getText().toString()));
        }
        if (edtStatisticDurationEnd.getText().length() > 0) {
            userParamntity.setToDuration(Integer.valueOf(edtStatisticDurationEnd.getText().toString()));
        }
    }

    public void showAtAnchor(View anchor) {
        if (commPopupWindow!=null&&anchor!=null) {
            commPopupWindow.showAtButton(anchor);
        }
    }

    public void dismiss() {
        if (commPopupWindow!=null&&commPopupWindow.isShowing()) {
            commPopupWindow.dismiss();
        }
    }

    public StatisticFilterBottomDialog setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
        return this;
    }

    public interface OnConfirmClickListener {
        void onConfirm(StatisticByUserParam userParamntity);
    }
}
