package com.ld.materialmanagement.model;


import android.content.Context;
import android.content.SharedPreferences;

import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.bean.User;
import com.ld.materialmanagement.utils.AesHelper;
import com.ld.materialmanagement.utils.StringUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Airmour@163.com on 2017/3/7
 */

public class LoginModel {

    public static void login(Context context, final String num, final String psw, final loginState loginState) {

        final SharedPreferences spUser = context.getSharedPreferences(AppConst.User.FILE, Context.MODE_PRIVATE);
        final SharedPreferences spHeader = context.getSharedPreferences(AppConst.Header.FILE, Context.MODE_PRIVATE);

        APi api = Network.getInstance().getApi(context);

        final Call<User> login = api.login(num, psw);

        login.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                Logger.i("onResponse " + body.toString());
                System.out.print("body"+body.toString());
                System.out.print("response"+response.toString());
                if (loginState != null) {

                    if (body.success) {
                        //登录成功
                        loginState.loginSuccess(body);

                        //加密至本地
                        spUser.edit()
                                .putString(AppConst.User.KEY_USER_ID, AesHelper.encrypt(num, StringUtil.getString()))
                                .putString(AppConst.User.KEY_USER_PW, AesHelper.encrypt(psw, StringUtil.getString()))
                                .putString(AppConst.User.KEY_USER_NAME, body.name)
                                .apply();

                        //更新请求头 缓存请求头 里的 set
                        Headers headers = response.headers();
                        List<String> set = headers.values("Set-Cookie");


                        StringBuilder sb = new StringBuilder();
                        for (String str : set) {

                            String[] paths = str.split("path");
                            sb.append(paths[0].trim());
                        }
                        Logger.d("Cookie:  " + sb);
                        //将Set-Cookie保存到本地
                        spHeader.edit().putString(AppConst.Header.SET_COOKIE, sb.toString()).apply();

                        //       spHeader.edit()
                        //               .putString(AppConst.Header.KEY_COOKIE_ONE, set.get(0))
                        //               .putString(AppConst.Header.KEY_COOKIE_TWO, set.get(1))
                        //               .apply();

                    } else {
                        //登录失败
                        loginState.loginFailure("登录失败,请检查账号或密码");
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Logger.i("onFailure " + t.getMessage());
                if (loginState != null) {
                    loginState.connectFail();
                }
            }
        });

    }


    public interface loginState {

        void loginSuccess(User user);

        void loginFailure(String msg);

        void connectFail();

    }
}
