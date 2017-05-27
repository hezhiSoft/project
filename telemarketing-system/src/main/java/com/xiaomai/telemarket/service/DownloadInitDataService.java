package com.xiaomai.telemarket.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.home.setting.data.UserStateEntity;
import com.xiaomai.telemarket.module.home.setting.data.repo.UserStateRemoteRepo;

import java.util.List;

/**
 * @description 下载初始化数据服务
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 19/05/2017 20:46 PM
 **/
public class DownloadInitDataService extends IntentService {

    public DownloadInitDataService() {
        super("DownloadInitDataService");
    }

    public static void startService(Context context){
        Intent intent = new Intent(context,DownloadInitDataService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        requestUserStateList();
    }

    private void requestUserStateList() {
        UserStateRemoteRepo.getInstance().requestUserStateLists(new RemetoRepoCallback<List<UserStateEntity>>() {
            @Override
            public void onSuccess(List<UserStateEntity> data) {
                if (data != null&&data.size()>0) {
                    try {
                        String stateJson=new Gson().toJson(data);
                        ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.USER_STATE_LIST,stateJson);
                    }catch (Exception e){
                        ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(), "error download initial data");
                    }
                }
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
