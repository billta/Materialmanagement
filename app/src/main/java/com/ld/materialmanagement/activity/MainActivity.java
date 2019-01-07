package com.ld.materialmanagement.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.bean.MainTab;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import zxing.activity.CaptureActivity;

import static com.ld.materialmanagement.activity.InLibDetails.EXTRA;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {


    private static final int REQUEST_CODE_PERMISSIONS = 0x0012;

    @BindView(R.id.fth_bottom)
    FragmentTabHost mTabHost;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    boolean isDoubleClick = false;
    private long mBackPressedTime;
    private long fristTime;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    protected void initView() {

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //绑定布局
        mTabHost.setup(this, getSupportFragmentManager(), R.id.fl_main);
        if (Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }

        //初始化TabHost
        initTabs();

    }


    @Override
    protected void initToolBar() {
        super.initToolBar();

        //setupToolbar(mToolBar);
        setSupportActionBar(mToolBar);
        //getSupportActionBar().setTitle("主页");
        mToolBar.setTitle("备件管理系统");
        mToolBar.setSubtitle("主页");
        mToolBar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolBar.setTitleTextColor(Color.WHITE);
        mToolBar.setSubtitleTextColor(Color.rgb(240, 255, 255));


    }

    private void initTabs() {
        MainTab[] tabs = MainTab.values();

        //遍历添加
        for (int i = 0; i < tabs.length; i++) {

            MainTab mainTab = tabs[i];

            //创建新的选项卡
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.resName));

            View indicator = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_indicator, null);

            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable icon = this.getResources().getDrawable(mainTab.resIcon);
            //设置图标
            title.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
            title.setText(getString(mainTab.resName));

            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            //传递数据
            Bundle bundle = new Bundle();
            bundle.putString("key", "content: " + getString(mainTab.resName));
            // 2. 把新的选项卡添加到TabHost中
            mTabHost.addTab(tab, mainTab.clazz, bundle);
        }


    }

    protected void initData() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mToolBar.getMenu().findItem(R.id.app_bar_search).setVisible(false);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mToolBar.setSubtitle(tabId);

                if ("更多".equals(tabId)) {
                    getSupportActionBar().hide();
                } else {
                    getSupportActionBar().show();
                }

                if ("主页".equals(tabId)) {
                    mToolBar.getMenu().findItem(R.id.app_bar_search).setVisible(false);
                }else if ("物资台账".equals(tabId)){
                    mToolBar.getMenu().findItem(R.id.app_bar_search).setVisible(false);
                }else {
                    mToolBar.getMenu().findItem(R.id.app_bar_search).setVisible(true);
                }


            }
        });

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.app_bar_search:
                search();
                break;
            case R.id.app_bar_scan:
                requestPermissions();
                break;
        }

        return true;
    }

    private void search() {

        int currentTab = mTabHost.getCurrentTab();
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(AppConst.FLAG_SEARCH, currentTab);
        startActivity(intent);
    }

    //再点一次退出
    @Override
    public void onBackPressed() {
        SharedPreferences sp = getSharedPreferences(AppConst.Config.FILE, Context.MODE_PRIVATE);
        boolean isDoubleClick = sp.getBoolean(AppConst.Config.KEY_DOUBLE_CLICK_EXIT, true);

        if (isDoubleClick) {
            long curTime = SystemClock.uptimeMillis();
            if ((curTime - mBackPressedTime) < (3 * 1000)) {
                finish();
            } else {
                mBackPressedTime = curTime;
                Toast.makeText(this, R.string.tip_double_click_exit, Toast.LENGTH_LONG).show();
            }
        } else {
            finish();
        }

    }


    public static void show(Context context, Bundle extras) {
        Intent intent = new Intent(context, MainActivity.class);
        if (extras != null) {
            intent.putExtra(EXTRA, extras);
        }
        context.startActivity(intent);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //权限被同意
        //startActivity(new Intent(this, ScanActivity.class));
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //权限被拒绝什么也不做
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSIONS)
    private void requestPermissions() {
        String[] perms = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
            //说明已经有权限了

            //传递一个flag
            int currentTab = mTabHost.getCurrentTab();
            Intent intent = new Intent(this, CaptureActivity.class);
            intent.putExtra(AppConst.FLAG_SCAN, currentTab);
            startActivity(intent);

        } else {
            //说明没有这些权限 需要去申请
            EasyPermissions.requestPermissions(this, "使用此功能,需要一些权限", REQUEST_CODE_PERMISSIONS, perms);
        }
    }
}
