package com.ld.materialmanagement.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Airmour@163.com on 2017/2/23
 */

public class App extends Application {

    public static List<Activity> activities = new LinkedList<>();

    //TODO app发布后要捕获异常并上传至服务器

    static Context _context;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
        //LeakCanary.install(this);

        //初始化Logger
        Logger.init("Ld");
    }

    public static synchronized App getContext() {
        return (App) _context;
    }

}
