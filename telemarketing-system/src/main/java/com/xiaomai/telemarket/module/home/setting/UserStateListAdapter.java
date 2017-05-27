package com.xiaomai.telemarket.module.home.setting;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    private Drawable checkDrawable;
    public UserStateListAdapter(Context context) {
        super(context);
        checkDrawable = context.getResources().getDrawable(R.mipmap.ic_select);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_user_state_list, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.ctvUserState.setText(mLists.get(position).getName());
        if (mLists.get(position).isSelect()) {
            viewHolder.ctvUserState.setCheckMarkDrawable(checkDrawable);
        } else {
            viewHolder.ctvUserState.setCheckMarkDrawable(null);
        }
        final UserStateEntity entity = mLists.get(position);
        viewHolder.ctvUserState.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (entity != null) {
                    if (listener!=null) {
                        listener.onItemClick(view,entity,position);
                    }
                }
            }
        });
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
