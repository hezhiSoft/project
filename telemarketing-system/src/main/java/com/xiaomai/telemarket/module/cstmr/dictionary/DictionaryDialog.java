package com.xiaomai.telemarket.module.cstmr.dictionary;

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
import android.widget.TextView;

import com.jinggan.library.base.ArrayListAdapter;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/26 10:15
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DictionaryDialog extends BottomSheetDialogFragment {

    @BindView(R.id.DialogDic_Title_TextView)
    TextView titleTextView;
    @BindView(R.id.DialogDic_GridView)
    GridView mGridView;

    private String selectContent;
    private String title;
    private OnClickItemListener clickItemListener;

    private DictionaryAdater adater;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (adater==null){
            adater=new DictionaryAdater(getActivity());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.dialog_dictionary,null);
        ButterKnife.bind(this,rootView);
        initGirdView();
        return rootView;
    }

    private void initGirdView(){
        titleTextView.setText(title);
        mGridView.setAdapter(adater);
    }

    @OnClick(R.id.DialogDic_close_ImageView)
    public void onClick(){
        dismiss();
    }

    public DictionaryDialog setTitle(String title){
        this.title=title;
        return this;
    }

    public DictionaryDialog setItemData(List<DictionaryEntity> data){
        if (adater==null){
            adater=new DictionaryAdater(getActivity());
        }
        adater.appendToList(data);
        return this;
    }

    public DictionaryDialog setSelectContent(String content){
        this.selectContent=content;
        return this;
    }

    public DictionaryDialog setClickListener(OnClickItemListener clickListener){
        this.clickItemListener=clickListener;
        return this;
    }



    class DictionaryAdater extends ArrayListAdapter<DictionaryEntity>{

        public DictionaryAdater(Activity activity){
            super(activity);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_dic,null);
            CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.dialog_CheckBox);
            checkBox.setText(mList.get(position).getName());
            if (!TextUtils.isEmpty(selectContent)&&selectContent.equals(mList.get(position).getName())){
                checkBox.setChecked(true);
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (clickItemListener!=null){
                        clickItemListener.onClickItem(mList.get(position));
                        dismiss();
                    }
                }
            });
            return convertView;
        }
    }

    public interface OnClickItemListener{
        void onClickItem(DictionaryEntity entity);
    }
}
