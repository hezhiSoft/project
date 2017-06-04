package com.xiaomai.telemarket.module.home.dial.data.source.local;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;

import static com.jinggan.library.utils.ISharedPreferencesUtils.setValue;

/**
 * @description 本地客户资源
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 04/06/2017 12:20 PM
 **/
public class CustomerLocalDataSource {

    private CusrometListEntity customerEntity;

    private static CustomerLocalDataSource INSTANCE;

    public static CustomerLocalDataSource getInstance(){
        if (INSTANCE == null) {
            synchronized (CustomerLocalDataSource.class){
                if (INSTANCE == null) {
                    INSTANCE = new CustomerLocalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    private CustomerLocalDataSource() {

    }

    /**
     * 设置上一个用户信息
     */
    public void setPreCustomer(CusrometListEntity entity) {
        if (entity != null) {
            String entityInfo = new Gson().toJson(entity);
            setValue(DataApplication.getInstance().getApplicationContext(), Constant.PRE_CUSTOMER_KEY, entityInfo);
        } else {
            setValue(DataApplication.getInstance().getApplicationContext(), Constant.PRE_CUSTOMER_KEY, "");
        }
        customerEntity = entity;
    }

    /**
     * 获取上一个用户信息
     */
    public CusrometListEntity getPreCustomer() {
        if (customerEntity == null) {
            try {
                String entityInfo= ISharedPreferencesUtils.getValue(DataApplication.getInstance().getApplicationContext(), Constant.PRE_CUSTOMER_KEY, "").toString();
                if (!TextUtils.isEmpty(entityInfo)) {
                    customerEntity = new Gson().fromJson(entityInfo, CusrometListEntity.class);
                }
            } catch (Exception e) {
                customerEntity = null;
            }
        }
        return customerEntity;
    }
}
