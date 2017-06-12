package com.easydear.user.module.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easydear.user.R;
import com.easydear.user.module.business.BusinessListAdapter;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-06-11
 * Time: 02:37
 * Version:1.0
 */

public class DynamicListFragment extends BaseFragment {

    @BindView(R.id.PageListFragment_RecyclerView)
    PullToRefreshRecyclerView PageListFragmentRecyclerView;
    Unbinder unbinder;

    private String key;
    private DynamicListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        key = getArguments().getString("key");
        adapter=new DynamicListAdapter(getContext());
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

    private void initRecylerView(){
        PageListFragmentRecyclerView.setRecyclerViewAdapter(adapter);
    }
}
