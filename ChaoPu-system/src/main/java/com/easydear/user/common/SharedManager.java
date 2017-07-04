package com.easydear.user.common;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.easydear.user.R;

/**
 * $desc$
 *
 * @author hezhiWu <wuhezhi007@gmail.com>
 * @version V1.0
 *          created at $date$ $time$
 *          <p>
 *          Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class SharedManager {

    private static SharedManager instance;

    public static SharedManager getInstance() {
        if (instance == null) {
            instance = new SharedManager();
        }
        return instance;
    }

    /**
     * 显示分享Dialog
     * <p>
     * author: hezhiWu
     * created at 2017/7/4 16:13
     */
    public void showShared(Context context) {
        final BottomSheetDialog dialog = new BottomSheetDialog(context);

        View view = LayoutInflater.from(context).inflate(R.layout.shared_layout, null);
        view.findViewById(R.id.share_close_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.share_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view.findViewById(R.id.share_wechatmoments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view.findViewById(R.id.share_sinaweibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view.findViewById(R.id.share_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view.findViewById(R.id.share_qzone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        dialog.setContentView(view);
        dialog.show();
    }
}
