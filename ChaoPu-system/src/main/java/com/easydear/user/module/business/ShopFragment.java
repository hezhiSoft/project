package com.easydear.user.module.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.easydear.user.module.business.data.BusinessDetailEntity.ActivityItemEntity;
import com.easydear.user.view.MeasuredListView;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.jinggan.library.utils.ILogcat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LJH on 2017/7/1.
 */

public class ShopFragment extends BaseFragment {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.business_shop_activity_lv)
    MeasuredListView mShopActivityListView;
    @BindView(R.id.business_shop_activity_card)
    PullToRefreshRecyclerView mBusiActivityCardListView;

    Unbinder unbinder;
    private ActivityAdapter mActivityAdapter;
    private BusinessCardAdapter mBusinessCardAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_business_shop, null);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initShopActivityListView();
        initBusinessCardRecyclerView();
    }

    private void initShopActivityListView() {
        mActivityAdapter = new ActivityAdapter(getActivity());
        mShopActivityListView.setAdapter(mActivityAdapter);
    }

    private void initBusinessCardRecyclerView() {
        mBusinessCardAdapter = new BusinessCardAdapter(getActivity());
        mBusiActivityCardListView.setRecyclerViewAdapter(mBusinessCardAdapter);
    }

    @Subscribe
    public void onUpdateUIData(EventBusValues value) {
        if (value.getWhat() == Constant.EventValue.SET_SHOP_ACTIVITY) {
            ILogcat.i(TAG, "onUpdateUIData SET_SHOP_ACTIVITY = " + value.getWhat());
            BusinessDetailEntity businessDetailEntity = (BusinessDetailEntity) value.getObject();

            ArrayList<ActivityItemEntity> activityList = businessDetailEntity.getActivityList();
            if (activityList != null && activityList.size() > 0) {
                mActivityAdapter.setActivityList(activityList);
                mActivityAdapter.notifyDataSetChanged();
            }

            ArrayList<BusinessDetailEntity.CardItemEntity> cardList = businessDetailEntity.getCardList();
            if (cardList != null && cardList.size() > 0) {
                ILogcat.i(TAG, "----------> onUpdateUIData cardList = " + cardList);
                mBusinessCardAdapter.addItems(cardList);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
