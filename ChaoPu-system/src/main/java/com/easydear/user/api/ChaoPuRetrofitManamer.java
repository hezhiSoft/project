package com.easydear.user.api;

import com.easydear.user.BuildConfig;
import com.jinggan.library.net.retrofit.RetrofitManager;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-06-11
 * Time: 01:51
 * Version:1.0
 */

public class ChaoPuRetrofitManamer {

    /**
     * 获取API
     * <p>
     * author: hezhiWu
     * created at 2017/5/20 11:40
     */
    public static APIService getAPIService() {
        return RetrofitManager.getService("http://www.somember.com/",APIService.class);
    }
}
