package com.xiaomai.telemarket.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @description 共享参数工具类
 * @author yangdu <youngdu29@gmail.com>
 * @createtime 20/05/2016 5:08 PM
 **/
public class SharedPreferenceUtils {


    private static final String TAG = "SharedPreferenceUtils";

    public static final String SPF_NAME_USERINFO="userInfo";
    public static final String SPF_NAME_SETTING="setting";

    private static SharedPreferenceUtils mInstantPreferenceUtils = new SharedPreferenceUtils();
    private static String mSpFileName = "";
    private static SharedPreferences sp = null;

    private SharedPreferenceUtils(){

    }

    public static SharedPreferenceUtils getInstance(Context ctx, String name) {
        return getInstance(ctx,name,Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtils getInstance(Context ctx, String name, int mode) {
        if (mInstantPreferenceUtils == null) {
            mInstantPreferenceUtils = new SharedPreferenceUtils();
        }
        if (ctx != null && !TextUtils.isEmpty(name)) {
            if (sp == null || TextUtils.equals(mSpFileName, name) == false) {
                sp = ctx.getSharedPreferences(name, mode < 0 ? Context.MODE_PRIVATE : mode);
                mSpFileName=name;
            }
        }
        return mInstantPreferenceUtils;
    }

    /**
     * 写入单个值， 支持多种数据类型
     *
     * @param key
     * @param value Object
     * @return
     */
    public boolean setValue(String key, Object value) {
        if (sp == null || TextUtils.isEmpty(key) && value != null) {
            return false;
        } else {
            Editor editor = sp.edit();
            fillEditor(editor, key, value);
            return editor.commit();
        }
    }

    /**
     * 写入Map键值对，支持多种数据类型
     *
     * @param map
     * @return
     */
    public boolean setMapValue(Map<String, Object> map) {
        if (sp == null || map == null || map.isEmpty()) {
            return false;
        } else {
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            Editor editor = sp.edit();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                fillEditor(editor, entry.getKey(), entry.getValue());
            }
            boolean result = editor.commit();
            if (result) {
                Log.i(TAG, "setMapValue: 写入成功" + map.toString());
            } else {
                Log.i(TAG, "setMapValue: 写入失败" + map.toString());
            }
            return result;
        }
    }

    /**
     * 获取对应值
     *
     * @param key
     * @return
     */
    public Object getValue(String key, Object defaultValue) {
        Object data = null;
        if (sp != null && sp.contains(key)) {
            Map<String, Object> map = getValueAll();
            if (null != map && map.containsKey(key)) {
                data = map.get(key);
            }
        }
        return null == data ? defaultValue : data;
    }

    /**
     * 返回String数据
     * @param key
     * @return
     */
    public String getValue(String key) {
        if (sp == null || TextUtils.isEmpty(key)) {
            return null;
        } else {
            return sp.getString(key, "");
        }
    }

    /**
     * 向Editor中写入值
     * @param editor
     * @param key
     * @param value
     */
    private void fillEditor(Editor editor, String key, Object value) {
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Set<?>) {
            //editor.putStringSet(key, (Set<String>)values);
        }
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Object> getValueAll() {
        if (sp != null) {
            try {
                return (HashMap<String, Object>) sp.getAll();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 清除key
     * @param key
     */
    public void deleteSPValue(String key){
        if (sp != null) {
            Editor editor = sp.edit();
            editor.remove(key);
            editor.commit();
        }
    }

    /**
     * 删除配置文件中所有值
     */
    public void deleteSP() {
        if (sp != null) {
            Editor editor = sp.edit();
            editor.clear();
            editor.commit();
        }
    }

}
