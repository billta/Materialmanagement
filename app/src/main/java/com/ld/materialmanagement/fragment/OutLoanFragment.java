package com.ld.materialmanagement.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
 * 借出物品的fragment
 */

public class OutLoanFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    public static final int SHOW = 11;
    public static final int HIDE = 22;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW:
                    mSwipeRefresh.setRefreshing(true);
                    break;
                case HIDE:
                    mSwipeRefresh.setRefreshing(false);
                    break;
            }
        }
    };
    private OutLibRecyclerViewAdapter adapter;


    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_out_loan;
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


        String json = CacheUtils.getStrCache(AppConst.Cache.LIST_OUT_LOAN);
        Gson gson = new Gson();

        LibBeans<OutLibBean> libBeans = gson.fromJson(json, new TypeToken<LibBeans<OutLibBean>>() {
        }.getType());

        if (libBeans != null) {
            adapter.updateData(libBeans.data);
        }

    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(SHOW);
        APi api = Network.getInstance().getApi(mActivity);

        Call<LibBeans<OutLibBean>> loanOut = api.OutLoan("借出");
        loanOut.enqueue(new Callback<LibBeans<OutLibBean>>() {
            @Override
            public void onResponse(Call<LibBeans<OutLibBean>> call, Response<LibBeans<OutLibBean>> response) {
                handler.sendEmptyMessage(HIDE);
                LibBeans<OutLibBean> body = response.body();
                // LibBeans body=response.body();
                if (body.data != null && body.data.size() > 0) {


                    adapter.updateData(body.data);
                }
                //设置缓存
                CacheUtils.setCache(AppConst.Cache.LIST_OUT_LOAN, body);

            }

            @Override
            public void onFailure(Call<LibBeans<OutLibBean>> call, Throwable t) {
                handler.sendEmptyMessage(HIDE);
            }
        });




    }
}
