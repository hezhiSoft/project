package com.xiaomai.telemarket.module.cstmr.dictionary;

import android.content.Context;

import com.google.gson.Gson;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import java.util.List;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/26 10:10
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DictionaryPresenter {

    private static DictionaryPresenter instance;

    public static DictionaryPresenter getInstance() {
        if (instance == null) {
            instance = new DictionaryPresenter();
        }
        return instance;
    }

    /**
     * 银行
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 10:13
     */
    public void queryBank(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("BANK", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_BANK_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 负债类型
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:06
     */
    public void queryDept(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("DeptType", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_DEPT_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 还款方式
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:45
     */
    public void queryRepaymentMode(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("RepaymentMode", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_RepaymentMode_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }


    /**
     * 区域
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:45
     */
    public void querySZAREA(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("SZAREA", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_SZAREA_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }


    /**
     * 保险公司
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:45
     */
    public void queryINSURANCECOMPANY(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("INSURANCECOMPANY", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_INSURANCECOMPANY_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }


    /**
     * 婚姻状态
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:45
     */
    public void queryMaritalStatus(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("MaritalStatus", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_MaritalStatus_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }
    /**
     * 缴费方式
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:45
     */
    public void queryPayCostType(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("PayCostType", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_PayCostType_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }


    /**
     * 土地用地
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:45
     */
    public void queryLandUse(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("LandUse", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_LandUse_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 性别
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:45
     */
    public void querySex(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("Sex", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_Sex_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }


    /**
     * 意向状态
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:45
     */
    public void queryInterestedStatus(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("InterestedStatus", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_InterestedStatus_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 贷款类型
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:45
     */
    public void queryLoanType(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("LoanType", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_LoanType_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 跟进方式
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:45
     */
    public void queryFollowType(final Context context) {
        CusrometRemoteRepo.getInstance().queryDictionary("FollowType", new RemetoRepoCallback<List<DictionaryEntity>>() {
            @Override
            public void onSuccess(List<DictionaryEntity> data) {
                ISharedPreferencesUtils.setValue(context, DictionaryHelper.DIC_FollowType_KEY, new Gson().toJson(data));
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFinish() {

            }
        });
    }

}
