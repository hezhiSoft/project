package com.xiaomai.telemarket.module.cstmr;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;

import com.jinggan.library.base.ArrayListAdapter;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
import com.xiaomai.telemarket.module.cstmr.data.FiltersEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/27 19:14
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FiltersButtomDialog extends BottomSheetDialogFragment implements RemetoRepoCallback<List<FiltersEntity>> {

    @BindView(R.id.DialogFilters_GridView)
    GridView DialogFiltersGridView;
    Unbinder unbinder;

    private FiltersAdater adater;

    private List<FiltersEntity> lists = new ArrayList<>();
    private List<FiltersEntity> selectLists = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adater = new FiltersAdater(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.filter_dialog, null);
        unbinder = ButterKnife.bind(this, rootView);
        DialogFiltersGridView.setAdapter(adater);
        CusrometRemoteRepo.getInstance().getFilters(this);
        return rootView;
    }

    @Override
    public void onSuccess(List<FiltersEntity> data) {
        adater.appendToList(data);
    }

    @Override
    public void onFailure(int code, String msg) {
        ToastUtil.showToast(getContext(), msg);
        dismiss();
    }

    @Override
    public void onThrowable(Throwable t) {
        ToastUtil.showToast(getContext(), "获取失败");
        dismiss();
    }

    @Override
    public void onUnauthorized() {
        ToastUtil.showToast(getContext(), "获取失败");
        dismiss();
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.DialogTaskFilters_replace,R.id.DialogFilters_close_ImageView, R.id.DialogTaskFilters_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.DialogFilters_close_ImageView:
                dismiss();
                break;
            case R.id.DialogTaskFilters_confirm:
                if (clickItemListener!=null){
                    clickItemListener.onClickItem(lists);
                }
                dismiss();
                break;
            case R.id.DialogTaskFilters_replace:
                if (clickItemListener!=null){
                    clickItemListener.onReplance();
                }
                dismiss();
                break;
        }
    }

    class FiltersAdater extends ArrayListAdapter<FiltersEntity> {

        public FiltersAdater(Activity activity) {
            super(activity);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_dic, null);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.dialog_CheckBox);
            checkBox.setText(mList.get(position).getName());
            if (selectLists != null && selectLists.size() > 0) {
                for (FiltersEntity entity : selectLists) {
                    if (entity.getName().equals(mList.get(position).getName())) {
                        checkBox.setChecked(true);
                        lists.add(mList.get(position));
                        break;
                    }
                }
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            lists.add(mList.get(position));
                        } else {
                            lists.remove(mList.get(position));
                        }
                }
            });
            return convertView;
        }
    }

    private OnClickItemListener clickItemListener;

    public FiltersButtomDialog setListener(OnClickItemListener listener) {
        this.clickItemListener = listener;
        return this;
    }

    public FiltersButtomDialog setSelectContent(List<FiltersEntity> entities) {
        this.selectLists = entities;
        return this;
    }

    public interface OnClickItemListener {
        void onClickItem(List<FiltersEntity> entity);
        void onReplance();
    }
}
