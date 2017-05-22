package com.xiaomai.telemarket.module.home.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.home.setting.data.UserStateEntity;
import com.xiaomai.telemarket.module.home.setting.data.repo.UserStateRemoteRepo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 用户状态设置
 * @createtime 06/05/2017 4:18 PM
 **/
public class UserStateSetFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallback<String> {


    @BindView(R.id.user_state_recyclerview)
    PullToRefreshRecyclerView user_state_recyclerview;
    Unbinder unbinder;
    private UserStateListAdapter adapter;

    private UserStateRemoteRepo userStateRemoteRepo;

    public static BaseFragment newInstance() {
        return new UserStateSetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userStateRemoteRepo = UserStateRemoteRepo.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_userstate_layout, null);
        unbinder = ButterKnife.bind(this, view);
        adapter=new UserStateListAdapter(getContext());
        initUserStateList();
        user_state_recyclerview.setRecyclerViewAdapter(adapter);
        user_state_recyclerview.setMode(PullToRefreshRecyclerView.Mode.DISABLED);//TODO 这里不做刷新
        user_state_recyclerview.setPullToRefreshListener(this);
        user_state_recyclerview.startUpRefresh();
        return view;
    }

    private void initUserStateList(){
        String userStateJson=ISharedPreferencesUtils.getValue(getActivity(), Constant.USER_STATE_LIST,"").toString();
        if (!TextUtils.isEmpty(userStateJson)) {
            try {
                List<UserStateEntity> userStateEntities = new Gson().fromJson(userStateJson,new TypeToken<ArrayList<UserStateEntity>>(){}.getType());
                if (userStateEntities!=null&&userStateEntities.size()>0) {
                    adapter.addItems(userStateEntities);
                    String state = ISharedPreferencesUtils.getValue(getActivity(), Constant.USER_STATE, Constant.UserState.INWORK.getValue()) + "";
                    adapter.changeUserState(state);
                }
            }catch (Exception ex){

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
//        userStateRemoteRepo.cancelRequest();
    }

    @Override
    public void onDownRefresh() {
//        userStateRemoteRepo.requestUserCurrentState(this);
    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onSuccess(String data) {
//        adapter.changeUserState(data);
    }

    @Override
    public void onFailure(int code, String msg) {

    }

    @Override
    public void onThrowable(Throwable t) {

    }

    @Override
    public void onUnauthorized() {

    }

    @Override
    public void onFinish() {

    }

}
