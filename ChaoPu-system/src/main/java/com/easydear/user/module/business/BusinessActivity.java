package com.easydear.user.module.business;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.jinggan.library.ui.widget.WaytoTabLayout;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LJH on 2017/1/15.
 */

public class BusinessActivity extends BaseActivity implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallbackV2<BusinessDetailEntity> {

    private final String TAG = this.getClass().getSimpleName();

    //    @BindView(R.id.business_scroll_vp)
//    ViewPager mScrollViewPager;
    @BindView(R.id.business_scroll_dot_layout)
    LinearLayout mDotLayout;

    @BindView(R.id.business_bg_img)
    ImageView mBusinessLogoImg;
    @BindView(R.id.business_name)
    TextView mBusinessNameTV;
    @BindView(R.id.business_address)
    TextView mBusinessAddress;
//    @BindView(R.id.business_tel)
//    TextView mBusinessTelephone;

    //    @BindView(R.id.business_member_container)
//    RelativeLayout mBusinessMemberContainer;
//    @BindView(R.id.business_member_vip_level)
//    TextView mBusinessMemberVipLevel;
//    @BindView(R.id.business_member_card_amount)
//    TextView mBusinessMemberCardAmount;
//    @BindView(R.id.business_member_credit)
//    TextView mBusinessMemberCredit;
//    @BindView(R.id.business_cardlist_recyclerView)
//    PullToRefreshRecyclerView mCardListRecyclerView;
//    @BindView(R.id.business_article_listview)
//    ListView mBusinessArticleListView;
    @BindView(R.id.business_tab_layout)
    WaytoTabLayout mBusinessTabLayout;

//    List<ArticleItemEntity> mBusinessArticleList;

    private String mBusinessNo;

    //    private BusinessPresenter mBusinessPresenter;
    private BussinessRepo mBussinessRepo;
    private static final int BUSINESS_SCROLL_IMAGE = 1001;
    private List<ImageView> dots = new ArrayList<ImageView>();
//    private ArrayList<ArticleItemEntity> mArticleItemList;
//    private CardAdapter mCardAdapter;

    private String[] mTabNames;
    private String[] mTabKeys = new String[]{"mdhd", "sjxq", "hyzx"};
    private List<BaseFragment> mFragments = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_business);
        mBusinessNo = getIntent().getStringExtra("businessNo");

//        setToolbarRightImage(R.mipmap.icon_add);
//        setSwipeEnabled(false);
        ButterKnife.bind(this);
        mBussinessRepo = BussinessRepo.getInstance();
        initTab();

//        initCardListRecyclerView();
//        initScrollImages(urls);
//        setScrollViewListener();

        requestBusinessDetail();
//        requestBusinessArticles();
    }

    /**
     * 初始化CardListRecyclerView
     */
    private void initCardListRecyclerView() {
//        mCardAdapter = new CardAdapter(this);
//        mCardListRecyclerView.setPullToRefreshListener(this);
//        mCardListRecyclerView.setRecyclerViewAdapter(mCardAdapter);
//        mCardListRecyclerView.startUpRefresh();
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

    private void requestBusinessArticles() {
//        mBusinessPresenter.requestBusinessArticles();
    }

//    @Override
//    public void setBusinessCardList(ArrayList<CardItemEntity> cardItems) {
//        mCardAdapter.addItems(cardItems);
//        mCardListRecyclerView.closeDownRefresh();
//        mCardListRecyclerView.onLoadMoreFinish();
//    }
//
//    @Override
//    public void setBusinessArticles(ArrayList<ArticleItemEntity> articleItems) {
//        if (articleItems == null || articleItems.size() == 0) {
//            return;
//        }
//        mBusinessArticleList = articleItems;
//        ArticleListAdapter adapter = new ArticleListAdapter(this);
//        adapter.setArticleItemList(articleItems);
//        mBusinessArticleListView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        IViewUtil.setListViewHeightBasedOnChildren(mBusinessArticleListView);
//
//        mBusinessArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Bundle bundle = new Bundle();
//                ArticleItemEntity articleItem = mBusinessArticleList.get(i);
//                bundle.putString("id", articleItem.getArticleId());
//                bundle.putString("businessNo", articleItem.getBusinessNO());
//                ISkipActivityUtil.startIntent(BusinessActivity.this, ArticleActivity.class, bundle);
//            }
//        });
//    }
//
//    @Override
//    public String getBusinessNo() {
//        return mBusinessNo;
//    }
//
//    @Override
//    public String getUserNo() {
//        return DataApplication.getInstance().getUserInfoEntity().getUserNo();
//    }

    /**
     * 初始化ScrollImage
     *
     * @param urls
     */
    private void initScrollImages(String[] urls) {

//        ScrollPagerAdapter adapter = new ScrollPagerAdapter(this, mArticleItemList);
//        mScrollViewPager.setAdapter(adapter);
//        for (int i = 0; i < 4; i++) {
//            ImageView img = new ImageView(this);
//            if (i == 0) {
//                img.setImageResource(R.drawable.dot_focus);
//            } else {
//                img.setImageResource(R.drawable.dot_normal);
//            }
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
//            params.setMargins(5, 0, 5, 30);
//
//            // 加载到布局容器
//            mDotLayout.addView(img, params);
//            dots.add(img);
//        }
//        mHandler.sendEmptyMessageDelayed(BUSINESS_SCROLL_IMAGE, Constant.FIVE_SECONDES);
    }

    /**
     * 设置轮播ViewPager监听
     */
    private void setScrollViewListener() {
//        mScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////                ILog.v(TAG, "onPageScrolled position = " + position);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                int size = dots.size();
////                ILog.d(TAG, "onPageSelected position = " + position);
//                for (int i = 0; i < size; i++) {
//                    ImageView img = dots.get(i);
//                    if (i == position % size) {
////                        img.setImageResource(R.drawable.dot_focus);
//                    } else {
////                        img.setImageResource(R.drawable.dot_normal);
//                    }
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
////                ILog.v(TAG, "onPageScrollStateChanged state = " + state);
//            }
//        });
    }

//    @OnClick({R.id.business_back, R.id.business_become_member})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.business_back:
//                onBackPressed();
//                break;
//            case R.id.business_become_member:
////                ISkipActivityUtil.startIntent(this, MemberCenterActivity.class);
//                break;
//        }
//    }

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
        BusinessDetailEntity entity = data;
        if (entity == null) {
            return;
        }
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
        if (businessDetailEntity==null){
            return;
        }
        setToolbarTitle(TextUtils.isEmpty(businessDetailEntity.getBusinessName())?"商家详情":businessDetailEntity.getBusinessName());

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
                /*商家Logo*/
        Glide.with(this).load(BuildConfig.DOMAIN + businessDetailEntity.getBusinessimages())
               .placeholder(R.mipmap.default_image)
                .error(R.mipmap.main_img_defaultpic_small)
                .into(mBusinessLogoImg);
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
}
