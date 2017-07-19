package com.easydear.user.module.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.easydear.user.view.MeasuredListView;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.jinggan.library.utils.ILogcat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LJH on 2017/7/1.
 */

public class ShopFragment extends BaseFragment {

    @BindView(R.id.business_shop_activity_lv)
    MeasuredListView mShopActivityListView;
    @BindView(R.id.business_shop_activity_card)
    PullToRefreshRecyclerView mShopCardListView;

    Unbinder unbinder;

    private ActivityAdapter mActivityAdapter;
    private BusinessCardAdapter mCardAdapter;

    private BusinessDetailEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entity = (BusinessDetailEntity) getArguments().getSerializable("entity");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_business_shop, null);
        unbinder = ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI(){
        if (entity==null){
            return;
        }
        List<BusinessDetailEntity.ActivityListBean> activityList = entity.getActivityList();
        if (activityList !=null && activityList.size() > 0) {
            setShopActivityListView(activityList);
        }

        List<BusinessDetailEntity.CardListBean> cardList = entity.getCardList();
        if (cardList != null && cardList.size() > 0) {
            setShopCardListView(entity.getCardList());
        }
    }

    private void setShopActivityListView(List<BusinessDetailEntity.ActivityListBean> entities) {
        mActivityAdapter = new ActivityAdapter(getActivity());
        mActivityAdapter.setList(entities);
        mShopActivityListView.setAdapter(mActivityAdapter);
    }

    private void setShopCardListView(final List<BusinessDetailEntity.CardListBean> cardList) {
        mCardAdapter = new BusinessCardAdapter(getActivity());
        mCardAdapter.addItems(cardList);
        mShopCardListView.setRecyclerViewAdapter(mCardAdapter);
        mCardAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                ILogcat.i(getClass().getSimpleName(), "----------> getCardNo = " + cardList.get(position).getCardNo());

            }
        });
        ILogcat.i(getClass().getSimpleName(), "----------> getBusinessNo = " + entity.getBusinessNo());
    }

}