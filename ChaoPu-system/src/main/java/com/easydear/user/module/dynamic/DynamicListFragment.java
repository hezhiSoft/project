package com.easydear.user.module.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.dynamic.data.DynamicEntity;
import com.easydear.user.module.dynamic.data.soruce.DynamicRepo;
import com.easydear.user.module.search.SearchActivity;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.jinggan.library.utils.ISkipActivityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-06-11
 * Time: 02:37
 * Version:1.0
 */

public class DynamicListFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallbackV2<List<DynamicEntity>> {

    @BindView(R.id.PageListFragment_RecyclerView)
    PullToRefreshRecyclerView PageListFragmentRecyclerView;
    Unbinder unbinder;

    private String key;
    private DynamicListAdapter adapter;

    private DynamicRepo dynamicRepo;
    private int pageSize = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        key = getArguments().getString("key");
        adapter = new DynamicListAdapter(getContext());

        dynamicRepo = DynamicRepo.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_list, null);
        unbinder = ButterKnife.bind(this, rootView);
        initRecylerView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initRecylerView() {
        PageListFragmentRecyclerView.setRecyclerViewAdapter(adapter);
        PageListFragmentRecyclerView.setPullToRefreshListener(this);
        PageListFragmentRecyclerView.startUpRefresh();
    }

    @Override
    public void onDownRefresh() {
        pageSize = 1;
        dynamicRepo.queryDynamics(pageSize, 20, "", key, "", "", "", this);
    }

    @Override
    public void onPullRefresh() {
        pageSize++;
        dynamicRepo.queryDynamics(pageSize, 20, "", key, "", "", "", this);
    }

    @Override
    public void onReqStart() {

    }

    @Override
    public void onSuccess(List<DynamicEntity> data) {
        if (pageSize == 1) {
            adapter.clearList();
            adapter.addItems(data);
            if (data == null || data.size() <= 0) {
                PageListFragmentRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
            }
        } else {
            adapter.addItems(data, adapter.getItemCount() - 1);
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        if (pageSize == 1) {
            PageListFragmentRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        } else {
            showToast(msg);
        }
    }

    @Override
    public void onFinish() {
        PageListFragmentRecyclerView.closeDownRefresh();
    }

}
