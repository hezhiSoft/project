package com.xiaomai.telemarket.module.function.callTrend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.function.data.CallOutDepStaticsEntity;
import com.xiaomai.telemarket.module.function.data.StatisticsByMonthParam;
import com.xiaomai.telemarket.module.function.data.source.CallOutRemoteRepo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 外呼趋势详情
 * @createtime 14/06/2017 1:04 AM
 **/
public class CallTrendDetailFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener,RemetoRepoCallback<List<CallOutDepStaticsEntity>> {

    @BindView(R.id.MonthStatistic_recyclerView)
    PullToRefreshRecyclerView MonthStatisticRecyclerView;
    Unbinder unbinder;

    private DepStatisticListAdapter mAdapter;

    private CallOutRemoteRepo mRepo;

    private StatisticsByMonthParam paramEntity;
    public static final String EXTRA_PARAM = "extra_param";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null&&bundle.containsKey(EXTRA_PARAM)) {
            paramEntity= (StatisticsByMonthParam) bundle.getSerializable(EXTRA_PARAM);
        }
        mRepo = CallOutRemoteRepo.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics_trend_detail, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mAdapter = new DepStatisticListAdapter(getActivity());
        MonthStatisticRecyclerView.setRecyclerViewAdapter(mAdapter);
        MonthStatisticRecyclerView.setMode(PullToRefreshRecyclerView.Mode.PULL_FROM_START);
        MonthStatisticRecyclerView.setPullToRefreshListener(this);
        MonthStatisticRecyclerView.startUpRefresh();//
    }

    public void setParamEntity(StatisticsByMonthParam paramEntity) {
        this.paramEntity = paramEntity;
    }

    public StatisticsByMonthParam getParamEntity() {
        return this.paramEntity;
    }

    @Override
    public void onDownRefresh() {
        if (paramEntity!=null) {
            mRepo.queryStatisticByDep(paramEntity,this);
        }
    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(List<CallOutDepStaticsEntity> data) {
        if (MonthStatisticRecyclerView!=null) {
            MonthStatisticRecyclerView.setEmptyTextViewVisiblity(View.GONE);
        }
        mAdapter.clearList();
        mAdapter.addItems(data);
    }

    @Override
    public void onFailure(int code, String msg) {
        mAdapter.clearList();
        if (MonthStatisticRecyclerView!=null) {// TODO: 30/05/2017 连续两次重启app这里报空指针错误
            MonthStatisticRecyclerView.setPageHint(R.mipmap.icon_data_empty,"数据为空");
        }
    }

    @Override
    public void onThrowable(Throwable t) {
        if (MonthStatisticRecyclerView != null) {
            MonthStatisticRecyclerView.setPageHint(R.mipmap.icon_page_error,"页面出错");
        }
    }

    @Override
    public void onUnauthorized() {
        if (MonthStatisticRecyclerView != null) {
            MonthStatisticRecyclerView.setPageHint(R.mipmap.icon_page_error,"页面出错");
        }
    }

    @Override
    public void onFinish() {
        if (MonthStatisticRecyclerView!=null) {
            MonthStatisticRecyclerView.closeDownRefresh();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRepo!=null) {
            mRepo.cancelRequest();
        }
    }
}
