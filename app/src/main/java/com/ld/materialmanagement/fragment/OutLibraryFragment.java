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
import com.ld.materialmanagement.adapter.OutLibRecyclerViewAdapter;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.bean.LibBeans;
import com.ld.materialmanagement.bean.OutLibBean;
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
 * 出库界面
 */
public class OutLibraryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_out_lib)
    RecyclerView mRecycleView;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private OutLibRecyclerViewAdapter adapter;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_out_library;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

        initSwipeRefresh(mSwipeRefresh);

        adapter = new OutLibRecyclerViewAdapter(mActivity);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));

        mRecycleView.setAdapter(adapter);
        onRefresh();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSwipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);

        String json = CacheUtils.getStrCache(AppConst.Cache.LIST_OUT_LIB);
        Gson gson = new Gson();

        LibBeans<OutLibBean> libBeans = gson.fromJson(json, new TypeToken<LibBeans<OutLibBean>>() {
        }.getType());
        if (libBeans != null) {
            ArrayList<OutLibBean> beans = new ArrayList<>();
            for (OutLibBean bean : libBeans.data) {
                if (bean.LendReturn.equals("出库")) {
                    beans.add(bean);
                }
            }
            adapter.updateData(beans);
        }
//        if (libBeans != null) {
//            ArrayList<OutLibBean> data = libBeans.data;
//
//            for (int i = 0; i < data.size(); i++) {
//
//                if (data.get(i).LendReturn.equals("出库")) {
//                    data.remove(i);
//                }
//            }
//
//            adapter.updateData(data);
//        }

    }


    @Override
    public void onRefresh() {

        APi api = Network.getInstance().getApi(mActivity);
        Call<LibBeans<OutLibBean>> outLibData = api.getOutLibData("出库");
        outLibData.enqueue(new Callback<LibBeans<OutLibBean>>() {
            @Override
            public void onResponse(Call<LibBeans<OutLibBean>> call, Response<LibBeans<OutLibBean>> response) {
                mSwipeRefresh.setRefreshing(false);

                LibBeans body = response.body();



                ArrayList<OutLibBean> data = body.data;
                //解析出数据
                if (data != null) {
                    ArrayList<OutLibBean> beans = new ArrayList<>();
                    for (OutLibBean bean : data) {
                        if (bean.LendReturn.equals("出库")) {
                            beans.add(bean);
                        }
                    }
                    adapter.updateData(beans);
                }
//                if (body != null) {
//                    ArrayList<OutLibBean> data = body.data;
//                    for (int i = 0; i < data.size(); i++) {
//                        if (data.get(i).LendReturn.equals("出库")) {
//                            data.remove(i);
//                        }
//                    }
//                    adapter.updateData(data);
//                }

                //设置缓存
                CacheUtils.setCache(AppConst.Cache.LIST_OUT_LIB, body);
            }

            @Override
            public void onFailure(Call<LibBeans<OutLibBean>> call, Throwable t) {
                Logger.i(t.getMessage());
                mSwipeRefresh.setRefreshing(false);

            }
        });
    }

}
