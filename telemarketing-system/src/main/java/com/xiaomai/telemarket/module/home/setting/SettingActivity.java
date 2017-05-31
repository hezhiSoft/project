package com.xiaomai.telemarket.module.home.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.BuildConfig;
import com.xiaomai.telemarket.MainActivity;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.appCheck.AppCheckHelper;
import com.xiaomai.telemarket.appCheck.data.VersionEntity;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.account.LoginActivity;
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
    @BindView(R.id.Version_update)
    TextView VersionUpdate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_menu);
        ButterKnife.bind(this);
        setToolbarVisibility(View.GONE);
        setSwipeEnabled(false);
        initEvent();
    }

    private void initEvent() {
        layoutTitle.setOnNaviBarClickListener(this);
        VersionUpdate.setText("版本更新V"+ BuildConfig.VERSION_NAME);
    }

    @OnClick({R.id.Version_update,R.id.tv_user_state_set, R.id.tv_dialing_source_set, R.id.tv_exit})
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
                DialogFactory.showMsgDialog(this, "退出", "确定退出当前账号?", "退出", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 19/05/2017 退出登录接口
                        ISharedPreferencesUtils.setValue(SettingActivity.this, Constant.ISLOGIN_KEY, false);
                        ISkipActivityUtil.startIntent(SettingActivity.this, LoginActivity.class);
//                        IActivityManage.getInstance().exit();
                    }
                }, null);
                break;
            case R.id.Version_update:
                break;
        }
    }

    @Override
    public void onBackClick() {
        ISkipActivityUtil.startIntent(SettingActivity.this, MainActivity.class);
        finish();
    }

    @Override
    public void onAddClick() {

    }

    @Override
    public void onFilterClick() {

    }
}
