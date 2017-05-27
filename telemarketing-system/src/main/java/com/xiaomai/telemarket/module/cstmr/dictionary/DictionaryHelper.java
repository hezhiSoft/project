package com.xiaomai.telemarket.module.cstmr.dictionary;

import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/26 10:12
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DictionaryHelper {
    public static final String DIC_BANK_KEY = "DIC_BANK";
    public static final String DIC_DEPT_KEY = "DIC_DEPT";
    public static final String DIC_RepaymentMode_KEY = "DIC_RepaymentMode";
    public static final String DIC_SZAREA_KEY = "DIC_SZAREA";
    public static final String DIC_INSURANCECOMPANY_KEY = "INSURANCECOMPANY";
    public static final String DIC_MaritalStatus_KEY = "MaritalStatus";
    public static final String DIC_PayCostType_KEY = "PayCostType";
    public static final String DIC_LandUse_KEY = "LandUse";
    public static final String DIC_Sex_KEY = "Sex";
    public static final String DIC_InterestedStatus_KEY = "InterestedStatus";
    public static final String DIC_LoanType_KEY = "LoanType";
    public static final String DIC_FollowType_KEY = "FollowType";

    public static void requestDictionary() {
        DictionaryPresenter.getInstance().queryBank(DataApplication.getInstance());
        DictionaryPresenter.getInstance().queryDept(DataApplication.getInstance());
        DictionaryPresenter.getInstance().queryRepaymentMode(DataApplication.getInstance());
        DictionaryPresenter.getInstance().querySZAREA(DataApplication.getInstance());
        DictionaryPresenter.getInstance().queryINSURANCECOMPANY(DataApplication.getInstance());
        DictionaryPresenter.getInstance().queryMaritalStatus(DataApplication.getInstance());
        DictionaryPresenter.getInstance().queryPayCostType(DataApplication.getInstance());
        DictionaryPresenter.getInstance().queryLandUse(DataApplication.getInstance());
        DictionaryPresenter.getInstance().querySex(DataApplication.getInstance());
        DictionaryPresenter.getInstance().queryInterestedStatus(DataApplication.getInstance());
        DictionaryPresenter.getInstance().queryLoanType(DataApplication.getInstance());
        DictionaryPresenter.getInstance().queryFollowType(DataApplication.getInstance());
    }

    /**
     * 获取机构/银行
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 11:48
     */
    public static List<DictionaryEntity> getBankData() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_BANK_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析机构银行
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 11:52
     */
    public static String ParseBank(String code) {
        List<DictionaryEntity> list = getBankData();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }

    /**
     * 负债
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:07
     */
    public static List<DictionaryEntity> getDeptData() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_DEPT_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析负债类型
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:08
     */
    public static String ParseDept(String code) {
        List<DictionaryEntity> list = getDeptData();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }

    /**
     * 还款方式
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:07
     */
    public static List<DictionaryEntity> getRepaymentMode() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_RepaymentMode_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析还款方式
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:48
     */
    public static String ParseRepaymentMode(String code) {
        List<DictionaryEntity> list = getRepaymentMode();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }

    /**
     * 区域
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:07
     */
    public static List<DictionaryEntity> getSZAREA() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_SZAREA_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析区域
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:48
     */
    public static String ParseSZAREA(String code) {
        List<DictionaryEntity> list = getSZAREA();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }

    /**
     * 保险公司
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:07
     */
    public static List<DictionaryEntity> getINSURANCECOMPANY() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_INSURANCECOMPANY_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析保险公司
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:48
     */
    public static String ParseINSURANCECOMPANY(String code) {
        List<DictionaryEntity> list = getINSURANCECOMPANY();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }

    /**
     * 婚姻状态
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:07
     */
    public static List<DictionaryEntity> getMaritalStatus() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_MaritalStatus_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析婚姻状态
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:48
     */
    public static String ParseMaritalStatus(String code) {
        List<DictionaryEntity> list = getMaritalStatus();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }


    /**
     * 婚缴费方式
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:07
     */
    public static List<DictionaryEntity> getPayCostType() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_PayCostType_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析缴费方式
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:48
     */
    public static String ParsePayCostType(String code) {
        List<DictionaryEntity> list = getPayCostType();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }


    /**
     * 婚缴费方式
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:07
     */
    public static List<DictionaryEntity> getLandUse() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_LandUse_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析缴费方式
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:48
     */
    public static String ParseLandUse(String code) {
        List<DictionaryEntity> list = getLandUse();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }

    /**
     * 性别
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:07
     */
    public static List<DictionaryEntity> getSex() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_Sex_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析性别
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:48
     */
    public static String ParseSex(String code) {
        List<DictionaryEntity> list = getSex();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }

    /**
     * 意向状态
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:07
     */
    public static List<DictionaryEntity> getInterestedStatus() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_InterestedStatus_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析意向状态
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:48
     */
    public static String ParseInterestedStatus(String code) {
        List<DictionaryEntity> list = getInterestedStatus();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }

    /**
     * 贷款类型
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:07
     */
    public static List<DictionaryEntity> getLoanType() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_LoanType_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析贷款类型
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:48
     */
    public static String ParseLoanType(String code) {
        List<DictionaryEntity> list = getLoanType();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }


    /**
     * 跟进方式
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 12:07
     */
    public static List<DictionaryEntity> getFollowType() {
        String info = ISharedPreferencesUtils.getValue(DataApplication.getInstance(), DictionaryHelper.DIC_FollowType_KEY, "").toString();
        List<DictionaryEntity> list = new Gson().fromJson(info, new TypeToken<List<DictionaryEntity>>() {
        }.getType());
        return list;
    }

    /**
     * 解析跟进方式
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 13:48
     */
    public static String ParseFollowType(String code) {
        List<DictionaryEntity> list = getFollowType();
        if (list == null || list.size() <= 0) {
            return "无";
        }
        for (DictionaryEntity entity : list) {
            if (code.equals(entity.getCode())) {
                return entity.getName();
            }
        }
        return "无";
    }


    /**
     *选择Dialog
     * 
     *author: hezhiWu
     *created at 2017/5/26 20:50
     */
    public static void showSelectDialog(Context context, final TextView textView, String content) {
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        View rootView = LayoutInflater.from(context).inflate(R.layout.select_layout, null);
        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.Dialog_RadioGroup);
        RadioButton isButton= ButterKnife.findById(rootView,R.id.Dialog_is);
        RadioButton noButton=ButterKnife.findById(rootView,R.id.Dialog_no);
        rootView.findViewById(R.id.Select_close_ImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if ("是".equals(content)){
            isButton.setChecked(true);
        }else if ("否".equals(content)){
            noButton.setChecked(true);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.Dialog_is) {
                    textView.setText("是");
                } else if (checkedId == R.id.Dialog_no) {
                    textView.setText("否");
                }
                dialog.dismiss();
            }
        });
        dialog.setContentView(rootView);
        dialog.show();
    }
}
