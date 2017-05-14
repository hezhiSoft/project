package com.jinggan.library.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity类管工具类
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:19
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class IActivityManage {
    private List<Activity> activities = new ArrayList<>();

    private static IActivityManage mActivityManage;

    public static IActivityManage getInstance() {
        if (mActivityManage == null) {
            mActivityManage = new IActivityManage();
        }
        return mActivityManage;
    }

    public void addActivity(Activity activity) {
        if (activities == null)
            return;
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activities == null)
            return;
        activities.remove(activity);
    }

    public void exit() {
        if (activities == null)
            return;
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.gc();
    }

    public List<Activity> getActivities() {
        return activities;
    }
}
