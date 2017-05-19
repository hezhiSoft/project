package com.xiaomai.telemarket.module.home.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.xiaomai.telemarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 用户状态设置
 * @createtime 06/05/2017 4:18 PM
 **/
public class UserStateSetFragment extends BaseFragment {

    @BindView(R.id.tv_state_online)
    TextView tvStateOnline;
    @BindView(R.id.tv_state_away)
    TextView tvStateAway;
    @BindView(R.id.tv_state_busy)
    TextView tvStateBusy;
    @BindView(R.id.tv_state_not_disturb)
    TextView tvStateNotDisturb;
    Unbinder unbinder;

    public static BaseFragment newInstance(){
        return new UserStateSetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_userstate_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_state_online, R.id.tv_state_away, R.id.tv_state_busy, R.id.tv_state_not_disturb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_state_online:

                break;
            case R.id.tv_state_away:

                break;
            case R.id.tv_state_busy:

                break;
            case R.id.tv_state_not_disturb:

                break;
        }
    }

    private void changeSelectState(int selectId){

    }

}
