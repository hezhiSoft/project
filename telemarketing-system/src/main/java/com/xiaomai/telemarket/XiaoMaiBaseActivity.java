package com.xiaomai.telemarket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.module.home.setting.SettingEditActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @description
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 07/06/2017 00:44 AM
 **/
public class XiaoMaiBaseActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainUserEvent(EventBusValues values) {
        if (values.getWhat()==0x666666) {
            DialogFactory.showMsgDialog(this, "提示", "当前名单源已空，是否切换名单源?", "是", "否", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(SettingEditActivity.EXTRA_TAG, SettingEditActivity.TAG_DIALING);
                    ISkipActivityUtil.startIntent(XiaoMaiBaseActivity.this, SettingEditActivity.class, bundle);
                }
            }, null);
        }
    }
}
