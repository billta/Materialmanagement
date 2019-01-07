package com.ld.materialmanagement.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.utils.CacheUtils;
import com.ld.materialmanagement.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_current_port)
    TextView mTvCurrentPort;
    @BindView(R.id.rl_change_port)
    LinearLayout mRlChangePort;
    @BindView(R.id.activity_settings)
    LinearLayout mActivitySettings;
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.switch_compat)
    SwitchCompat mSwitchCompat;
    private SharedPreferences sp;

    @Override
    protected int getContentView() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void initWidget() {
        mTextTitle.setText("设置");

        // 从sp中得到
        sp = getSharedPreferences(AppConst.Config.FILE, Context.MODE_PRIVATE);
        String port = sp.getString(AppConst.Config.KEY_CHANGE_PORT, "192.100.10.228");
        mTvCurrentPort.setText("当前地址是: " + port);

        boolean exit = sp.getBoolean(AppConst.Config.KEY_DOUBLE_CLICK_EXIT, true);
        mSwitchCompat.setChecked(exit);

        //双击退出
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sp.edit().putBoolean(AppConst.Config.KEY_DOUBLE_CLICK_EXIT, isChecked).commit();
            }
        });

    }

    @OnClick({R.id.rl_change_port
            , R.id.ll_double_exit
            , R.id.ll_clear_cache
            , R.id.btn_exit_account
            , R.id.ll_check_update
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_change_port:
                // 弹出修改端口的dialog
                showChangePortDialog();
                break;
            case R.id.ll_double_exit:
                // 再点一次退出
                changeExitStatus();
                break;
            case R.id.ll_clear_cache:
                //清除缓存
                cleanCache();
                break;
            case R.id.btn_exit_account:
                //退出账号
                logOut();
                break;

            case R.id.ll_check_update:
                checkUpdate();
                break;

        }
    }

    private void checkUpdate() {
        showWaitingDialog("检测新版本");
        new Thread() {
            @Override
            public void run() {
                super.run();


                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideWaitingDialog();
                        Snackbar.make(mActivitySettings, "已经是最新版本", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }.start();
    }

    /**
     * 退出登录
     */
    private void logOut() {

        SharedPreferences spUser = getSharedPreferences(AppConst.User.FILE, Context.MODE_PRIVATE);
        spUser.edit().clear().apply();
        UiUtils.showLoginActivity(SettingsActivity.this, null);
        finish();
    }

    private void cleanCache() {
        showWaitingDialog("正在清除缓存");
        new Thread() {
            @Override
            public void run() {
                super.run();

                CacheUtils.clearAllCache();
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideWaitingDialog();
                        Snackbar.make(mActivitySettings, "缓存已清除", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }.start();

    }


    private void changeExitStatus() {
        boolean checked = mSwitchCompat.isChecked();
        if (checked) {
            mSwitchCompat.setChecked(false);
            Toast.makeText(mContext, "关闭再点一次退出", Toast.LENGTH_SHORT).show();
        } else {
            mSwitchCompat.setChecked(true);
            Toast.makeText(mContext, "开启再点一次退出", Toast.LENGTH_SHORT).show();
        }
    }


    private void showChangePortDialog() {
        LayoutInflater inflater = (LayoutInflater) SettingsActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_edit_text, null);
        EditText editText = (EditText) view.findViewById(R.id.dialog_et);

        String port = sp.getString(AppConst.Config.KEY_CHANGE_PORT, "172.17.193.200");
        editText.setHint(port);

        new AlertDialog.Builder(SettingsActivity.this)
                .setTitle("更改地址")
                .setIcon(R.drawable.ic_wifi_tethering)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et = (EditText) view.findViewById(R.id.dialog_et);
                        String ip = et.getText().toString();

                        if (ip.matches("[\\u4E00-\\u9FA5]")) {
                            //更新sp中的内容
                            sp.edit().putString(AppConst.Config.KEY_CHANGE_PORT, ip).apply();
                            Toast.makeText(mContext, "更改成功", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(mContext, "不能包含中文", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }


}
