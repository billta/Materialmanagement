package com.ld.materialmanagement.application;

/**
 * Created by Airmour@163.com on 2017/2/23
 * app的常量类
 */
public class AppConst {

    public static final String SP_CACHE = "cache";
    public static final String FLAG_SEARCH = "search";
    public static final String FLAG_SCAN = "scan";
    public static final String FLAG_CHECKED="checked";

    // SharedPreferences 的key
    public static final class Sp {
        public static final String KEY_IS_FIRST = "isFirst";
        public static final String KEY_FRIST_CHART = "firstChart";

    }

    public static final class Config {
        // 双击退出
        public static final String FILE = "config";
        public static final String KEY_DOUBLE_CLICK_EXIT = "key_double_exit";
        public static final String KEY_CHANGE_PORT = "key_port";


    }

    //请求头
    public static final class Header {
        public static final String FILE = "header";
        public static final String SET_COOKIE = "Cookie";
    }

    // user
    public static final class User {
        public static final String FILE = "user";
        public static final String KEY_USER_ID = "id";
        public static final String KEY_USER_PW = "pw";
        public static final String KEY_USER_NAME = "name";
    }

    //缓存的文件名
    public static final class Cache {
        //统计图的
        public static final String CHART_ONE = "chartOne";
        public static final String CHART_TWO = "chartTwo";
        //台账
        public static final String LIST_GOODS = "listGoods";
        //入库列表
        public static final String LIST_IN_LIB = "listInLib";
        public static final String LIST_IN_LOAN = "listInLoan";
        //出库列表
        public static final String LIST_OUT_LIB = "listOutLib";
        public static final String LIST_OUT_LOAN = "listOutLan";


    }
}
