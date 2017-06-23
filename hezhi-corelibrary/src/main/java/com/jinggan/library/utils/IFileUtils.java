package com.jinggan.library.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 文件工具类
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:19
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class IFileUtils {

    /*文件根目录*/
    public static final String FILE_ROOT_DIRECTORY = "XiaoMai";
    /*项目根目录*/
    public static final String PROJECT_ROOT_DIRECTORY = "Base";
    /*图片文件夹*/
    public static final String IMAGE_DEIRECTORY = "image";
    /*录音文件夹*/
    public static final String RECORD_DIRECTORY = "records";
    /*缓存文件夹*/
    public static final String IMAGE_CATCH_DIR = "catch";
    /*下载目录*/
    public static final String DOWNLOAD_DIR = "download";

    /**
     * SD卡剩余容量
     * <p>
     * author: hezhiWu
     * created at 2017/3/22 17:27
     */
    public static long getSDFreeSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
    }

    /**
     * 获取SDK总容量
     * <p>
     * author: hezhiWu
     * created at 2017/3/22 17:28
     */
    public static long getSDAllSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //获取所有数据块数
        long allBlocks = sf.getBlockCount();
        //返回SD卡大小
        //return allBlocks * blockSize; //单位Byte
        //return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize) / 1024 / 1024; //单位MB
    }


    /**
     * 获取SD卡根目录
     *
     * @return
     */
    public static String getSDROOT() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 图片缓存地址
     *
     * @return
     */
    public static String getImageCatchDir() {
        String path = getSDROOT() + File.separator + FILE_ROOT_DIRECTORY + File.separator + IMAGE_CATCH_DIR;
        File file = new File(path);
        if (!file.exists()) {
            File filePath = file.getParentFile();
            filePath.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     *录音文件夹目录
     *
     * @return
     */
    public static String getRecordDirectory(){
        String path = getSDROOT() + File.separator + FILE_ROOT_DIRECTORY + File.separator + RECORD_DIRECTORY;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 下载目录
     *
     * @return
     */
    public static String getDownloadDir() {
        return getSDROOT() + File.separator + FILE_ROOT_DIRECTORY + File.separator + DOWNLOAD_DIR;
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return file.delete();
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 获取指定文件大小
     * <p>
     * author: hezhiWu
     * created at 2017/5/12 22:08
     */
    public static long getFileSize(File file){
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ILogcat.d("IFileUtils","size=="+size);
        return size;
    }


    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = files[i].delete();
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    public static String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Files.FileColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Files.FileColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 删除文件或整个文件夹
     * @param file
     */
    public static void deleteFileAndDir(File file) {
        try {
            if (file != null&&file.exists()) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (File f : files) {
                        if (f.isDirectory()) {
                            deleteFileAndDir(f);
                        } else if(f.isFile()){
                            f.delete();
                        }
                    }
                    file.delete();
                } else if (file.isFile()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件后缀名
     * @param file
     * @return
     */
    public static String getFileExtension(File file) {
        String extension="";
        if (file != null) {
            String fileName = file.getName();
            if (fileName.lastIndexOf(".")!=0) {
                extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            }
        }
        return extension;
    }
}
