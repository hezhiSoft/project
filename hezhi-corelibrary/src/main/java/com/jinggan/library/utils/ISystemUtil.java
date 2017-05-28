package com.jinggan.library.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.jinggan.library.base.BaseApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.utils
 * @Description:系统工具类
 * @date 2016/11/28 10:44
 */

public class ISystemUtil {

    /**
     * 获取版本名
     *
     * @return 当前应用的版本名
     */
    public static String getVersionName() {
        String versionName = "";
        try {
            PackageManager manager = BaseApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            versionName = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        int versionCode = 0;
        try {
            PackageManager manager = BaseApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：com.baidu.trace.LBSTraceService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(80);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 安装
     *
     * @param context
     */
    public static void installAPK(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 获取IMEI号
     * <p>
     * author: hezhiWu
     * created at 2017/4/21 17:19
     */
    public static String getIMEI(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }

    /**
     * 6.0系统以上动态申请权限
     * @param context
     * @param permissions 要申请的权限数组
     * @return
     */

    /**
     * 6.0系统以上动态申请权限
     * @author yangdu <youngdu29@gmail.com>
     * @createtime 27/05/2017 1:39 AM
     */
    @TargetApi(23)
    public static boolean requestPermissions(Activity context, String ... permissions) {
        List<String> permissionList = new ArrayList<>();
        if (permissions != null) {
            for (String permission:
                    permissions) {
                addPermission(context,permissionList,permission);//检测权限
            }
        }
        if (permissionList.size() > 0) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            }
            context.requestPermissions(permissionList.toArray(new String[permissionList.size()]), 0);//申请未授权权限
            return true;
        }
        return false;
    }

    public static void addPermission(Activity context,List<String> permissionsList, String permission) {
        if (!checkHasSelfPermission(context,permission)) {
            permissionsList.add(permission);
        }
    }

    /**
     * 6.0以上检测权限
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkHasSelfPermission(Activity context,String permission){
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)|| ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @desc 拨打电话
     * @param activity
     * @param teleNumber 号码
     * @param isDirect 是否直接播出
     * @author  <youngdu29@gmail.com>
     * @createtime 14/04/2017 9:54 AM
     */
    public static void makeCall(Activity activity, String teleNumber, boolean isDirect) {
        if (activity!=null&&!TextUtils.isEmpty(teleNumber)) {
            Intent intent = new Intent();
            intent.setAction(isDirect?Intent.ACTION_CALL:Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + teleNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }

    public static void reStartActivity(Context context){
        ActivityManager mAm = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        //获得当前运行的task
        List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo rti : taskList) {
            //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
            if(rti.topActivity.getPackageName().equals(context.getPackageName())) {
                mAm.moveTaskToFront(rti.id,0);
                return;
            }
        }
//        //若没有找到运行的task，用户结束了task或被系统释放，则重新启动mainactivity
//        Intent resultIntent = new Intent(context, MainActivity.class);
//        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        context.startActivity(resultIntent);
    }
}
