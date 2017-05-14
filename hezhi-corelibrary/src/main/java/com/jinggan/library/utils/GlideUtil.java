package com.jinggan.library.utils;

/**
 * @Package: com.yunwei.library.imageLoader
 * @Description:URL适配
 * @author: Aaron
 * @date: 2016-06-15
 * @Time: 11:25
 * @version: V1.0
 */
public class GlideUtil {

    public static String fitterUrl(String url) {
        if (!url.contains("http://")) {
            url = "file://" + url;
        }
        return url;
    }
}
