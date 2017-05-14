package com.jinggan.library.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Notification 工具类
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:21
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class NotificationUtil {

    /**
     * 创建前台Notification
     * <p>
     * author: hezhiWu
     * created at 2017/3/15 10:26
     *
     * @param context
     * @param contentTitle
     * @param contentText
     * @param launcherRes
     * @param cls          跳转目标
     */
    public static Notification CreateForegroundNotification(Context context, String contentTitle, String contentText, int launcherRes, Class<?> cls) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        Intent intent = new Intent(context.getApplicationContext(), cls);
        mBuilder.setContentTitle(contentTitle)
                .setContentText(contentText)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), launcherRes)) // 设置下拉列表中的图标(大图标)
                .setSmallIcon(launcherRes)
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));//设置通知小ICON
        return mBuilder.build();
    }

    /**
     * 发送通知
     * <p>
     * author: hezhiWu
     * created at 2017/3/15 10:09
     *
     * @param context     上下文对象
     * @param appName     App 名字
     * @param content     内容
     * @param launcherRes App Launcher 图标
     * @param ticker
     */
    public static void sendShowMessageNotification(Context context, String appName, int launcherRes, String content, String ticker) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle(appName)
                .setContentText(content)
                .setTicker(ticker) //通知首次出现在通知栏，带上升动画效果的
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), launcherRes)) // 设置下拉列表中的图标(大图标)
                .setSmallIcon(launcherRes);//设置通知小ICON
        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify((int) System.currentTimeMillis(), notification);
    }

}
