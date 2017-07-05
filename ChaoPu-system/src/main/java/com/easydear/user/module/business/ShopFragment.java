package com.easydear.user.module.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.easydear.user.view.MeasuredListView;
import com.jinggan.library.base.BaseFragment;

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
    @BindView(R.id.Title)
    TextView Title;
    @BindView(R.id.ActivityName)
    TextView ActivityName;

    Unbinder unbinder;

    private ActivityAdapter mActivityAdapter;

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
        List<BusinessDetailEntity.ActivityListBean> been=entity.getActivityList();
        if (been!=null&&been.size()>0){
            Title.setText(been.get(0).getTitle());
            ActivityName.setText(been.get(0).getActivityName());
        }

        initShopActivityListView(entity.getCardList());
    }


    private void initShopActivityListView(List<BusinessDetailEntity.CardListBean> entity) {
        mActivityAdapter = new ActivityAdapter(getActivity());
        mShopActivityListView.setAdapter(mActivityAdapter);
        mActivityAdapter.appendToList(entity);
    }

}
