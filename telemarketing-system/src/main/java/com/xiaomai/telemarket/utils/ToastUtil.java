package com.xiaomai.telemarket.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @description Toast工具类
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 14/04/2017 3:30 PM
 **/
public class ToastUtil {

    private static Toast toast;

    public static void showToast(Context context, String msg){
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
