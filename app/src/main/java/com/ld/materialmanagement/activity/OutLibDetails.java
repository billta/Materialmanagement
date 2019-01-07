package com.ld.materialmanagement.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TableLayout;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.adapter.OutDetailRecyclerViewAdapter;
import com.ld.materialmanagement.bean.GoodsOutList;
import com.ld.materialmanagement.bean.LibBeans;
import com.ld.materialmanagement.bean.OutLibBean;
import com.ld.materialmanagement.model.APi;
import com.ld.materialmanagement.model.Network;
import com.ld.materialmanagement.utils.L;
import com.ld.materialmanagement.utils.StringUtil;
import com.ld.materialmanagement.widget.DescriptionView;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ld.materialmanagement.activity.InLibDetails.EXTRA;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/3/11.
 */

public class OutLibDetails extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.dv_code)
    DescriptionView mDvCode;
    @BindView(R.id.dv_out_date)
    DescriptionView mDvOutDate;
    @BindView(R.id.dv_shen_qing_dan_wei)
    DescriptionView mDvShenQingDanWei;
    @BindView(R.id.dv_contract_code)
    DescriptionView mDvContractCode;
    @BindView(R.id.dv_company_id)
    DescriptionView mDvCompanyId;
    @BindView(R.id.dv_ling_yong_bu_men)
    DescriptionView mDvLingYongBuMen;
    @BindView(R.id.dv_supplier_name)
    DescriptionView mDvSupplierName;
    @BindView(R.id.dv_plan_code)
    DescriptionView mDvPlanCode;
    @BindView(R.id.dv_signer_bu_men)
    DescriptionView mDvSignerBuMen;
    @BindView(R.id.dv_signer_wu_zi_zhu_guan)
    DescriptionView mDvSignerWuZiZhuGuan;
    @BindView(R.id.dv_signer_jing_ban_ben)
    DescriptionView mDvSignerJingBanBen;
    @BindView(R.id.dv_SignerKuGuanYuan)
    DescriptionView mDvSignerKuGuanYuan;
    @BindView(R.id.tb_in_lib_item)
    TableLayout mTbInLibItem;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    private String id;
    private APi api;
    private OutDetailRecyclerViewAdapter adapter;

    @Override
    protected boolean initBundle(Bundle extras) {
        id = extras.getString(EXTRA);
        L.e("outid........"+id);
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_out_lib;
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("出库单详情");
    }

    @Override
    protected void initWidget() {
        adapter = new OutDetailRecyclerViewAdapter(OutLibDetails.this);

        mRecycleView.setLayoutManager(new LinearLayoutManager(OutLibDetails.this));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(adapter);

    }



    @Override
    protected void initData() {
        api = Network.getInstance().getApi(OutLibDetails.this);
        getDetail();
    }

    public void getDetail() {

        Call<OutLibBean> singleOutOrder = api.getSingleOutOrder(id);
        singleOutOrder.enqueue(new Callback<OutLibBean>() {
            @Override
            public void onResponse(Call<OutLibBean> call, Response<OutLibBean> response) {
                OutLibBean body = response.body();
                if (!TextUtils.isEmpty(body.Code)) {
                    fullData(body);
                } else {
                    alertErrorDialog("出库单异常");
                }
            }

            @Override
            public void onFailure(Call<OutLibBean> call, Throwable t) {
                alertErrorDialog("网络连接失败");
            }
        });

        Call<LibBeans<GoodsOutList>> goodsOutList = api.getOutListGoods(id);
        goodsOutList.enqueue(new Callback<LibBeans<GoodsOutList>>() {
            @Override
            public void onResponse(Call<LibBeans<GoodsOutList>> call, Response<LibBeans<GoodsOutList>> response) {
                ArrayList<GoodsOutList> data = response.body().data;
                if(data.size()>0){
                    adapter.updateData(data);
                }else{
                    alertErrorDialog("出库物资异常");
                }
            }

            @Override
            public void onFailure(Call<LibBeans<GoodsOutList>> call, Throwable t) {
                alertErrorDialog("网络连接失败");
            }
        });


    }

    private void fullData(OutLibBean bean) {

        mDvCode.setDescText(bean.Code);
        mDvOutDate.setDescText(StringUtil.formatDateString(bean.OutDate));
        mDvShenQingDanWei.setDescText(bean.ShenQingDanWei);
        mDvContractCode.setDescText(bean.ContractCode);
        mDvCompanyId.setDescText(bean.CompanyId);
        mDvLingYongBuMen.setDescText(bean.LingYongBuMen);
        mDvSupplierName.setDescText(bean.SupplierName);
        mDvPlanCode.setDescText(bean.PlanCode);
        mDvSignerBuMen.setDescText(bean.SignerBuMen);
        mDvSignerWuZiZhuGuan.setDescText(bean.SignerWuZiZhuGuan);
        mDvSignerJingBanBen.setDescText(bean.SignerJingBanRen);
        mDvSignerKuGuanYuan.setDescText(bean.SignerKuGuanYuan);

    }

    public static void show(Context context, String extras) {
        Intent intent = new Intent(context, OutLibDetails.class);
        if (extras != null) {
            intent.putExtra(EXTRA, extras);
        }
        context.startActivity(intent);
    }

    private void alertErrorDialog(String msg) {
        showMaterialDialog("错误", msg, null, "确定", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
