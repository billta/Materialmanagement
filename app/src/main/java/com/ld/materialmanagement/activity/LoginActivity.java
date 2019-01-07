package com.ld.materialmanagement.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.bean.User;
import com.ld.materialmanagement.model.LoginModel;
import com.ld.materialmanagement.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ld.materialmanagement.activity.InLibDetails.EXTRA;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.u_number)
    EditText mNumber;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.sign_in_button)
    Button mSignIn;
    @BindView(R.id.ll_root_login)
    LinearLayout mRootView;
    private Dialog dialog;


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.sign_in_button)
    public void onClick() {

        hideImm();

        if (checkInput())
            return;

        String num = mNumber.getText().toString().trim();
        String pwd = mPassword.getText().toString().trim();

        login(num, pwd);

    }

    /**
     * 登录
     *
     * @param num  用户名
     * @param pswd 密码
     * @return 是否登录成功
     */
    public void login(String num, String pswd) {

        dialog = showWaitingDialog("正在登录...");
        LoginModel.login(LoginActivity.this,num, pswd, new LoginModel.loginState() {
            @Override
            public void loginSuccess(User user) {
                dialog.dismiss();

                // 跳转界面
                UiUtils.showMainActivity(LoginActivity.this, null);
                finish();
            }

            @Override
            public void loginFailure(String msg) {
                dialog.dismiss();

                // 给出错误提示
                showMaterialDialog("错误", msg, "确定", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideMaterialDialog();
                    }
                }, null);
            }

            @Override
            public void connectFail() {
                    //没有网络
            }

        });
    }

    public boolean checkInput() {
        boolean flag = false;
        if (checkNull(mNumber)) {
            showMaterialDialog("错误", "工号不能为空", "确定", null, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideMaterialDialog();
                }
            }, null);
            flag = true;
        }
        if (checkNull(mPassword)) {
            showMaterialDialog("错误", "密码不能为空", "确定", null, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideMaterialDialog();
                }
            }, null);
            flag = true;
        }
        return flag;
    }

    public boolean checkNull(EditText editText) {
        return TextUtils.isEmpty(editText.getText());
    }

    /**
     * 隐藏输入法
     */
    public void hideImm() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mRootView, InputMethodManager.SHOW_FORCED);
        if (imm.isActive()) {//isOpen若返回true，则表示输入法打开
            imm.hideSoftInputFromWindow(mRootView.getWindowToken(), 0); //强制隐藏键盘
        }
    }


    public static void show(Context context, Bundle extras) {

        Intent intent = new Intent(context, LoginActivity.class);
        if (extras != null) {
            intent.putExtra(EXTRA, extras);
        }
        context.startActivity(intent);
    }

}
