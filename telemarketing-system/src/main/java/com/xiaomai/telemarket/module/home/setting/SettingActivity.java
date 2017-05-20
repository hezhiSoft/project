package com.xiaomai.telemarket.module.home.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.view.widget.TitleLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 设置界面
 * @createtime 06/05/2017 4:12 PM
 **/
public class SettingActivity extends BaseActivity implements TitleLayout.OnNaviBarClickListener {

    @BindView(R.id.tv_user_state_set)
    TextView tvUserStateSet;
    @BindView(R.id.tv_dialing_source_set)
    TextView tvDialingSourceSet;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.layout_title)
    TitleLayout layoutTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_menu);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        layoutTitle.setOnNaviBarClickListener(this);
    }

    @OnClick({R.id.tv_user_state_set, R.id.tv_dialing_source_set, R.id.tv_exit})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_user_state_set:
                bundle.putString(SettingEditActivity.EXTRA_TAG,SettingEditActivity.TAG_USERSTATE);
                ISkipActivityUtil.startIntent(SettingActivity.this, SettingEditActivity.class, bundle);
                break;
            case R.id.tv_dialing_source_set:
                bundle.putString(SettingEditActivity.EXTRA_TAG,SettingEditActivity.TAG_DIALING);
                ISkipActivityUtil.startIntent(SettingActivity.this, SettingEditActivity.class, bundle);
                break;
            case R.id.tv_exit:
                ToastUtil.showToast(DataApplication.getInstance(), "退出系统");
                break;
        }
    }

    @Override
    public void onBackClick() {

    }

    @Override
    public void onAddClick() {

    }

    @Override
    public void onFilterClick() {

    }
}
