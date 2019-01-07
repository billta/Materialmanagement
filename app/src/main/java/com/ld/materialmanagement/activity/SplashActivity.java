package com.ld.materialmanagement.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.bean.User;
import com.ld.materialmanagement.model.LoginModel;
import com.ld.materialmanagement.utils.AesHelper;
import com.ld.materialmanagement.utils.StringUtil;
import com.ld.materialmanagement.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 闪屏界面
 * 1.创建桌面快捷方式
 * 2.检查版本更新
 * 3.初始化资源
 * 4.跳转界面
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_appversion)
    TextView mTvAppversion;
    private SharedPreferences sp;


    public static final int FLAG_JUMP = 1;
    public static final int FLAG_DISS = 2;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FLAG_JUMP:
                    UiUtils.showMainActivity(SplashActivity.this, null);
                    finish();
                    break;

                case FLAG_DISS:
                    finish();
                    break;

            }
        }
    };


    protected void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }


    }


    protected void initData() {
        sp = getSharedPreferences(AppConst.Config.FILE, Context.MODE_PRIVATE);

        boolean isFirst = sp.getBoolean(AppConst.Sp.KEY_IS_FIRST, true);
        if (isFirst) {
            // 如果是第一次启动 创建桌面快捷方式
            createShoutCut();
            sp.edit().putBoolean(AppConst.Sp.KEY_IS_FIRST, false).apply();
        }

        mTvAppversion.setText(StringUtil.getVersionName());

    }

    @Override
    protected void onStart() {
        super.onStart();
        //检查更新
        checkUpDate();
        //尝试登陆
        tryLogin();
        //检查网络
        //checkNet();

    }

    private void checkNet() {

        //判断是否连接到内网
        //http://172.17.193.33/ 华能的官网


    }


    /**
     * 尝试登陆
     */
    public void tryLogin() {

        //获取本地保存的账号密码 并解密 如果获取不到就跳转登陆界面
        SharedPreferences userSp = getSharedPreferences(AppConst.User.FILE, Context.MODE_PRIVATE);

        String id = AesHelper.decrypt(userSp.getString(AppConst.User.KEY_USER_ID, ""), StringUtil.getString());
        String pswd = AesHelper.decrypt(userSp.getString(AppConst.User.KEY_USER_PW, ""), StringUtil.getString());

        LoginModel.login(SplashActivity.this, id, pswd, new LoginModel.loginState() {
            @Override
            public void loginSuccess(User user) {
                //登录成功
                UiUtils.showMainActivity(SplashActivity.this, null);
                finish();
            }

            @Override
            public void loginFailure(String msg) {
                //登录失败
                UiUtils.showLoginActivity(SplashActivity.this, null);
                finish();
            }

            @Override
            public void connectFail() {
                //当前网络不是内网
                Toast.makeText(mContext, "请连接至正确的网络", Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessageDelayed(FLAG_JUMP, 500);
            }
        });
    }

    /**
     * 检查更新
     */
    public void checkUpDate() {
        //TODO

    }

    /**
     * 创建桌面快捷方式   需要在manifest中配置权限
     * <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT"/>
     */
    private void createShoutCut() {

        // 创建桌面快捷方式
        Intent intent = new Intent();
        // 调频
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        /**
         * 快捷方式的三要素 长啥样(BitMap) 叫啥名 干啥事
         */
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);

        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, R.string.app_name);
        // 让快捷方式打开我们的应用 隐式意图 先给我们自己的界面添加action
        Intent home = new Intent();
        home.setAction("com.ld.home");
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, home);
        // 告诉启动器如果存在就不用创建了 不允许重复 源码中的键 是"duplicate"
        intent.putExtra("duplicate", false);

        sendBroadcast(intent);
    }

    public void jumpToMain() {
        jumpActivity(MainActivity.class);
    }

    public void jumpToLogin() {
        jumpActivity(LoginActivity.class);
    }

    public void jumpActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
        finish();
    }
}
