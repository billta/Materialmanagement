package com.ld.materialmanagement.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ld.materialmanagement.R;
import com.ld.materialmanagement.adapter.InLibRecyclerViewAdapter;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.bean.InLibBean;
import com.ld.materialmanagement.bean.LibBeans;
import com.ld.materialmanagement.model.APi;
import com.ld.materialmanagement.model.Network;
import com.ld.materialmanagement.utils.CacheUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 入库的界面
 */
public class InLibraryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_in_lib)
    RecyclerView mRecycleView;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private InLibRecyclerViewAdapter adapter;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_in_library;
    }


    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

        initSwipeRefresh(mSwipeRefresh);

        adapter = new InLibRecyclerViewAdapter(mActivity);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));

        mRecycleView.setAdapter(adapter);

        onRefresh();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSwipeRefresh.setOnRefreshListener(this);
        //mSwipeRefresh.setRefreshing(true);
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);

        String json = CacheUtils.getStrCache(AppConst.Cache.LIST_IN_LIB);
        Gson gson = new Gson();

        LibBeans<InLibBean> libBeans = gson.fromJson(json, new TypeToken<LibBeans<InLibBean>>() {
        }.getType());

        if (libBeans != null) {
            ArrayList<InLibBean> beans = new ArrayList<>();
            for (InLibBean bean : libBeans.data) {
                if (bean.LendReturn.equals("入库")) {
                    beans.add(bean);
                }
            }
            adapter.updateData(beans);
        }
    }

    @Override
    public void onRefresh() {

        APi api = Network.getInstance().getApi(mActivity);
        Call<LibBeans<InLibBean>> inLibData = api.getInLibData("入库");
        inLibData.enqueue(new Callback<LibBeans<InLibBean>>() {
            @Override
            public void onResponse(Call<LibBeans<InLibBean>> call, Response<LibBeans<InLibBean>> response) {
                mSwipeRefresh.setRefreshing(false);
                //解析出数据
                LibBeans body = response.body();
                ArrayList<InLibBean> data = body.data;

                if (data != null) {
                    ArrayList<InLibBean> beans = new ArrayList<>();
                    for (InLibBean bean : data) {
                        if (bean.LendReturn.equals("入库")) {
                            beans.add(bean);
                        }
                    }
                    adapter.updateData(beans);
                }

                //设置缓存
                CacheUtils.setCache(AppConst.Cache.LIST_IN_LIB, body);

            }

            @Override
            public void onFailure(Call<LibBeans<InLibBean>> call, Throwable t) {
                Logger.i(t.getMessage());
                mSwipeRefresh.setRefreshing(false);
            }
        });

    }
}
