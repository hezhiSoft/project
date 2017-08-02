package com.easydear.user.module.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.DataApplication;
import com.easydear.user.R;
import com.easydear.user.module.search.data.SearchEntity;
import com.easydear.user.module.search.data.source.SearchCallBack;
import com.easydear.user.module.search.data.source.SearchRepo;
import com.easydear.user.util.ISpfUtil;
import com.easydear.user.view.MeasuredGridView;
import com.easydear.user.view.MeasuredListView;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.utils.ILogcat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LJH on 2017/3/4.
 */
public class SearchActivity extends ChaoPuBaseActivity implements RemetoRepoCallbackV2<List<SearchEntity>>, SearchCallBack {

    final String TAG = getClass().getSimpleName();

    @BindView(R.id.search_root_layout)
    RelativeLayout mSearchRootLayout;
    @BindView(R.id.search_editText)
    EditText mSearchEditText;
    @BindView(R.id.search_hot_gridView)
    MeasuredGridView mSearchHotGridView;
    @BindView(R.id.search_history_textView)
    TextView mSearchHistoryTextView;
    @BindView(R.id.search_history_listView)
    MeasuredListView mSearchHistoryListView;

    private View mSearchMatchedView;
    private MeasuredListView mSearchMatchedListView;

    private SearchRepo mSearchRepo;
    private SearchHotAdapter mSearchHotAdapter;
    private String mSearchText;
    private String mMatchText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setToolbarTitle("搜索");
        setToolbarVisibility(View.GONE);
        ButterKnife.bind(this);
        initRepo();
        setEditTextInputChangeListener();
        initSearchHotGridView();
        initSearchHistoryListView();
        initSearchKeyMatchedView();
    }

    private void initRepo() {
        mSearchRepo = SearchRepo.getInstance();
    }

    private void initSearchHotGridView() {
        mSearchHotAdapter = new SearchHotAdapter(this);
        mSearchHotGridView.setAdapter(mSearchHotAdapter);
        requestHotSearch();
    }

    private void initSearchHistoryListView() {
        mSearchRepo.requestHistorySearch(getUserNo(), this, this);

        // ---------服务器无数据，使用本地保存；若服务器有数据，从这里开始删除---------
        String savedHistory = (String) ISpfUtil.getValue("search_history", "");
        ILogcat.v(TAG, "Saved search history = " + savedHistory);
        if (!savedHistory.isEmpty()) {
            final String[] savedHisArr = savedHistory.split(";");
            int len = savedHisArr.length;
            if (len > 0) {
                mSearchHistoryTextView.setVisibility(View.VISIBLE);
                mSearchHistoryListView.setVisibility(View.VISIBLE);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchActivity.this, R.layout.item_search_list_layout, savedHisArr);
                mSearchHistoryListView.setAdapter(arrayAdapter);
                mSearchHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        saveAndSearch(savedHisArr[i]);
                    }
                });
            }
        }
        // ---------服务器无数据，使用本地保存；若服务器有数据，从这里结束删除---------
    }

    private void initSearchKeyMatchedView() {
        mSearchMatchedView = LayoutInflater.from(this).inflate(R.layout.dialog_search_match_layout, null);
        mSearchMatchedListView = (MeasuredListView) mSearchMatchedView.findViewById(R.id.search_matched_listView);
    }

    private void requestHotSearch() {
        mSearchRepo.requestHotSearch(this, this);
    }

    @Override
    public void onReqStart() {

    }

    @Override
    public void onSuccess(List<SearchEntity> entities) {

    }

    @Override
    public void onFailure(int code, String msg) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onHotSearchSuccess(final List<SearchEntity> entities) {
        if (entities == null) {
            return;
        }
        mSearchHotAdapter.setSearchHotList((ArrayList<SearchEntity>) entities);
        mSearchHotAdapter.notifyDataSetChanged();
        mSearchHotGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String newSearch = entities.get(position).getMsg();
                saveAndSearch(newSearch);
            }
        });
    }

    @Override
    public void onHotSearchFailure(String msg) {
        ILogcat.e(TAG, "onHotSearchFailure:: msg = " + msg);
    }

    @Override
    public void onHistorySearchSuccess(List<SearchEntity> entities) {
        if (entities == null || entities.size() <= 0) {
            ILogcat.v(TAG, "onHistorySearchSuccess: entities empty!");
            return;
        }

        int size = entities.size();
        final String[] historyArr = new String[size];
        for (int i = 0; i < size; i++) {
            historyArr[i] = entities.get(i).getMsg();
        }
        mSearchHistoryTextView.setVisibility(View.VISIBLE);
        mSearchHistoryListView.setVisibility(View.VISIBLE);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchActivity.this, R.layout.item_search_list_layout, historyArr);
        mSearchHistoryListView.setAdapter(arrayAdapter);
        mSearchHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                saveAndSearch(historyArr[i]);
            }
        });

    }

    @Override
    public void onHistorySearchFailure(String msg) {
        ILogcat.e(TAG, "onHistorySearchFailure:: msg = " + msg);
    }

    @Override
    public void onMatchedKeySuccess(List<SearchEntity> entities) {
        if (entities == null || entities.size() <= 0) {
            ILogcat.v(TAG, "onGetMatchedKeySuccess: entities empty!");
            return;
        }

        int size = entities.size();
        ILogcat.v(TAG, "onGetMatchedKeySuccess: entities size = " + size);
        final String[] searchMatchedArr = new String[size];
        for (int i = 0; i < size; i++) {
            searchMatchedArr[i] = entities.get(i).getMsg();
        }
        int len = searchMatchedArr.length;
        if (len > 0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchActivity.this, R.layout.item_search_list_layout, searchMatchedArr);
            mSearchMatchedListView.setAdapter(arrayAdapter);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mSearchRootLayout.removeView(mSearchMatchedView);
            mSearchRootLayout.addView(mSearchMatchedView, params);
            mSearchMatchedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ILogcat.v(TAG, "On Searched Key Clicked: " + searchMatchedArr[i]);
                    saveAndSearch(searchMatchedArr[i]);
                }
            });
        }
    }

    @Override
    public void onMatchedKeyFailure(String msg) {
        ILogcat.e(TAG, "onMatchedKeyFailure:: msg = " + msg);
        mSearchRootLayout.removeView(mSearchMatchedView);
    }

    private void saveAndSearch(String search) {
        saveSearchHistory(search);
        startToSearch(search);
    }

    private void saveSearchHistory(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return;
        }
        String savedHistory = (String) ISpfUtil.getValue("search_history", "");
        String newHistory = searchText + ";";
        if (!savedHistory.isEmpty()) {
            String[] savedHisArr = savedHistory.split(";");
            int len = savedHisArr.length;
            for (int i = 0; i < 4 && i < len; i++) {
                if (!savedHisArr[i].equals(searchText)) {
                    newHistory += savedHisArr[i] + ";";
                }
            }
        }
        ISpfUtil.setValue("search_history", newHistory);
        ILogcat.v(TAG, "Save new history = " + newHistory);
    }

    private void startToSearch(String searchText) {
        Intent intent = new Intent();
        intent.putExtra("search_key", searchText);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick({R.id.search_cancel_textView, R.id.search_start_textView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_cancel_textView:
                finish();
                break;
            case R.id.search_start_textView:
                mSearchText = mSearchEditText.getText().toString();
                saveAndSearch(mSearchText);
                break;
            default:
                break;
        }
    }

    private void setEditTextInputChangeListener() {
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ILogcat.v(TAG, "onTextChanged:  charSequence = " + charSequence);
                mMatchText = charSequence.toString();
                if (mMatchText == null || mMatchText.isEmpty()) {
                    mSearchRootLayout.removeView(mSearchMatchedView);
                    return;
                }
                startToMatch(mMatchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void startToMatch(String matchKey) {
        mSearchRepo.requestKeyMatch(getUserNo(), getKey(), this, this);
    }

    public String getUserNo() {
        return DataApplication.getInstance().getUserInfoEntity().getUserNo();
    }

    public String getKey() {
        return mMatchText;
    }

}
