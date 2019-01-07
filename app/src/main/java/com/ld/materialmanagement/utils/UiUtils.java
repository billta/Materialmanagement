package com.ld.materialmanagement.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import com.ld.materialmanagement.activity.InLibDetails;
import com.ld.materialmanagement.activity.LoginActivity;
import com.ld.materialmanagement.activity.MainActivity;
import com.ld.materialmanagement.activity.OutLibDetails;
import com.ld.materialmanagement.application.App;

/**
 * ___/|
 * \o.O|
 * (___)
 *   U
 * Created by Airmour@163.com on 2017/3/9
 */
public class UiUtils {


    /**
     * 得到屏幕的宽度
     */
    public static int getScreenWidth(Context mContext) {
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        manager.getDefaultDisplay().getSize(point);

        int width = point.x;
        int height = point.y;
        return width;
    }


    /**
     * 得到屏幕的高度
     */
    public static int getScreenHeight(Context mContext) {
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        manager.getDefaultDisplay().getSize(point);

        int height = point.y;
        return height;
    }


    public static void showLoginActivity(Context context,Bundle extras) {
        LoginActivity.show(context,extras);
    }

    public static void showInLibDetailActivity(Context context, String extras) {
        InLibDetails.show(context,extras);
    }

    public static void showOutLibDetailActivity(Context context, String extras) {
        OutLibDetails.show(context,extras);
    }

    public static void showMainActivity(Context context,  Bundle extras) {
        MainActivity.show(context,extras);
    }

    public static Context getContext() {
        return App.getContext();
    }
}
