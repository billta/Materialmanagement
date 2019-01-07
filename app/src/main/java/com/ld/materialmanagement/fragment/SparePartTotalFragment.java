package com.ld.materialmanagement.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.ld.materialmanagement.R;
import com.ld.materialmanagement.activity.SparePartBeiJianActivity;
import com.ld.materialmanagement.activity.SparePartToolActivity;
import com.ld.materialmanagement.adapter.MMGoodsRecyclerViewAdapter;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.bean.LibBeans;
import com.ld.materialmanagement.bean.MMGoods;
import com.ld.materialmanagement.model.APi;
import com.ld.materialmanagement.model.Network;
import com.ld.materialmanagement.utils.CacheUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BillTian on 2017/11/21.
 */

public class SparePartTotalFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.rv_mm_goods)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_tool)
    Button mBtnTool;
    @BindView(R.id.btn_spare)
    Button mBtnSpare;

    private MMGoodsRecyclerViewAdapter adapter;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_sparepart_total;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        initSwipeRefresh(mSwipeRefresh);

        adapter=new MMGoodsRecyclerViewAdapter(mActivity);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        mRecyclerView.setAdapter(adapter);
        //界面初始化的时候刷新数据
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
        String json = CacheUtils.getStrCache(AppConst.Cache.LIST_GOODS);
        List<MMGoods> data= JSON.parseArray(json,MMGoods.class);
        if (data !=null){
            adapter.updateData(data);
        }
    }

    @OnClick({R.id.btn_tool, R.id.btn_spare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tool:
                Intent intentTool=new Intent(getActivity(), SparePartToolActivity.class);
                startActivity(intentTool);

                break;
            case R.id.btn_spare:
                Intent intentBeijian=new Intent(getActivity(), SparePartBeiJianActivity.class);
                startActivity(intentBeijian);

                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {

        APi api = Network.getInstance().getApi(mActivity);
        Call<LibBeans<MMGoods>> goodsList = api.getGoodsListGJ();

        goodsList.enqueue(new Callback<LibBeans<MMGoods>>() {
            @Override
            public void onResponse(Call<LibBeans<MMGoods>> call, Response<LibBeans<MMGoods>> response) {
                ArrayList<MMGoods> data = response.body().data;
                adapter.updateData(data);

                CacheUtils.setCache(AppConst.Cache.LIST_GOODS,data);

            }

            @Override
            public void onFailure(Call<LibBeans<MMGoods>> call, Throwable t) {
                Logger.e(t.getMessage());
            }
        });
    mSwipeRefresh.setRefreshing(false);
    }
}
