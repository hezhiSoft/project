package com.jinggan.library.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * @author zls
 * @version V1.0
 * @Package com.yunwei.frame.utils
 * @Description:
 * @date 2017/2/8
 */

public class ISkipFragmentUtil {
    public static void goToReplaceFragment(FragmentManager fragmentManager, Fragment fragment, int container){
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
    }

    public static void goToReplaceFragment(FragmentManager fragmentManager, Fragment fragment, int container, Bundle bundle){
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .addToBackStack(fragment.getTag())
                .commitAllowingStateLoss();
    }
}
