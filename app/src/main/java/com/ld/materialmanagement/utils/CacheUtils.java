package com.ld.materialmanagement.utils;

import android.os.Environment;

import com.google.gson.Gson;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.bean.InLibBean;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.google.common.io.CharStreams.copy;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/4/19.
 */

public class CacheUtils {


    //清除缓存
    public static boolean clearCache(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            boolean delete = file.delete();
            return delete;
        } else {
            return false;
        }
    }


    /**
     * 设置缓存
     *
     * @param fileName 缓存的文件名 </br>
     * @param t        要缓存的对象
     */
    public static <T> void setCache(String fileName, T t) {
        Gson gson = new Gson();
        String str = gson.toJson(t);
        String cachePath = getCachePath() + fileName;
        //将字符串写入文件
        File file = new File(cachePath);
        if (file.exists()) {
            file.delete();
        }

        FileWriter writer;
        try {
            writer = new FileWriter(cachePath);
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //获取缓存
    public static <T> T getCache(String fileName, Class<T> classOfT) {
        Gson gson = new Gson();
        String path = getCachePath() + fileName;

        File file = new File(path);
        String str = "";
        try {
            str = readFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        T t = gson.fromJson(str, classOfT);
        return t;
    }

    public static String getStrCache(String fileName) {
        String path = getCachePath() + fileName;

        File file = new File(path);
        String str;
        try {
            str = readFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            str = "";
        }
        return str;
    }


    public static String readFile(File file) throws IOException {
        if (file != null && file.canRead()) {

            try {
                FileInputStream is = new FileInputStream(file);
                byte[] b = new byte[is.available()];
                is.read(b);
                String result = new String(b);

                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "";
    }


    /**
     * 获取应用的cache目录
     */
    public static String getCachePath() {
        File f = UiUtils.getContext().getCacheDir();
        if (null == f) {
            return null;
        } else {
            return f.getAbsolutePath() + "/";
        }
    }

    /**
     * 清除所有缓存
     */
    public static void clearAllCache() {
        //删除所有缓存文件
        String[] files = {
                AppConst.Cache.CHART_ONE,
                AppConst.Cache.CHART_TWO,
                AppConst.Cache.LIST_GOODS,
                AppConst.Cache.LIST_IN_LIB,
                AppConst.Cache.LIST_OUT_LIB
        };

        for (int i = 0; i < files.length; i++) {
            File file = new File(getCachePath() + files[i]);
            if (file.exists()) {
                file.delete();
            }
        }

        System.gc();
    }
}
