package com.ld.materialmanagement.utils;

import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.ld.materialmanagement.application.App;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/3/9.
 */

public class StringUtil {

    static {
        System.loadLibrary("LdJni");
    }

    public static synchronized native String getString();

    /**
     * \/Date(1489029246930)\/
     *
     * @param str 括号中含有数字字符串 <br>
     * @return long类型的数 <br>
     */
    public static long getLong(String str) {

        if(TextUtils.isEmpty(str))
            return  System.currentTimeMillis();

        String regex = "(?<=\\()(.+?)(?=\\))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? Long.parseLong(matcher.group().trim()) : System.currentTimeMillis();
    }

    /**
     * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd '<br>
     * 如Sat May 11 17:24:21 CST 2002 to '2002-05-11 '<br>
     *
     * @param time Date 日期<br>
     * @return String<br>
     */
    public static String dateToString(Date time) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        String ctime = formatter.format(time);

        return ctime;
    }

    public static String timeToString(Date time) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ctime = formatter.format(time);

        return ctime;
    }

    public static String formatDateString(String str) {
        return dateToString(new Date(getLong(str)));
    }
    public static String formatTimeString(String str) {
        return timeToString(new Date(getLong(str)));
    }

    public static String getVersionName() {
        try {
            return App
                    .getContext()
                    .getPackageManager()
                    .getPackageInfo(App.getContext().getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            return "undefined version name";
        }
    }

}
