package com.xiaomai.telemarket.module.cstmr.fragment.follow;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;
import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;
import com.xiaomai.telemarket.module.cstmr.dictionary.DictionaryHelper;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 13:31
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometFollowAdapter extends BaseRecyclerViewAdapter<FollowEntity> {

    private OnClickItemLisenter listenter;
    private MediaPlayer player;

    public CusrometFollowAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHodler(inflater.inflate(R.layout.item_cusromet_edtails_layout, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        viewHodler.DetailsTileTextView.setText("跟进明细 " + (position + 1));
        viewHodler.DetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHodler.DetailsContentLayout.getVisibility() == View.VISIBLE) {
                    viewHodler.ExpandImageView.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    viewHodler.DetailsContentLayout.setVisibility(View.GONE);
                    viewHodler.lineView.setVisibility(View.VISIBLE);
                    if (player != null && player.isPlaying()) {
                        player.stop();
                    }
                    if (listenter != null) {
                        listenter.onSeleceItemPosition(null);
                    }
                } else {
                    viewHodler.ExpandImageView.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    viewHodler.DetailsContentLayout.setVisibility(View.VISIBLE);
                    viewHodler.lineView.setVisibility(View.GONE);
                    if (listenter != null) {
                        listenter.onSeleceItemPosition(mLists.get(position));
                    }
                }
            }
        });
        if (position == 0) {
            viewHodler.ExpandImageView.setImageResource(R.drawable.ic_expand_less_black_24dp);
            viewHodler.DetailsContentLayout.setVisibility(View.VISIBLE);
            viewHodler.lineView.setVisibility(View.GONE);
            if (listenter != null) {
                listenter.onSeleceItemPosition(mLists.get(position));
            }
        }

        final View infoView = inflater.inflate(R.layout.cusromet_follow_layout, null);
        setDetailsData(infoView, mLists.get(position));
        viewHodler.DetailsContentLayout.addView(infoView);
    }

    class ViewHodler extends RecyclerView.ViewHolder {

        @BindView(R.id.icDetails)
        ImageView icDetails;
        @BindView(R.id.Details_tile_TextView)
        TextView DetailsTileTextView;
        @BindView(R.id.Details_layout)
        RelativeLayout DetailsLayout;
        @BindView(R.id.Details_content_layout)
        RelativeLayout DetailsContentLayout;
        @BindView(R.id.Details_line)
        View lineView;
        @BindView(R.id.Details_expand_iamgeView)
        ImageView ExpandImageView;

        public ViewHodler(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void setDetailsData(View rootView, final FollowEntity entity) {
        if (entity == null) {
            return;
        }
        FormSelectTopTitleView Follow_FollowType = ButterKnife.findById(rootView, R.id.Follow_FollowType);
        FormSelectTopTitleView Follow_FollowDate = ButterKnife.findById(rootView, R.id.Follow_FollowDate);
        FormSelectTopTitleView Follow_InterestedStatus = ButterKnife.findById(rootView, R.id.Follow_InterestedStatus);
        FormSelectTopTitleView Follow_LoanType = ButterKnife.findById(rootView, R.id.Follow_LoanType);
        FormWriteTopTitleView Follow_Amount = ButterKnife.findById(rootView, R.id.Follow_Amount);
        FormSelectTopTitleView Follow_NextFollowType = ButterKnife.findById(rootView, R.id.Follow_NextFollowType);
        FormSelectTopTitleView Follow_NextFollowDate = ButterKnife.findById(rootView, R.id.Follow_NextFollowDate);
        FormWriteTopTitleView Follow_NextFollowTime = ButterKnife.findById(rootView, R.id.Follow_NextFollowTime);
        FormWriteTopTitleView Follow_Remark = ButterKnife.findById(rootView, R.id.Follow_Remark);
        FormWriteTopTitleView Follow_FollowPerson = ButterKnife.findById(rootView, R.id.Follow_FollowPerson);
        ButterKnife.findById(rootView, R.id.Follow_vodie_layout).setVisibility(View.VISIBLE);
        final Button vodieButton = ButterKnife.findById(rootView, R.id.Follow_vodie_Button);
        /*跟进方式*/
        Follow_FollowType.setContentText(DictionaryHelper.ParseFollowType(entity.getFollowType() + "")).setArrowDropVisibility(View.GONE);
        /*跟进时间*/
        Follow_FollowDate.setContentText(entity.getFollowDate().replaceAll("T", " ")).setArrowDropVisibility(View.GONE);
        /*意向状态*/
        Follow_InterestedStatus.setContentText(DictionaryHelper.ParseInterestedStatus(entity.getInterestedStatus() + "")).setArrowDropVisibility(View.GONE);
        /*贷款类型*/
        Follow_LoanType.setContentText(DictionaryHelper.ParseLoanType(entity.getLoanType() + "")).setArrowDropVisibility(View.GONE);
        /*贷款金额*/
        Follow_Amount.setContentText(entity.getAmount() + "").setItemEnabled(false);
        /*下次跟进方式*/
        Follow_NextFollowType.setContentText(DictionaryHelper.ParseFollowType(entity.getNextFollowType() + "")).setArrowDropVisibility(View.GONE);
        /*下次跟进日期*/
        Follow_NextFollowDate.setContentText(entity.getNextFollowDate().replaceAll("T", " ")).setArrowDropVisibility(View.GONE);
        /*下次跟进时间点*/
        Follow_NextFollowTime.setContentText(entity.getNextFollowTime() + "").setItemEnabled(false);
        /*备注*/
        Follow_Remark.setContentText(entity.getRemark()).setItemEnabled(false);
        /*跟进人*/
        Follow_FollowPerson.setItemEnabled(false);
        Follow_FollowPerson.setContentText(DataApplication.getInstance().getUserInfoEntity().getDisplayName());
        if (TextUtils.isEmpty(entity.getFileUrl())) {
            vodieButton.setBackgroundResource(R.drawable.btn_press);
            vodieButton.setClickable(false);
        }
        vodieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(entity.getFileUrl())) {
                    return;
                }
                if (player!=null&&player.isPlaying()) {
                    vodieButton.setText("播放");
                    player.stop();
                } else {
                    try {
                        vodieButton.setText("暂停");
                        player = new MediaPlayer();
                        player.setDataSource(entity.getFileUrl());
                        player.prepare();
                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                vodieButton.setText("播放");
                                player.stop();
                            }
                        });
                        player.start();
                    } catch (IOException e) {
                        vodieButton.setText("播放");
                        e.printStackTrace();
                    }
                }

            }
        });
    }


    public void setListenter(OnClickItemLisenter listenter) {
        this.listenter = listenter;
    }

    public interface OnClickItemLisenter {
        void onSeleceItemPosition(FollowEntity entity);
    }
}
