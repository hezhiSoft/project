package com.easydear.user.module.account;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.DataApplication;
import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.account.data.UserInfoEntity;
import com.easydear.user.module.account.data.source.AccountRepo;
import com.easydear.user.module.dynamic.DynamicFragment;
import com.google.gson.Gson;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.utils.ISharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 修改昵称
 * <p>
 * author: hezhiWu
 * created at 2017/7/4 16:55
 */
public class UpdateNickActivity extends ChaoPuBaseActivity implements RemetoRepoCallbackV2<Void> {

    @BindView(R.id.Nick_EditView)
    EditText editView;

    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        setToolbarTitle("修改昵称");
        ButterKnife.bind(this);

        if (DataApplication.getInstance().getUserInfoEntity()!=null){
            editView.setText(DataApplication.getInstance().getUserInfoEntity().getNickName());
        }
    }

    @OnClick(R.id.Nick_Submit)
    public void onClick() {
        if (TextUtils.isEmpty(editView.getText().toString())) {
            showToast("昵称不能为空!");
            return;
        }

        AccountRepo.getInstance().updateNickName(editView.getText().toString(), this);

    }

    @Override
    public void onReqStart() {
        dialog = DialogFactory.createLoadingDialog(this, "修改...");
    }

    @Override
    public void onSuccess(Void data) {
        UserInfoEntity infoEntity=DataApplication.getInstance().getUserInfoEntity();
        infoEntity.setNickName(editView.getText().toString());

        ISharedPreferencesUtils.setValue(this, Constant.USERINFO_KEY,new Gson().toJson(infoEntity));

        showToast("修改成功");
        finish();
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(msg);
    }

    @Override
    public void onFinish() {
        DialogFactory.dimissDialog(dialog);
    }
}
