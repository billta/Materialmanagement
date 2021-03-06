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

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/4/26.
 * <p>
 * <p>
 * 归还物品的fragment
 */

public class InLoanFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private InLibRecyclerViewAdapter adapter;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_in_loan;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSwipeRefresh.setOnRefreshListener(this);
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
    protected void initData(Bundle arguments) {
        super.initData(arguments);

        String json = CacheUtils.getStrCache(AppConst.Cache.LIST_IN_LOAN);
        Gson gson = new Gson();

        LibBeans<InLibBean> libBeans = gson.fromJson(json, new TypeToken<LibBeans<InLibBean>>() {
        }.getType());

        if (libBeans != null) {
            adapter.updateData(libBeans.data);
        }
    }

    @Override
    public void onRefresh() {

        APi api = Network.getInstance().getApi(mActivity);
        Call<LibBeans<InLibBean>> loanIn = api.InLoan("归还");

        loanIn.enqueue(new Callback<LibBeans<InLibBean>>() {
            @Override
            public void onResponse(Call<LibBeans<InLibBean>> call, Response<LibBeans<InLibBean>> response) {
                mSwipeRefresh.setRefreshing(false);
                //解析出数据
                LibBeans body = response.body();
                //更新adapter的数据
                adapter.updateData(body.data);
                //设置缓存
                CacheUtils.setCache(AppConst.Cache.LIST_IN_LOAN, body);
            }

            @Override
            public void onFailure(Call<LibBeans<InLibBean>> call, Throwable t) {
                mSwipeRefresh.setRefreshing(false);
            }
        });

    }
}
