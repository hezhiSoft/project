package com.xiaomai.telemarket.module.cstmr.fragment.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;
import com.xiaomai.telemarket.module.cstmr.fragment.follow.FollowActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/16$ 下午10:38$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CusrometInfoShowFragment extends BaseFragment {

    @BindView(R.id.CusInfo_CustomerName)
    TextView CusInfoCustomerName;
    @BindView(R.id.CusInfo_CustomerTel)
    TextView CusInfoCustomerTel;
    @BindView(R.id.CusInfo_IsSZHukou)
    TextView CusInfoIsSZHukou;
    @BindView(R.id.CusInfo_IsEmptyTel)
    TextView CusInfoIsEmptyTel;
    @BindView(R.id.CusInfo_Sex)
    TextView CusInfoSex;
    @BindView(R.id.CusInfo_MaritalStatus)
    TextView CusInfoMaritalStatus;
    @BindView(R.id.CusInfo_Payroll)
    TextView CusInfoPayroll;
    @BindView(R.id.CusInfo_BankFlow)
    TextView CusInfoBankFlow;
    @BindView(R.id.CusInfo_AccumulationFundAccount)
    TextView CusInfoAccumulationFundAccount;
    @BindView(R.id.CusInfo_SocialSecurityAccount)
    TextView CusInfoSocialSecurityAccount;
    @BindView(R.id.CusInfo_Remark)
    TextView CusInfoRemark;
    @BindView(R.id.CusInfo_YiXiang)
    TextView YiXiangStatus;
    @BindView(R.id.CustomerInfo_Mark_Empty)
    TextView CustomerInfoMarkEmpty;
    @BindView(R.id.CustomerInfo_Set_Intent)
    TextView CustomerInfoSetIntent;
    Unbinder unbinder;

    private CusrometListEntity entity;
    private boolean isSkipToFollow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        entity = (CusrometListEntity) getArguments().getSerializable("entity");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cusromet_info, null);
        unbinder = ButterKnife.bind(this, rootView);
        initUI(entity);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initUI(CusrometListEntity entity) {
        if (entity == null) {
            return;
        }
        CusInfoCustomerName.setText(entity.getCustomerName());

        CusInfoCustomerTel.setText(entity.getCustomerTel());
        CusInfoIsSZHukou.setText(entity.getIsSZHukou() == 0 ? "否" : "是");
        CusInfoIsEmptyTel.setText(entity.getIsEmpty() == Constant.ISEmpty.YesEmpty.getValue() ? "是" : "否");
        CusInfoSex.setText(DictionaryHelper.ParseSex(entity.getSex() + ""));
        CusInfoMaritalStatus.setText(DictionaryHelper.ParseMaritalStatus(entity.getMaritalStatus() + ""));
        CusInfoPayroll.setText(entity.getWage() + "");
        CusInfoBankFlow.setText(entity.getAccountWater() + "");
        CusInfoAccumulationFundAccount.setText(entity.getAccumulationFundAccount() + "");
        CusInfoSocialSecurityAccount.setText(entity.getSocialSecurityAccount() + "");
        CusInfoRemark.setText(entity.getRemark());
        /*意向状态*/
        int status = entity.getInterestedStatus();
        if (status == Constant.Description.YesInterested.getValue()) {
            YiXiangStatus.setText("有意向");
        } else if (status == Constant.Description.NoInterested.getValue()) {
            YiXiangStatus.setText("无意向");
        }
    }

    @OnClick({R.id.CustomerInfo_Mark_Empty, R.id.CustomerInfo_Set_Intent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.CustomerInfo_Mark_Empty:
                //标记空号
                if (entity != null) {
                    showProgressDlg("操作中...");
                    CusrometRemoteRepo.getInstance().setEmptyTel(entity.getID(), new RemetoRepoCallback<Void>() {
                        @Override
                        public void onSuccess(Void data) {
                            showToast("设置成功！");
                            // TODO: 18/06/2017 刷新ui
                            entity.setIsEmpty(1);
                            initUI(entity);
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            showToast(msg);
                        }

                        @Override
                        public void onThrowable(Throwable t) {
                            showToast("服务器异常！");
                        }

                        @Override
                        public void onUnauthorized() {
                            showToast("服务器异常！");
                        }

                        @Override
                        public void onFinish() {
                            dismissProgressDlg();
                        }
                    });
                }
                break;
            case R.id.CustomerInfo_Set_Intent:
                //设置意向
                if (entity != null) {
                    DialogFactory.showMsgDialog(getContext(), "设置提示", "是否设置下次跟进时间?", "设置", "提交", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isSkipToFollow = true;
                            setInterested();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isSkipToFollow = false;
                            setInterested();
                        }
                    });
                }
                break;
        }
    }

    private void setInterested() {
        showProgressDlg("操作中...");
        CusrometRemoteRepo.getInstance().setInterested(entity.getID(), new RemetoRepoCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                entity.setInterestedStatus(2);
                if (entity.getIsEmpty()==0) {
                    entity.setIsEmpty(Constant.ISEmpty.NoEmpty.getValue());
                }
                initUI(entity);
                if (isSkipToFollow) {
                    FollowActivity.startIntentToQuery(getActivity(), entity, entity.getID());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                showToast(msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                showToast("服务器异常！");
            }

            @Override
            public void onUnauthorized() {
                showToast("服务器异常！");
            }

            @Override
            public void onFinish() {
                dismissProgressDlg();
            }
        });
    }

    @Subscribe
    public void onUpdateUIData(EventBusValues values) {
        if (values.getWhat() == 0x201) {
            entity = (CusrometListEntity) values.getObject();
            initUI(entity);
        }
        if (values.getWhat() == 0x890) {
            initUI((CusrometListEntity) values.getObject());
        }
    }

    /**
     * 客户ID
     * <p>
     * author: hezhiWu
     * created at 2017/5/20 14:09
     */
    public String getCusrometId() {
        if (entity != null) {
            return entity.getID();
        }
        return "";
    }

    public CusrometListEntity getCusromentEntity() {
        return entity;
    }

}
