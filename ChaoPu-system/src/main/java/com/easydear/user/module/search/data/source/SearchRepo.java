package com.easydear.user.module.search.data.source;

import com.easydear.user.api.ChaoPuRetrofitManamer;
import com.easydear.user.api.ResponseEntity;
import com.easydear.user.module.search.SearchActivity;
import com.easydear.user.module.search.data.SearchEntity;
import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.net.retrofit.RetrofitCallbackV2;

import java.util.List;

import retrofit2.Call;

/**
 * Created by LJH on 2017/7/8.
 */

public class SearchRepo implements BaseDataSourse {

    private Call<ResponseEntity<List<SearchEntity>>> searchCall;

    private static SearchRepo instance;

    public static SearchRepo getInstance() {
        if (instance == null) {
            instance = new SearchRepo();
        }
        return instance;
    }

    public void requestHotSearch(final RemetoRepoCallbackV2<List<SearchEntity>> callback, final SearchCallBack searchCB) {
        callback.onReqStart();
        final Call<ResponseEntity<List<SearchEntity>>> call = ChaoPuRetrofitManamer.getService().queryHotSearch();
        call.enqueue(new RetrofitCallbackV2<ResponseEntity<List<SearchEntity>>>() {
            @Override
            public void onSuccess(ResponseEntity<List<SearchEntity>> data) {
                if (data.getCode() == 200) {
                    searchCB.onHotSearchSuccess(data.getData());
                } else {
                    callback.onFailure(data.getCode(), data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code, msg);
                searchCB.onHotSearchFailure(msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    public void requestKeyMatch(String userNo, String key, final RemetoRepoCallbackV2<List<SearchEntity>> callback, final SearchCallBack searchCB) {
        callback.onReqStart();
        final Call<ResponseEntity<List<SearchEntity>>> call = ChaoPuRetrofitManamer.getService().queryKeyMatch(userNo, key);
        call.enqueue(new RetrofitCallbackV2<ResponseEntity<List<SearchEntity>>>() {
            @Override
            public void onSuccess(ResponseEntity<List<SearchEntity>> data) {
                if (data.getCode() == 200) {
                    searchCB.onMatchedKeySuccess(data.getData());
                } else {
                    callback.onFailure(data.getCode(), data.getMessage());
                    searchCB.onMatchedKeyFailure(data.getMessage());
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

    @Override
    public void cancelRequest() {
        if (searchCall != null && !searchCall.isCanceled()) {
            searchCall.cancel();
        }
    }
}
