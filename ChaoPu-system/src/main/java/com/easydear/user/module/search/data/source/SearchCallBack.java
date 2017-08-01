package com.easydear.user.module.search.data.source;

import com.easydear.user.module.search.data.SearchEntity;

import java.util.List;

/**
 * Created by LJH on 2017/7/8.
 */

public interface SearchCallBack {

    void onHotSearchSuccess(List<SearchEntity> list);

    void onHotSearchFailure(String msg);

    void onHistorySearchSuccess(List<SearchEntity> list);

    void onHistorySearchFailure(String msg);

    void onMatchedKeySuccess(List<SearchEntity> list);

    void onMatchedKeyFailure(String msg);

}
