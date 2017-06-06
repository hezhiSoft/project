package com.xiaomai.telemarket.module.cstmr.fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.view.MainBottomNavigationBar;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.xiaomai.telemarket.MainActivity;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.CusrometManagementAdapter;
import com.xiaomai.telemarket.module.cstmr.FiltersButtomDialog;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.FiltersEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待跟进列表
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/14 11:37
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometManagementStayFragment extends BaseFragment implements RemetoRepoCallback<List<CusrometListEntity>>, FiltersButtomDialog.OnClickItemListener, PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener {

    @BindView(R.id.Stay_recyclerView)
    PullToRefreshRecyclerView CustomerAllRecyclerView;
    @BindView(R.id.Time_sor_TextView)
    TextView sorTextView;


    private CusrometManagementAdapter adapter;

    private int pageIndex;
    private String sort = "desc";/*默认降序*/
    private CusrometRemoteRepo remoteRepo;

    private JSONObject filters;

    private List<FiltersEntity> filtersEntities = new ArrayList<>();

    private boolean desc = true;

    private boolean hidden = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        remoteRepo = CusrometRemoteRepo.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_stay, null);
        ButterKnife.bind(this, rootView);
        adapter = new CusrometManagementAdapter(getContext(), 2);
        CustomerAllRecyclerView.setRecyclerViewAdapter(adapter);
        CustomerAllRecyclerView.setMode(PullToRefreshRecyclerView.Mode.BOTH);
        CustomerAllRecyclerView.setPullToRefreshListener(this);
        CustomerAllRecyclerView.startUpRefresh();
        return rootView;
    }

    @OnClick({R.id.Time_sor_TextView, R.id.Stay_toolBar_add_ImageView, R.id.Stay_toolBar_screen_ImageView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Stay_toolBar_add_ImageView:
                break;
            case R.id.Stay_toolBar_screen_ImageView:
                FiltersButtomDialog dialog = new FiltersButtomDialog();
                dialog.setSelectContent(filtersEntities).setListener(this);
                dialog.show(getFragmentManager(), getClass().getSimpleName());
                break;
            case R.id.Time_sor_TextView:
//                sorTextView.set
                if (!desc) {
                    Drawable drawable;
                    if (Build.VERSION.SDK_INT >= 21) {
                        drawable = getResources().getDrawable(R.mipmap.ic_sor_down, getActivity().getTheme());
                    } else {
                        drawable = getResources().getDrawable(R.mipmap.ic_sor_down);
                    }
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    sorTextView.setCompoundDrawables(null, null, drawable, null);
                    desc = true;
                    sort = "desc";
                } else {
                    Drawable drawable;
                    if (Build.VERSION.SDK_INT >= 21) {
                        drawable = getResources().getDrawable(R.mipmap.ic_sor_up, getActivity().getTheme());
                    } else {
                        drawable = getResources().getDrawable(R.mipmap.ic_sor_up);
                    }
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    sorTextView.setCompoundDrawables(null, null, drawable, null);
                    desc = false;
                    sort = "asc";
                }
                CustomerAllRecyclerView.startUpRefresh();
                break;
        }
    }

    @Override
    public void onClickItem(List<FiltersEntity> entity) {
        if (entity != null && entity.size() > 0) {
            this.filtersEntities = entity;
            try {
                filters = new JSONObject();
                for (FiltersEntity filtersEntity : entity) {
                    filters.put(filtersEntity.getKey(), filtersEntity.getCode());
                }
                CustomerAllRecyclerView.startUpRefresh();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onReplance() {
        filtersEntities.clear();
        filters = null;
        CustomerAllRecyclerView.startUpRefresh();
    }

    @Override
    public void onDownRefresh() {
        pageIndex = 1;
        remoteRepo.requestStayFollow(pageIndex, sort,filters, this);
    }

    @Override
    public void onPullRefresh() {
        pageIndex++;
        remoteRepo.requestStayFollow(pageIndex,sort, filters, this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
    }

    @Override
    public void onSuccess(List<CusrometListEntity> data) {
        if (pageIndex == 1) {
            adapter.clearList();
            CustomerAllRecyclerView.setEmptyTextViewVisiblity(View.GONE);
            adapter.addItems(data);
            ((MainActivity) getActivity()).getMainBottomNavigationBar().addTabSign(2, data.size());
            EventBusValues busValues = new EventBusValues();
            busValues.setWhat(0x900);
            busValues.setObject(data.size());
            if (!hidden) {
                ((MainActivity) getActivity()).getMainBottomNavigationBar().setFirstSelectedTab(2);
            }
        } else {
            adapter.addItems(data, adapter.getItemCount() - 1);
            if (data.size() <= 0) {
                CustomerAllRecyclerView.onLoadMoreFinish();
            }
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        if (pageIndex == 1) {
            adapter.clearList();
            CustomerAllRecyclerView.setPageHint(R.mipmap.icon_data_empty,"资料为空");
        } else {
//            showToast(msg);
        }
    }

    @Override
    public void onThrowable(Throwable t) {
//        showToast("数据异常");
        CustomerAllRecyclerView.setPageHint(R.mipmap.icon_page_error,"页面出错");
    }

    @Override
    public void onUnauthorized() {
//        showToast("数据获取失败");
        CustomerAllRecyclerView.setPageHint(R.mipmap.icon_page_error,"页面出错");
    }

    @Override
    public void onFinish() {
        CustomerAllRecyclerView.closeDownRefresh();
    }

}
