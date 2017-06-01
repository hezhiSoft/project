package com.xiaomai.telemarket.module.cstmr.fragment.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.BuildConfig;
import com.xiaomai.telemarket.MainActivity;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.appCheck.AppCheckHelper;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.account.LoginActivity;
import com.xiaomai.telemarket.module.home.setting.SettingEditActivity;
import com.xiaomai.telemarket.view.widget.TitleLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/28$ 上午11:51$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class MineFragment extends BaseFragment implements TitleLayout.OnNaviBarClickListener {

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
    @BindView(R.id.tv_user_state)
    TextView tvUserState;
    @BindView(R.id.tv_dialing_source)
    TextView tvDialingSource;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_setting_menu, null);
        unbinder=ButterKnife.bind(this, rootView);
        initUI();
        initEvent();
        return rootView;
    }

    private void initUI() {
        tvUserState.setText(ISharedPreferencesUtils.getValue(getActivity(), Constant.USER_STATE_NAME_KEY, "上线").toString());
        if (ISharedPreferencesUtils.getValue(getActivity(), Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PRIVATE).equals(Constant.DIAL_NUMBER_CODE_PUBLIC)) {
            tvDialingSource.setText(getResources().getString(R.string.text_number_library_public));
        } else {
            tvDialingSource.setText(getResources().getString(R.string.text_number_dialing_private));
        }
    }

    private void initEvent() {
        layoutTitle.setOnNaviBarClickListener(this);
        VersionUpdate.setText("版本更新V" + BuildConfig.VERSION_NAME);
    }

    @OnClick({R.id.Version_update, R.id.tv_user_state_set, R.id.tv_dialing_source_set, R.id.tv_exit})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_user_state_set:
                bundle.putString(SettingEditActivity.EXTRA_TAG, SettingEditActivity.TAG_USERSTATE);
                ISkipActivityUtil.startIntent(getActivity(), SettingEditActivity.class, bundle);
                break;
            case R.id.tv_dialing_source_set:
                bundle.putString(SettingEditActivity.EXTRA_TAG, SettingEditActivity.TAG_DIALING);
                ISkipActivityUtil.startIntent(getActivity(), SettingEditActivity.class, bundle);
                break;
            case R.id.tv_exit:
                DialogFactory.showMsgDialog(getContext(), "退出", "确定退出当前账号?", "退出", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 19/05/2017 退出登录接口
                        ISharedPreferencesUtils.setValue(getActivity(), Constant.ISLOGIN_KEY, false);
                        ISkipActivityUtil.startIntent(getActivity(), LoginActivity.class);
//                        IActivityManage.getInstance().exit();
                    }
                }, null);
                break;
            case R.id.Version_update:
                AppCheckHelper.getInstance().checkVersion(getActivity(), true);
                break;
        }
    }

    @Override
    public void onBackClick() {
        ISkipActivityUtil.startIntent(getActivity(), MainActivity.class);
        getActivity().finish();
    }

    @Override
    public void onAddClick() {

    }

    @Override
    public void onFilterClick() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}


