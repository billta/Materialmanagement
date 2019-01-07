package com.ld.materialmanagement.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.widget.CustomDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * fragment 基类
 */
public abstract class BaseFragment extends Fragment {


    /**
     * 贴附的activity
     */
    protected FragmentActivity mActivity;
    /**
     * 根view
     */
    protected View mRootView;

    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完onCreateView,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare;
    private Unbinder unbinder;
    private SharedPreferences cacheSp;

    private CustomDialog mDialogWaiting;

    @Override
    public void onAttach(Context context) {
        // 防止空指针
        super.onAttach(context);
        mActivity = getActivity();
        cacheSp = mActivity.getSharedPreferences(AppConst.SP_CACHE, Context.MODE_PRIVATE);

    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutResourceId(), container, false);

        unbinder = ButterKnife.bind(this, mRootView);
        initView(mRootView);

        initData(getArguments());

        mIsPrepare = true;

        onLazyLoad();

        initListener();

        return mRootView;
    }

    /**
     * 初始化下拉刷新的颜色
     *
     * @param mSwipeRefresh
     */
    protected void initSwipeRefresh(SwipeRefreshLayout mSwipeRefresh) {

        //设置刷新时的颜色
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

    }

    /**
     * 初始化数据
     *
     * @param arguments 接收到的从其他地方传递过来的参数
     */
    protected void initData(Bundle arguments) {

    }

    /**
     * 初始化View
     */
    protected void initView(View rootView) {

    }

    /**
     * 设置监听事件
     */
    protected void initListener() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.mIsVisible = isVisibleToUser;

        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    /**
     * 用户可见时执行的操作
     */
    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad();
        }
    }

    /**
     * 懒加载，仅当用户可见切view初始化结束后才会执行
     */
    protected void onLazyLoad() {

    }

    /**
     * 设置根布局资源id
     *
     * @return
     */
    protected abstract int setLayoutResourceId();



    public void onBackPressed() {
        super.getActivity().onBackPressed();
        hideWaitingDialog();
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



    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }


}


