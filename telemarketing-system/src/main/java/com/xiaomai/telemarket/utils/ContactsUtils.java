package com.xiaomai.telemarket.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.support.v4.app.ActivityCompat;

/**
 * @description 联系人工具
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 17/06/2017 7:03 PM
 **/
public class ContactsUtils {

    public static ContactsUtils INSTANCE;
    private ContentResolver resolver;
    private MyContentObserver myContentObserver;
    private MyContentObserver myCallObserver;
    public static final String CONTACT_NAME_SUFFIX = "-麦麦";

    public static ContactsUtils getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (ContactsUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ContactsUtils();
                }
            }
        }
        return INSTANCE;
    }

    private ContactsUtils() {
    }


    /**
     * 保存客户信息到通讯录
     * @param context
     * @param name 姓名
     * @param phoneNumber 电话
     * {@link "http://blog.csdn.net/zhangphil/article/details/50633727"}
     * @return
     */
    public void saveCustomerToContacts(Context context, String name, String phoneNumber,Handler handler) {
        if (context == null) {
            return ;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            return ;
        }
        if (resolver==null) {
            resolver = context.getContentResolver();
        }
        myContentObserver = new MyContentObserver(handler);
        resolver.registerContentObserver(ContactsContract.RawContacts.CONTENT_URI,true,myContentObserver);
        // 创建一个空的ContentValues  
        ContentValues values = new ContentValues();

        // 向RawContacts.CONTENT_URI空值插入，  
        // 先获取Android系统返回的rawContactId  
        // 后面要基于此id插入值  
        Uri rawContactUri = resolver.insert(RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        // 内容类型  
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        // 联系人名字  
        values.put(StructuredName.GIVEN_NAME, name+CONTACT_NAME_SUFFIX);
        // 向联系人URI添加联系人名字  
        resolver.insert(Data.CONTENT_URI, values);
        values.clear();

        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码  
        values.put(Phone.NUMBER, phoneNumber);
        // 电话类型  
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        // 向联系人电话号码URI添加电话号码  
        resolver.insert(Data.CONTENT_URI, values);
        values.clear();

//        values.put(Data.RAW_CONTACT_ID, rawContactId);
//        values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
//        // 联系人的Email地址
//        values.put(Email.DATA, "zhangphil@xxx.com");
//        // 电子邮件的类型
//        values.put(Email.TYPE, Email.TYPE_WORK);
        // 向联系人Email URI添加Email数据  
//        resolver.insert(Data.CONTENT_URI, values);
    }

    public void deleteContact(Context context,String name) {
        //根据姓名求id
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;//Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{Data._ID},"display_name=?", new String[]{name+CONTACT_NAME_SUFFIX}, null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            //根据id删除data中的相应数据
            resolver.delete(uri, "display_name=?", new String[]{name+CONTACT_NAME_SUFFIX});
            uri = Uri.parse("content://com.android.contacts/data");
            resolver.delete(uri, "raw_contact_id=?", new String[]{id+""});
        }

    }

    /**
     * 删除系统通话记录
     * @param context
     * @param tellNumber
     * @return
     */
    public void deleteCallLog(Context context, String tellNumber) {
        if (context == null) {
            return ;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return ;
        }
        if (resolver == null) {
            resolver = context.getContentResolver();
        }
//        myCallObserver = new MyContentObserver(handler);
//        resolver.registerContentObserver(CallLog.Calls.CONTENT_URI, true, myCallObserver);
        resolver.delete(CallLog.Calls.CONTENT_URI, "number=?", new String[]{tellNumber});
    }

    /**
     * 调用完保存联系人方法后要调用该方法
     */
    public void unregisterContentObserver() {
        if (resolver!=null) {
            if (myContentObserver!=null) {
                resolver.unregisterContentObserver(myContentObserver);
            }
            if (myCallObserver!=null) {
                resolver.unregisterContentObserver(myCallObserver);
            }
        }
    }

    /**
     * ContentObserver 上下文观察者
     * resolver.registerContentObserver(CallLog.Calls.CONTENT_URI, true, new CallLogObserver(incomingNumber, new Handler()));
     */
    public class MyContentObserver extends ContentObserver {

        private Handler mHandler;

        public MyContentObserver(Handler handler) {
            super(handler);
            this.mHandler = handler;
        }

        /**
         * 数据发生改变
         *
         * @param selfChange
         */
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            if (mHandler != null) {
                mHandler.sendEmptyMessage(0);
            }
            ContactsUtils.getINSTANCE().unregisterContentObserver();
        }
    }

}
