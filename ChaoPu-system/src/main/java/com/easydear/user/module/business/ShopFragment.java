package com.easydear.user.module.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.business.data.ActivityItemEntity;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.easydear.user.module.business.data.CardItemEntity;
import com.easydear.user.view.MeasuredListView;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
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

    Unbinder unbinder;
    private ActivityAdapter mActivityAdapter;

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
    }

    private void initShopActivityListView() {
        mActivityAdapter = new ActivityAdapter(getActivity());
        mShopActivityListView.setAdapter(mActivityAdapter);
    }

    @Subscribe
    public void onUpdateUIData(EventBusValues value) {
        if (value.getWhat() == Constant.EventValue.SET_SHOP_ACTIVITY) {
            ILogcat.i(TAG, "onUpdateUIData SET_SHOP_ACTIVITY = " + value.getWhat());
            BusinessDetailEntity businessDetailEntity = (BusinessDetailEntity) value.getObject();

            ArrayList<String> activityList = businessDetailEntity.getActivityList();
            if (activityList != null) {
//        if (activityList != null && activityList.size() > 0) {
                // TODO To be finished!
                ArrayList<ActivityItemEntity> items = new ArrayList<ActivityItemEntity>();

                ActivityItemEntity item1 = new ActivityItemEntity();
                item1.setType("赠");
                item1.setContent("常规保养，送空气滤芯");
                items.add(item1);

                ActivityItemEntity item2 = new ActivityItemEntity();
                item2.setType("银");
                item2.setContent("刷中国银行信用卡，保养8折");
                items.add(item2);

                ActivityItemEntity item3 = new ActivityItemEntity();
                item3.setType("VIP");
                item3.setContent("VIP会员保养，买三赠一");
                items.add(item3);

                ActivityItemEntity item4 = new ActivityItemEntity();
                item4.setType("VIP");
                item4.setContent("添加会员，领VIP优惠护照套票");
                items.add(item4);

                mActivityAdapter.setActivityList(items);
                mActivityAdapter.notifyDataSetChanged();
            }

            ArrayList<CardItemEntity> cardList = businessDetailEntity.getCardList();
            if (cardList != null && cardList.size() > 0) {
                ILogcat.i(TAG, "----------> onUpdateUIData cardList = " + cardList);
                // TODO To be finished!
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
