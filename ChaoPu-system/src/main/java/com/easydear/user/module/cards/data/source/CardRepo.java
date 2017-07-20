package com.easydear.user.module.cards.data.source;

import com.easydear.user.DataApplication;
import com.easydear.user.api.ChaoPuRetrofitManamer;
import com.easydear.user.api.ResponseEntity;
import com.easydear.user.api.RetrofitManager;
import com.easydear.user.module.cards.data.CardEntity;
import com.easydear.user.module.cards.data.InterestDetailEntity;
import com.easydear.user.module.cards.data.InterestsEntity;
import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.net.retrofit.RetrofitCallbackV2;
import com.jinggan.library.utils.ILogcat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Query;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/16$ 下午8:24$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CardRepo implements BaseDataSourse {

    private Call<ResponseEntity<List<CardEntity>>> cardsCall;
    private Call<ResponseEntity<List<InterestsEntity>>> interestsCall;
    private Call<ResponseEntity<InterestDetailEntity>> interestDetailCall;

    private static CardRepo instance;

    public static CardRepo getInstance() {
        if (instance == null) {
            instance = new CardRepo();
        }
        return instance;
    }

    public void queryCards(int pageSize, String keywords, final RemetoRepoCallbackV2<List<CardEntity>> callback) {
        callback.onReqStart();
        String url = "neweasydear-app/card/listByKey?pageSize=" + pageSize + "&pageCount=10&keywords=" + keywords+"&userNo="+ DataApplication.getInstance().getUserInfoEntity().getUserNo();
        cardsCall = RetrofitManager.getInstance().getService().queryCards(url);
        cardsCall.enqueue(new RetrofitCallbackV2<ResponseEntity<List<CardEntity>>>() {
            @Override
            public void onSuccess(ResponseEntity<List<CardEntity>> data) {
                if (data.getCode() == 200) {
                    callback.onSuccess(data.getData());
                } else {
                    callback.onFailure(data.getCode(), data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    public void queryInterests(int pageSize, int pageCount, final RemetoRepoCallbackV2<List<InterestsEntity>> callback){
        interestsCall= ChaoPuRetrofitManamer.getAPIService().queryInterests(DataApplication.getInstance().getUserInfoEntity().getUserNo(),pageSize,pageCount);
        interestsCall.enqueue(new RetrofitCallbackV2<ResponseEntity<List<InterestsEntity>>>() {
            @Override
            public void onSuccess(ResponseEntity<List<InterestsEntity>> data) {
                if (data.getCode()==200){
                    callback.onSuccess(data.getData());
                }else {
                    callback.onFailure(data.getCode(),data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code,msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    public void queryInterestDetail(String cardNo, final RemetoRepoCallbackV2<InterestDetailEntity> callback){
        interestDetailCall= ChaoPuRetrofitManamer.getAPIService().queryInterestDetail(cardNo);
        interestDetailCall.enqueue(new RetrofitCallbackV2<ResponseEntity<InterestDetailEntity>>() {
            @Override
            public void onSuccess(ResponseEntity<InterestDetailEntity> data) {
                if (data.getCode()==200){
                    callback.onSuccess(data.getData());
                }else {
                    callback.onFailure(data.getCode(),data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code,msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    @Override
    public void cancelRequest() {
        if (cardsCall != null && !cardsCall.isCanceled()) {
            cardsCall.cancel();
        }
    }
}
