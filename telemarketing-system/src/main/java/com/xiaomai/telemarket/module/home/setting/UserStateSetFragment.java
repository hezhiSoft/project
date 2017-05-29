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
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.home.setting.data.UserStateEntity;

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
public class UserStateSetFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener,BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener,UserStateSetContract.View {


    @BindView(R.id.user_state_recyclerview)
    PullToRefreshRecyclerView user_state_recyclerview;
    Unbinder unbinder;
    private UserStateListAdapter adapter;

    private UserStateChangePresenter userStateChangePresenter;

    public static BaseFragment newInstance() {
        return new UserStateSetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userStateChangePresenter = new UserStateChangePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_userstate_layout, null);
        unbinder = ButterKnife.bind(this, view);
        adapter=new UserStateListAdapter(getContext());
        initUserStateList();
        adapter.setOnItemClickListener(this);
        user_state_recyclerview.setRecyclerViewAdapter(adapter);
        user_state_recyclerview.setMode(PullToRefreshRecyclerView.Mode.DISABLED);//TODO 这里不做刷新
        user_state_recyclerview.setPullToRefreshListener(this);
//        user_state_recyclerview.startUpRefresh();
        return view;
    }

    private void initUserStateList(){
        String userStateJson=ISharedPreferencesUtils.getValue(getActivity(), Constant.USER_STATE_LIST,"").toString();
        if (!TextUtils.isEmpty(userStateJson)) {
            try {
                List<UserStateEntity> userStateEntities = new Gson().fromJson(userStateJson,new TypeToken<ArrayList<UserStateEntity>>(){}.getType());
                if (userStateEntities!=null&&userStateEntities.size()>0) {
                    adapter.addItems(userStateEntities);
                    String state = ISharedPreferencesUtils.getValue(getActivity(), Constant.USER_STATE_KEY, Constant.UserState.INWORK.getValue()) + "";
                    adapter.changeUserState(state);
                }
            }catch (Exception ex){

            }
        }
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        if (data!=null) {
            UserStateEntity entity = (UserStateEntity) data;
            userStateChangePresenter.changeUserState(Integer.valueOf(entity.getCode()));
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
    public void showChangeUserStateStart() {
        showProgressDlg(getResources().getString(R.string.common_progress_dlg_tip));
    }

    @Override
    public void showChangeUserStateSuccess(int status) {
        dismissProgressDlg();
        adapter.changeUserState(status + "");
        ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.USER_STATE_KEY, status);
        ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.USER_STATE_NAME_KEY, adapter.getUserState());
    }

    @Override
    public void showChangeUserStateFailed(String msg) {
        dismissProgressDlg();
        ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(), msg);
    }
}
