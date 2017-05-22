package com.xiaomai.telemarket.module.home.setting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.home.setting.data.UserStateEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 用户状态
 * @createtime 20/05/2017 5:20 PM
 **/
public class UserStateListAdapter extends BaseRecyclerViewAdapter<UserStateEntity> {

    public UserStateListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_user_state_list, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.ctvUserState.setText(mLists.get(position).getName());
        viewHolder.ctvUserState.setChecked(mLists.get(position).isSelect());
    }

    /**
     * 设置用户状态
     * @param stateCode
     */
    public void changeUserState(String stateCode) {
        if (mLists!=null&&mLists.size()>0) {
            for (UserStateEntity entity:mLists) {
                entity.setSelect(TextUtils.equals(stateCode+"",entity.getCode()));
            }
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ctv_user_state)
        CheckedTextView ctvUserState;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
