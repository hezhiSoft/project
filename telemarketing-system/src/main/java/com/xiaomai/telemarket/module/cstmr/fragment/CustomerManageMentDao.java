package com.xiaomai.telemarket.module.cstmr.fragment;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;

/**
 * @description
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 19/06/2017 12:36 AM
 **/
public interface CustomerManageMentDao<T> extends RemetoRepoCallback{

    void onSuccess(T Data, int total);
}
