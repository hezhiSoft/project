package com.xiaomai.telemarket.module.function.callTrend;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.ui.date.DatePickDialog;
import com.jinggan.library.ui.date.OnSureLisener;
import com.jinggan.library.ui.date.bean.DateBean;
import com.jinggan.library.ui.date.bean.DateType;
import com.jinggan.library.utils.IDateTimeUtils;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.function.data.StatisticsByMonthParam;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 外呼趋势筛选
 * @createtime 14/06/2017 3:52 AM
 **/
public class DepStatisticFilterBottomDialog extends BottomSheetDialogFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_statistic_year_start)
    TextView tvStatisticYearStart;

    private StatisticsByMonthParam paramEntity;

    private OnConfirmClickListener onConfirmClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistic_filter_dep_dialog, null);
        unbinder = ButterKnife.bind(this, view);
        resetParams(paramEntity);
        setMaxHeight(view);
        ViewGroup viewParent = (ViewGroup) view.getParent();
        if (viewParent != null) {
            viewParent.removeView(view);
        }
        return view;
    }

    // TODO: 12/06/2017 设置bottom sheet dialog 高度 不管用
    private void setMaxHeight(View contentView) {
        Dialog dialog = getDialog();
        dialog.setContentView(contentView);
        View view = dialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
        BottomSheetBehavior.from(view).setState(BottomSheetBehavior.STATE_EXPANDED);
//        BottomSheetBehavior.from(view).setPeekHeight(ScreenUtils.getScreenHeight(getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.DialogFilters_close_ImageView, R.id.tv_statistic_year_start,R.id.DialogTaskFilters_replace, R.id.DialogTaskFilters_confirm})
    public void onViewClicked(View view) {
        DatePickDialog dialog = null;
        switch (view.getId()) {
            case R.id.DialogFilters_close_ImageView:
                dismiss();
                break;
            case R.id.tv_statistic_year_start:
                //开始日期
                dialog = new DatePickDialog(getContext());
                dialog.setTitle("选择呼叫年份");
                dialog.setType(DateType.TYPE_YY);
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(DateBean date) {
                        tvStatisticYearStart.setText(date.getYear()+"");
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
                    onConfirmClickListener.onConfirm(paramEntity);
                }
                dismiss();
                break;
        }
    }

    public DepStatisticFilterBottomDialog setUserParamntity(StatisticsByMonthParam paramEntity) {
        this.paramEntity = paramEntity;
        return this;
    }

    private void resetParams(StatisticsByMonthParam paramEntity) {
        if (paramEntity != null) {
            tvStatisticYearStart.setText(paramEntity.getYear());
        } else {
            tvStatisticYearStart.setText(IDateTimeUtils.getCurrentDate(DateType.TYPE_YY.getFormat()));
        }
        this.paramEntity = paramEntity;
    }

    private void getParams() {
        if (paramEntity == null) {
            paramEntity = new StatisticsByMonthParam();
        }
        paramEntity.setYear(tvStatisticYearStart.getText().toString());
    }

    public DepStatisticFilterBottomDialog setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
        return this;
    }

    public interface OnConfirmClickListener {
        void onConfirm(StatisticsByMonthParam paramEntity);
    }
}
