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
import com.ld.materialmanagement.adapter.InDetailRecyclerViewAdapter;
import com.ld.materialmanagement.bean.GoodsInList;
import com.ld.materialmanagement.bean.InLibBean;
import com.ld.materialmanagement.bean.LibBeans;
import com.ld.materialmanagement.model.APi;
import com.ld.materialmanagement.model.Network;
import com.ld.materialmanagement.utils.L;
import com.ld.materialmanagement.utils.StringUtil;
import com.ld.materialmanagement.widget.DescriptionImageView;
import com.ld.materialmanagement.widget.DescriptionView;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ___/|
 * \o.O|   a cat
 * (___)
 * U
 * Created by Airmour@163.com on 2017/3/11.
 */

public class InLibDetails extends BaseActivity {

    public static final String EXTRA = "InLibDetails";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.dv_order_num)
    DescriptionView mDvOrderNum;
    @BindView(R.id.dv_in_date)
    DescriptionView mDvInDate;
    @BindView(R.id.dv_location_code)
    DescriptionView mDvLocationCode;
    @BindView(R.id.dv_money_ru_ku)
    DescriptionView mDvMoneyRuKu;
    @BindView(R.id.dv_money_shui_e)
    DescriptionView mDvMoneyShuiE;
    @BindView(R.id.dv_money_jia_shui_he_ji)
    DescriptionView mDvMoneyJiaShuiHeJi;
    @BindView(R.id.dv_supplier_name)
    DescriptionView mDvSupplierName;
    @BindView(R.id.dv_contract_code)
    DescriptionView mDvContractCode;
    @BindView(R.id.div_is_on_the_way)
    DescriptionImageView mDivIsOnTheWay;
    @BindView(R.id.dv_signer_cai_wu_ji_he)
    DescriptionView mDvSignerCaiWuJiHe;
    @BindView(R.id.dv_check_month)
    DescriptionView mDvCheckMonth;
    @BindView(R.id.div_is_audited)
    DescriptionImageView mDivIsAudited;
    @BindView(R.id.dv_invoice_no)
    DescriptionView mDvInvoiceNo;
    @BindView(R.id.dv_flow_status)
    DescriptionView mDvFlowStatus;
    @BindView(R.id.dv_description)
    DescriptionView mDvDescription;
    @BindView(R.id.dv_signer_ku_guan_yuan)
    DescriptionView mDvSignerKuGuanYuan;
    @BindView(R.id.dv_signer_wu_zi_zhu_guan)
    DescriptionView mDvSignerWuZiZhuGuan;
    @BindView(R.id.dv_person_jing_ban)
    DescriptionView mDvPersonJingBan;
    @BindView(R.id.tb_in_lib_item)
    TableLayout mTbInLibItem;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    private String id;
    private APi api;
    private InDetailRecyclerViewAdapter adapter;

    @Override
    protected boolean initBundle(Bundle extras) {
        id = extras.getString(EXTRA);
        L.e("InId........"+id);
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_in_lib;
    }

    @Override
    protected void initWidget() {
        adapter = new InDetailRecyclerViewAdapter(InLibDetails.this);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        api = Network.getInstance().getApi(InLibDetails.this);

        getDetail();
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("入库单详情");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideWaitingDialog();
    }

    public void getDetail() {

        showWaitingDialog("加载中...");

        Call<InLibBean> singleInOrder = api.getSingleInOrder(id);
        singleInOrder.enqueue(new Callback<InLibBean>() {
            @Override
            public void onResponse(Call<InLibBean> call, Response<InLibBean> response) {
                InLibBean bean = response.body();
                if (!TextUtils.isEmpty(bean.Code)){
//                if (bean.Code != null) {
                    fullData(bean);
                } else {
                    alertErrorDialog("入库单异常");
                }
            }

            @Override
            public void onFailure(Call<InLibBean> call, Throwable t) {
                alertErrorDialog("网络连接失败");

            }
        });

        Call<LibBeans<GoodsInList>> goodsInList = api.getInListGoods(id);
        goodsInList.enqueue(new Callback<LibBeans<GoodsInList>>() {
            @Override
            public void onResponse(Call<LibBeans<GoodsInList>> call, Response<LibBeans<GoodsInList>> response) {
                ArrayList<GoodsInList> data = response.body().data;
                if (data.size() > 0) {
                    adapter.updateData(data);
                }
                else {
                    alertErrorDialog("入库物资异常");
                }

            }

            @Override
            public void onFailure(Call<LibBeans<GoodsInList>> call, Throwable t) {
                alertErrorDialog("网络连接失败");
            }
        });

    }

    private void fullData(InLibBean bean) {

        mDvOrderNum.setDescText(bean.Code);
        mDvInDate.setDescText(StringUtil.formatDateString(bean.InDate));
        mDvLocationCode.setDescText(bean.LocationCode);
        mDvMoneyRuKu.setDescText(bean.MoneyRuKu + "");
        mDvMoneyShuiE.setDescText(bean.MoneyShuiE + "");
        mDvMoneyJiaShuiHeJi.setDescText(bean.MoneyJiaShuiHeJi + "");

        mDvSupplierName.setDescText(bean.SupplierName);

        mDvContractCode.setDescText(bean.ContractCode);
        mDivIsOnTheWay.setDescState(bean.IsOnTheWay);
        mDvSignerCaiWuJiHe.setDescText(bean.SignerCaiWuJiHe);
        mDvCheckMonth.setDescText(bean.CheckMonth);
        mDivIsAudited.setDescState(bean.IsAudited);
        mDvInvoiceNo.setDescText(bean.InvoiceNo);
        mDvFlowStatus.setDescText(bean.FlowStatus);
        mDvDescription.setDescText(bean.Description);
        mDvSignerKuGuanYuan.setDescText(bean.SignerKuGuanYuan);
        mDvSignerWuZiZhuGuan.setDescText(bean.SignerWuZiZhuGuan);
        mDvPersonJingBan.setDescText(bean.PersonJingBan);

        mDvSupplierName.setDescText(bean.SupplierName);
        mDvSupplierName.setDescTextSize(10f);

        hideWaitingDialog();
    }


    public static void show(Context context, String extras) {
        Intent intent = new Intent(context, InLibDetails.class);
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
