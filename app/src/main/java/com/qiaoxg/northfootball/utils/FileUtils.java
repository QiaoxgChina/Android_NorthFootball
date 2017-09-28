package com.qiaoxg.northfootball.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.qiaoxg.northfootball.app.AppConstants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by admin on 2017/3/14.
 */

public class FileUtils {

    private static final String TAG = "FileUtils";

    /**
     * 根据路径加载本地图片
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmapByPath(String path) {
        boolean isFile = isFileExist(path);
        if (!isFile) {
            return null;
        }
        return BitmapFactory.decodeFile(path);
    }

    /**
     * 保存bitmap到本地
     *
     * @param filePath
     * @param bitmap
     */
    public static void saveBitmap(String filePath, Bitmap bitmap) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
            }
        }
    }


    /**
     * 获取手机内置存储路径
     *
     * @return
     */
    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 根据路径判断文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File f = new File(path);
        if (f.exists() && f.isFile()) {
            return true;
        }
        return false;
    }

    /**
     * 获得系统相册的路径
     *
     * @return
     */
    public static String getDCIMDir() {
        String result = null;
        File videoDirFile = null;
        boolean error = false;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            result = getExternalStorageDirectory();
            result += File.separator + AppConstants.DCIM_CAMERA_PATH;
            videoDirFile = new File(result);
            if (!videoDirFile.exists() && !videoDirFile.mkdirs()) {
                error = true;
            }
        }
        if (error)
            return null;
        return result;
    }

    /**
     * 获得html页面缓存路径
     *
     * @return
     */
    public static String getHtmlTempDir() {
        String result = null;
        File dirFile = null;
        boolean error = false;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            result = getExternalStorageDirectory();
            result += File.separator + AppConstants.LOCAL_HTML_PATH;
            dirFile = new File(result);
            Log.e(TAG, "getHtmlTempDir: dirFile is " + dirFile);
            if (!dirFile.exists() && !dirFile.mkdirs()) {
                error = true;
            }
        }
        if (error)
            return null;
        return result;
    }

}
