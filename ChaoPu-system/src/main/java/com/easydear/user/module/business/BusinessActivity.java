package com.easydear.user.module.business;

import android.Manifest;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.easydear.user.module.business.data.soruce.BussinessRepo;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;
import com.jinggan.library.ui.widget.WaytoTabLayout;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.IStringUtils;
import com.jinggan.library.utils.ISystemUtil;
import com.jinggan.library.utils.PermissionHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LJH on 2017/1/15.
 */

public class BusinessActivity extends BaseActivity implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallbackV2<BusinessDetailEntity> {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.business_bg_img)
    ImageView mBusinessLogoImg;
    @BindView(R.id.business_name)
    TextView mBusinessNameTV;
    @BindView(R.id.business_address)
    TextView mBusinessAddress;
    @BindView(R.id.business_logo)
    ImageView businessLogo;
    @BindView(R.id.business_tab_layout)
    WaytoTabLayout mBusinessTabLayout;


    private String mBusinessNo;

    private BussinessRepo mBussinessRepo;
    private static final int BUSINESS_SCROLL_IMAGE = 1001;
    private List<ImageView> dots = new ArrayList<ImageView>();

    private String[] mTabNames;
    private String[] mTabKeys = new String[]{"mdhd", "sjxq", "hyzx"};
    private List<BaseFragment> mFragments = new ArrayList<>();

    private BusinessDetailEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_business);
        mBusinessNo = getIntent().getStringExtra("businessNo");

        ButterKnife.bind(this);
        mBussinessRepo = BussinessRepo.getInstance();
        initTab();


        requestBusinessDetail();
    }

    @OnClick({R.id.business_location, R.id.business_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.business_location:
                if (entity==null){
                    showToast("数据异常");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putDouble("lng", IStringUtils.toDouble(entity.getLongitude()));
                bundle.putDouble("lat", IStringUtils.toDouble(entity.getLatitude()));

                ISkipActivityUtil.startIntent(this, LocationActivity.class,bundle);
                break;
            case R.id.business_phone:
                if (entity==null){
                    showToast("数据异常");
                    return;
                }

                if (PermissionHelper.checkPermission(this, Manifest.permission.CALL_PHONE, 0x3001)) {
                    ISystemUtil.makeCall(this, entity.getTelephone(), false);
                }
                break;
        }
    }

    private void initTab() {
        mTabNames = getResources().getStringArray(R.array.business_tab_array);
        for (int i = 0; i < mTabKeys.length; i++) {

            Bundle bundle = new Bundle();
            bundle.putString("key", mTabKeys[i]);
            switch (i) {
                case 0:
                    ShopFragment shopFragment = new ShopFragment();
                    shopFragment.setArguments(bundle);
                    mFragments.add(shopFragment);
                    break;
                case 1:
                    BusinessDetailFragment businessDetailFragment = new BusinessDetailFragment();
                    businessDetailFragment.setArguments(bundle);
                    mFragments.add(businessDetailFragment);
                    break;
                case 2:
                    MemberFragment memberFragment = new MemberFragment();
                    memberFragment.setArguments(bundle);
                    mFragments.add(memberFragment);
                    break;
                default:
                    break;
            }
        }
        mBusinessTabLayout.initTabLayout(getSupportFragmentManager(), mFragments, mTabNames);
    }

    @Override
    public void onDownRefresh() {
//        mBusinessPresenter.requestBusinessCardList();
    }

    @Override
    public void onPullRefresh() {

    }

    private void requestBusinessDetail() {
        mBussinessRepo.queryBusinessDetail(mBusinessNo, this);
    }


    @Override
    protected void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
//        ILog.v(TAG, "dispatchMessage msg.what = " + msg.what);
        switch (msg.what) {
            case BUSINESS_SCROLL_IMAGE:
                mHandler.removeMessages(BUSINESS_SCROLL_IMAGE);
//                int pos = mScrollViewPager.getCurrentItem();
//                ILog.v(TAG, "dispatchMessage pos1 = " + pos);
//                mScrollViewPager.setCurrentItem(++pos);
//                ILog.d(TAG, "dispatchMessage pos2 = " + mScrollViewPager.getCurrentItem());
//                mHandler.sendEmptyMessageDelayed(BUSINESS_SCROLL_IMAGE, Constant.FIVE_SECONDES);
                break;
            default:
                break;
        }
    }

    @Override
    public void onReqStart() {

    }

    @Override
    public void onSuccess(BusinessDetailEntity data) {
        if (data == null) {
            return;
        }

        this.entity = data;
        /** Set All Details */
        setBusinessInfo(entity);
        setShopActivity(entity);
        setBusinessDetail(entity);
        setMemberInfo(entity);
    }

    @Override
    public void onFailure(int code, String msg) {

    }

    @Override
    public void onFinish() {

    }

    private void setBusinessInfo(BusinessDetailEntity businessDetailEntity) {
        if (businessDetailEntity == null) {
            return;
        }
        setToolbarTitle(TextUtils.isEmpty(businessDetailEntity.getBusinessName()) ? "商家详情" : businessDetailEntity.getBusinessName());

        String province = businessDetailEntity.getProvinceAdd();
        String city = businessDetailEntity.getCityAdd();
        String area = businessDetailEntity.getAreaAdd();
        String street = businessDetailEntity.getStreetAdd();
        String address = (province == null ? "" : province) +
                (city == null ? "" : city) +
                (area == null ? "" : area) +
                (street == null ? "" : street) +
                businessDetailEntity.getAddress();


        mBusinessNameTV.setText(businessDetailEntity.getBusinessName());
        mBusinessAddress.setText(address);
//        mBusinessTelephone.setText(entity.getTelephone());

        /*商家宣传图片*/
        Glide.with(this).load(BuildConfig.DOMAIN + businessDetailEntity.getBusinessImages())
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.main_img_defaultpic_small)
                .into(mBusinessLogoImg);

        /*商家Logo*/
        Glide.with(this).load(BuildConfig.DOMAIN + businessDetailEntity.getLogo())
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.default_head_img)
                .error(R.mipmap.default_head_img)
                .into(new RoundedBitmapImageViewTarget(this, businessLogo));


    }

    private void setShopActivity(BusinessDetailEntity businessDetailEntity) {
        EventBusValues busValue = new EventBusValues();
        busValue.setWhat(Constant.EventValue.SET_SHOP_ACTIVITY);
        busValue.setObject(businessDetailEntity);
        EventBus.getDefault().post(busValue);
    }

    private void setBusinessDetail(BusinessDetailEntity businessDetailEntity) {
        EventBusValues busValue = new EventBusValues();
        busValue.setWhat(Constant.EventValue.SET_BUSINESS_DETAIL);
        busValue.setObject(businessDetailEntity);
        EventBus.getDefault().post(busValue);
    }

    private void setMemberInfo(BusinessDetailEntity businessDetailEntity) {
        EventBusValues busValue = new EventBusValues();
        busValue.setWhat(Constant.EventValue.SET_MEMBER_INFO);
        busValue.setObject(businessDetailEntity);
        EventBus.getDefault().post(busValue);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x3001) {
            ISystemUtil.makeCall(this, entity.getTelephone(), false);
        }
    }
}
