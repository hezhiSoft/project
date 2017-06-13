package com.xiaomai.telemarket.module.cstmr.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.jinggan.library.base.ArrayListAdapter;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.common.CommPopupWindow;
import com.xiaomai.telemarket.module.cstmr.FiltersButtomDialog;
import com.xiaomai.telemarket.module.cstmr.data.FiltersEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/13$ 下午7:42$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CusrometManagementFilterManager implements RemetoRepoCallback<List<FiltersEntity>> {

    private Activity context;

    GridView DialogFiltersGridView;
    private EditText NameEditText,PhoneEditText,RemarkEditText;

    private FiltersAdater adater;

    private List<FiltersEntity> selectLists;
    private List<FiltersEntity> lists = new ArrayList<>();

    private CommPopupWindow commPopupWindow;
    private String name,phone,remark;

    public CusrometManagementFilterManager(View view,Activity context){
        this.context=context;
        initView(view);
    }

    private void initView(View showView){
        View rootView = LayoutInflater.from(context).inflate(R.layout.filter_dialog, null);
        DialogFiltersGridView= ButterKnife.findById(rootView,R.id.DialogFilters_GridView);
        NameEditText=ButterKnife.findById(rootView,R.id.filter_name);
        PhoneEditText=ButterKnife.findById(rootView,R.id.filter_phone);
        RemarkEditText=ButterKnife.findById(rootView,R.id.filter_remark);

        rootView.findViewById(R.id.DialogFilters_close_ImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commPopupWindow!=null){
                    commPopupWindow.dismiss();
                }
            }
        });

        rootView.findViewById(R.id.DialogTaskFilters_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickItemListener != null) {
                    clickItemListener.onClickItem(lists, NameEditText.getText().toString(),PhoneEditText.getText().toString(),RemarkEditText.getText().toString());
                }
                if (commPopupWindow!=null){
                    commPopupWindow.dismiss();
                }
            }
        });

        rootView.findViewById(R.id.DialogTaskFilters_replace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickItemListener != null) {
                    clickItemListener.onReplance();
                }
                if (commPopupWindow!=null){
                    commPopupWindow.dismiss();
                }
            }
        });

        adater=new FiltersAdater(context);
        DialogFiltersGridView.setAdapter(adater);

        commPopupWindow=new CommPopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        commPopupWindow.showAtButton(showView);

        CusrometRemoteRepo.getInstance().getFilters(this);
    }

    @Override
    public void onSuccess(List<FiltersEntity> data) {
        adater.appendToList(data);
    }

    @Override
    public void onFailure(int code, String msg) {
        ToastUtil.showToast(context, msg);
    }

    @Override
    public void onThrowable(Throwable t) {
        ToastUtil.showToast(context, "获取失败");
    }

    @Override
    public void onUnauthorized() {
        ToastUtil.showToast(context, "获取失败");
    }

    @Override
    public void onFinish() {
    }

    class FiltersAdater extends ArrayListAdapter<FiltersEntity> {

        public FiltersAdater(Activity activity) {
            super(activity);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_dic, null);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.dialog_CheckBox);
            checkBox.setText(mList.get(position).getName());
            if (selectLists != null && selectLists.size() > 0) {
                for (FiltersEntity entity : selectLists) {
                    if (entity.getName().equals(mList.get(position).getName())) {
                        checkBox.setChecked(true);
                        if (!lists.contains(entity))
                            lists.add(entity);
                    }
                }
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        lists.add(mList.get(position));
                    } else {
                        if (selectLists != null && selectLists.size() > 0) {
                            for (FiltersEntity entity : selectLists) {
                                if (entity.getName().equals(mList.get(position).getName())) {
                                    lists.remove(entity);
                                }
                            }
                        }
                    }
                }
            });
            return convertView;
        }
    }

    private OnClickItemListener clickItemListener;

    public CusrometManagementFilterManager setListener(OnClickItemListener listener) {
        this.clickItemListener = listener;
        return this;
    }

    public CusrometManagementFilterManager setSelectContent(List<FiltersEntity> entities) {
        if (entities == null) {
            this.selectLists = new ArrayList<>();
        } else
            this.selectLists = entities;
        return this;
    }

    public interface OnClickItemListener {
        void onClickItem(List<FiltersEntity> entity,String name,String phone,String remark);

        void onReplance();
    }
}
