package com.easydear.user.api;

import com.easydear.user.module.business.data.BusinessEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-06-11
 * Time: 01:45
 * Version:1.0
 */

public interface APIService {


    /**
     * 查询商家列表
     *
     * @param url
     * @return
     */
    @POST
    Call<ResponeEntity<List<BusinessEntity>>> queryBusiness(@Url String url);
}
