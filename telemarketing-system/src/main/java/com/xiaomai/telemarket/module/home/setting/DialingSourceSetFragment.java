package com.xiaomai.telemarket.module.home.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.xiaomai.telemarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 拨号名单源
 * @createtime 06/05/2017 4:17 PM
 **/
public class DialingSourceSetFragment extends BaseFragment {

    @BindView(R.id.tv_source_public)
    TextView tvSourcePublic;
    @BindView(R.id.tv_source_private)
    TextView tvSourcePrivate;
    Unbinder unbinder;

    public static BaseFragment newInstance(){
        return new DialingSourceSetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_dialing_source_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_source_public, R.id.tv_source_private})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_source_public:
                ToastUtil.showToast(getContext(), "公共名单库");
                break;
            case R.id.tv_source_private:
                ToastUtil.showToast(getContext(), "我的客户列表");
                break;
        }
    }
}
