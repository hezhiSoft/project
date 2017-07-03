package com.easydear.user.module.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.jinggan.library.utils.ILogcat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LJH on 2017/7/1.
 */

public class MemberFragment extends BaseFragment {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.business_member_cardNum)
    TextView mBusiMemCardNumber;
    @BindView(R.id.business_member_cardlist)
    PullToRefreshRecyclerView mBusiMemCardListView;

    Unbinder unbinder;
    MemberCardAdapter mCardAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_business_member, null);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCardListRecyclerView();
    }

    private void initCardListRecyclerView() {
        mCardAdapter = new MemberCardAdapter(getActivity());
        mBusiMemCardListView.setRecyclerViewAdapter(mCardAdapter);
    }

    @Subscribe
    public void onUpdateUIData(EventBusValues value) {
        if (value.getWhat() == Constant.EventValue.SET_MEMBER_INFO) {
            ILogcat.i(TAG, "onUpdateUIData SET_MEMBER_INFO = " + value.getWhat());
            BusinessDetailEntity businessDetailEntity = (BusinessDetailEntity) value.getObject();
            mBusiMemCardNumber.setText("可用卡券  " + businessDetailEntity.getCardList().size());
            mCardAdapter.setBusinessLogo(businessDetailEntity.getLogo());
            mCardAdapter.addItems(businessDetailEntity.getCardList());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
