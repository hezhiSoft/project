package com.xiaomai.telemarket.module.home.dial.data.repo;

import com.jinggan.library.base.BaseDataSourse;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;

import java.util.List;

import retrofit2.Call;

/**
 * @description 获取拨号资源
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 26/05/2017 00:33 AM
 **/
public class CustomerPhoneNumberRemoteRepo implements BaseDataSourse{
    private Call<Responese<List<CusrometListEntity>>> listCusrometListCall;

    private static CustomerPhoneNumberRemoteRepo instance;

    public static CustomerPhoneNumberRemoteRepo getInstance() {
        if (instance == null) {
            instance = new CustomerPhoneNumberRemoteRepo();
        }
        return instance;
    }


    public void requestPhoneNumberFromPublic() {

    }

    public void requestPhoneNumberFromPrivate(){

    }

    @Override
    public void cancelRequest() {
        if (listCusrometListCall!=null&&!listCusrometListCall.isCanceled()) {
            listCusrometListCall.cancel();
        }
    }
}
