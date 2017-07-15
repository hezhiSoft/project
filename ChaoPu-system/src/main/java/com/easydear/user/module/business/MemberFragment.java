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
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by LJH on 2017/7/1.
 */

public class MemberFragment extends BaseFragment {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.business_member_v1)
    TextView mBusiMemberV1;
    @BindView(R.id.business_member_v2)
    TextView mBusiMemberV2;
    @BindView(R.id.business_member_v3)
    TextView mBusiMemberV3;
    @BindView(R.id.business_member_v4)
    TextView mBusiMemberV4;
    @BindView(R.id.business_member_v5)
    TextView mBusiMemberV5;
    @BindView(R.id.business_member_priv)
    TextView mBusiMemberPrivilege;
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
//            mCardAdapter.addItems(businessDetailEntity.getCardList());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.business_member_v1, R.id.business_member_v2, R.id.business_member_v3, R.id.business_member_v4, R.id.business_member_v5})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.business_member_v1:
                mBusiMemberV1.setBackgroundColor(getResources().getColor(R.color.bright_red, null));
                mBusiMemberV2.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV3.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV4.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV5.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberPrivilege.setText("维修保养整单97折");
                break;
            case R.id.business_member_v2:
                mBusiMemberV2.setBackgroundColor(getResources().getColor(R.color.bright_red, null));
                mBusiMemberV1.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV3.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV4.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV5.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberPrivilege.setText("维修保养整单9折");
                break;
            case R.id.business_member_v3:
                mBusiMemberV3.setBackgroundColor(getResources().getColor(R.color.bright_red, null));
                mBusiMemberV1.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV2.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV4.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV5.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberPrivilege.setText("维修保养整单85折");
                break;
            case R.id.business_member_v4:
                mBusiMemberV4.setBackgroundColor(getResources().getColor(R.color.bright_red, null));
                mBusiMemberV1.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV2.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV3.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV5.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberPrivilege.setText("维修保养整单8折");
                break;
            case R.id.business_member_v5:
                mBusiMemberV5.setBackgroundColor(getResources().getColor(R.color.bright_red, null));
                mBusiMemberV1.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV2.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV3.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberV4.setBackgroundColor(getResources().getColor(R.color.light_gray, null));
                mBusiMemberPrivilege.setText("维修保养整单8折");
                break;
        }
    }
}
