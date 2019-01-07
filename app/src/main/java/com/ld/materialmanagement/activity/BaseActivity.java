package com.ld.materialmanagement.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.application.App;
import com.ld.materialmanagement.widget.CustomDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.drakeet.materialdialog.MaterialDialog;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    protected MaterialDialog mMaterialDialog;
    private CustomDialog mDialogWaiting;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initBundle(getIntent().getExtras())) {

            initView();
            setContentView(getContentView());
            unbinder = ButterKnife.bind(this);
            initToolBar();
            initWidget();
            initData();

        } else {
            finish();
        }
        mContext = this;
        App.activities.add(this);
    }

    /**
     * 初始化ActionBar
     * requestWindowF...
     * getSupportActionBar().hide()
     * ..
     */
    protected void initToolBar() {
    }

    /**
     * @param toolbar            Support v7 Toolbar
     * @param title              Toolbar 的标题
     * @param subtitle           Toolbar 的子标题
     * @param menu               Toolbar 的菜单，如果为0则不初始化菜单
     * @param isNavigationEnable 是否显示返回按钮并响应点击返回操作
     */
    protected void setupToolbar(@NonNull final Toolbar toolbar,
                                @StringRes int title,
                                @StringRes int subtitle,
                                @MenuRes int menu,
                                boolean isNavigationEnable) {
        if (title != 0) {
            toolbar.setTitle(title);
        }
        if (subtitle != 0) {
            toolbar.setSubtitle(subtitle);
        }
        if (menu != 0) {
            toolbar.inflateMenu(menu);
        }
        if (isNavigationEnable) {
            toolbar.setNavigationIcon(R.mipmap.ic_launcher);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Activity) (toolbar.getContext())).finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideWaitingDialog();
    }

    /**
     * @return 返回xml布局
     */
    protected abstract int getContentView();

    /**
     * 初始化View
     * 在ButterKnife.bind(this) 方法之前
     */
    protected void initView() {
    }

    /**
     * 初始化控件
     * 在ButterKnife.bind(this) 方法之后
     * 设置控件监听
     */
    protected void initWidget() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        App.activities.remove(this);
    }


    protected boolean initBundle(Bundle extras) {
        return true;
    }

    /**
     * @param tip                         标题
     * @param message                     信息
     * @param positiveText                积极的
     * @param negativeText                消极的
     * @param positiveButtonClickListener
     * @param negativeButtonClickListener
     * @return
     */
    public MaterialDialog showMaterialDialog(String tip, String message, String positiveText, String negativeText, View.OnClickListener positiveButtonClickListener, View.OnClickListener negativeButtonClickListener) {
        hideMaterialDialog();
        mMaterialDialog = new MaterialDialog(this);
        if (!TextUtils.isEmpty(tip)) {
            mMaterialDialog.setTitle(tip);
        }
        if (!TextUtils.isEmpty(message)) {
            mMaterialDialog.setMessage(message);
        }
        if (!TextUtils.isEmpty(positiveText)) {
            mMaterialDialog.setPositiveButton(positiveText, positiveButtonClickListener);
        }
        if (!TextUtils.isEmpty(negativeText)) {
            mMaterialDialog.setNegativeButton(negativeText, negativeButtonClickListener);
        }
        mMaterialDialog.show();
        return mMaterialDialog;
    }

    /**
     * 隐藏MaterialDialog
     */
    public void hideMaterialDialog() {
        if (mMaterialDialog != null) {
            mMaterialDialog.dismiss();
            mMaterialDialog = null;
        }
    }

    /**
     * 显示等待提示框
     */
    public Dialog showWaitingDialog(String tip) {
        hideWaitingDialog();
        View view = View.inflate(this, R.layout.dialog_waiting, null);
        if (!TextUtils.isEmpty(tip))
            ((TextView) view.findViewById(R.id.tvTip)).setText(tip);
        mDialogWaiting = new CustomDialog(this, view, R.style.dialog);
        mDialogWaiting.show();
        mDialogWaiting.setCancelable(false);
        return mDialogWaiting;
    }

    /**
     * 隐藏等待提示框
     */
    public void hideWaitingDialog() {
        if (mDialogWaiting != null) {
            mDialogWaiting.dismiss();
            mDialogWaiting = null;
        }

    }


}
