package com.xiaomai.telemarket.module.home.setting;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.common.Constant;

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
    private Drawable drawable;

    public static BaseFragment newInstance() {
        return new DialingSourceSetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_dialing_source_layout, null);
        unbinder = ButterKnife.bind(this, view);
        changeUIState(ISharedPreferencesUtils.getValue(getActivity(), Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PRIVATE).equals(Constant.DIAL_NUMBER_CODE_PUBLIC));
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
                ISharedPreferencesUtils.setValue(getActivity(), Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PUBLIC);
                changeUIState(true);
                break;
            case R.id.tv_source_private:
                ISharedPreferencesUtils.setValue(getActivity(), Constant.DIAL_NUMBER_SOURCE, Constant.DIAL_NUMBER_CODE_PRIVATE);
                changeUIState(false);
                break;
        }
        if (getActivity()!=null) {
            getActivity().finish();
        }
    }

    private void changeUIState(boolean isPublic) {
        if (drawable == null) {
            drawable = getResources().getDrawable(R.mipmap.ic_select);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        if (isPublic) {
            tvSourcePublic.setCompoundDrawables(null, null, drawable, null);
            tvSourcePrivate.setCompoundDrawables(null, null, null, null);
        } else {
            tvSourcePublic.setCompoundDrawables(null, null, null, null);
            tvSourcePrivate.setCompoundDrawables(null, null, drawable, null);
        }
        tvSourcePrivate.refreshDrawableState();
        tvSourcePublic.refreshDrawableState();
    }
}
