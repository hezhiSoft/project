package com.easydear.user.module.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.easydear.user.R;
import com.easydear.user.adapter.TextGridAdapter;
import com.easydear.user.common.Constant;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.utils.ILogcat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LJH on 2017/7/1.
 */

public class BusinessDetailFragment extends BaseFragment {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.business_detail_opentime)
    TextView mBusiDetailOpenTime;
    @BindView(R.id.business_detail_brandname)
    TextView mBusiDetailBrandName;
    @BindView(R.id.business_detail_busitime)
    TextView mBusiDetailBusiTime;
    @BindView(R.id.business_detail_businessdescription)
    TextView mBusiDetailDescription;
    @BindView(R.id.business_detail_service)
    GridView mBusiDetailService;

    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_business_detail, null);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Subscribe
    public void onUpdateUIData(EventBusValues value) {
        if (value.getWhat() == Constant.EventValue.SET_BUSINESS_DETAIL) {
            ILogcat.i(TAG, "onUpdateUIData SET_BUSINESS_DETAIL = " + value.getWhat());
            BusinessDetailEntity businessDetailEntity = (BusinessDetailEntity) value.getObject();
            mBusiDetailOpenTime.setText(businessDetailEntity.getOpentime());
            mBusiDetailBrandName.setText(businessDetailEntity.getBrandName());
            mBusiDetailBusiTime.setText(businessDetailEntity.getBusinessTime());
            mBusiDetailDescription.setText(businessDetailEntity.getBusinessDescription());
            mBusiDetailService.setAdapter(new TextGridAdapter(getActivity(), getServiceList(businessDetailEntity.getMerchantServices())));
        }
    }

    private List<String> getServiceList(String merchantServices) {
        if (merchantServices == null) {
            return null;
        }
        List<String> services = new ArrayList<String>();
        String[] serviceArr = merchantServices.split(",");
        int len = serviceArr.length;
        for (int i = 0; i < len; i++) {
            services.add(serviceArr[i]);
        }
        return services;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
