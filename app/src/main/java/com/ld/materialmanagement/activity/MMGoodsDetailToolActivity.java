package com.ld.materialmanagement.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.bean.GoodsInList;
import com.ld.materialmanagement.bean.GoodsOutList;
import com.ld.materialmanagement.bean.LibBeans;
import com.ld.materialmanagement.bean.MMGoods;
import com.ld.materialmanagement.model.APi;
import com.ld.materialmanagement.model.Network;
import com.ld.materialmanagement.widget.DescriptionImageView;
import com.ld.materialmanagement.widget.DescriptionView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BillTian on 2017/9/18.
 */

public class MMGoodsDetailToolActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.dv_name)
    DescriptionView mDvName;
    @BindView(R.id.dv_code)
    DescriptionView mDvCode;

    @BindView(R.id.dv_goods_type_name)
    DescriptionView mDvGoodsTypeName;
    @BindView(R.id.dv_spect_property)
    DescriptionView mDvSpectProperty;
    @BindView(R.id.dv_store_keeper_name)
    DescriptionView mDvStoreKeeperName;

    @BindView(R.id.dv_location_name)
    DescriptionView mDvLocationName;
    @BindView(R.id.dv_goods_place_no)
    DescriptionView mDvGoodsPlaceNo;
    @BindView(R.id.dv_current_balance)
    DescriptionView mDvCurrentBalance;
    @BindView(R.id.dv_measure_unit)
    DescriptionView mDvMeasureUnit;

    @BindView(R.id.dv_std_stock)
    DescriptionView mDvStdStock;

    @BindView(R.id.div_status)
    DescriptionImageView mDivStatus;
    @BindView(R.id.tb_mm_detail)
    TableLayout mTbMmDetail;

    @BindView(R.id.activity_mmgoods_detail)
    LinearLayout mActivityMmgoodsDetail;

    @BindView(R.id.dv_owner_area)
    DescriptionView mDvOwnerArea;



    private String id;
    private APi api;


    @Override
    protected int getContentView() {
        return R.layout.activity_mmgoods_detail_tool;
    }

    @Override
    protected boolean initBundle(Bundle extras) {
        id = String.valueOf(extras.getInt("id"));
        return true;
    }

    @Override
    protected void initWidget() {
        showWaitingDialog("联网中...");
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("备件详情");
    }

    @Override
    protected void initData() {

        api = Network.getInstance().getApi(MMGoodsDetailToolActivity.this);
        Call<MMGoods> singleMM = api.getSingleMM(id);

        singleMM.enqueue(new Callback<MMGoods>() {
            @Override
            public void onResponse(Call<MMGoods> call, Response<MMGoods> response) {

                hideWaitingDialog();

                if (response.isSuccessful()) {
                    MMGoods body = response.body();
                    //填充到ui上
                    fullData(body);

                } else {
                    alertErrorDialog("没有找到该备件");
                }
            }

            @Override
            public void onFailure(Call<MMGoods> call, Throwable t) {
                Logger.i(t.getMessage());
                alertErrorDialog("联网失败");
            }
        });
    }

    private void fullData(MMGoods goods) {
        //将数据填充到activity中
        mToolbar.setTitle(goods.Name);
        mDvName.setDescText(goods.Name);//名称

        Logger.i(goods.toString());
        //        1工具名称
        //        2物资编码
        //        3规格型号
        //        4库房
        //        5位置
        //        6定额数量
        //        7实际数量
        //        8单位
        //        9物资类型
        //        10保管员
        //        11状态（一周，未还标红）

        mDvCode.setDescText(goods.Code);//物资编码
        mDvGoodsTypeName.setDescText(goods.GoodsTypeName);//物资类型
        mDvSpectProperty.setDescText(goods.SpectProperty);//规格型号
        mDvStoreKeeperName.setDescText(goods.StoreKeeperName);//保管员
        mDvLocationName.setDescText(goods.LocationName);//库房
        mDvGoodsPlaceNo.setDescText(goods.GoodsPlaceNo);//位置
        mDvCurrentBalance.setDescText(goods.CurrentBalance);//实际数量
        mDvMeasureUnit.setDescText(goods.MeasureUnit);//单位
        mDvStdStock.setDescText(goods.StdStock);//定额数量

        mDivStatus.setDescState("normal".equals(goods.Status));//状态

        mDvOwnerArea.setDescText(goods.OwnerArea);

    }

    @OnClick({R.id.btn_in_detail,
            R.id.btn_out_detail
    })
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_in_detail:

                Call<LibBeans<GoodsInList>> goodsInList = api.getGoodsInList(id);
                goodsInList.enqueue(new Callback<LibBeans<GoodsInList>>() {
                    @Override
                    public void onResponse(Call<LibBeans<GoodsInList>> call, Response<LibBeans<GoodsInList>> response) {
                        if (response.isSuccessful()) {

                            ArrayList<GoodsInList> data = response.body().data;

                            SpannableString[] msgs;
                            if (data.size() > 0) {
                                msgs = new SpannableString[data.size()];

                                for (int i = 0; i < data.size(); i++) {
                                    GoodsInList goodsIn = data.get(i);
                                    msgs[i] = goodsIn.showInDialog();
                                }
                            } else {
                                msgs = new SpannableString[1];
                                msgs[0] = new SpannableString("暂时没有入库信息");
                            }


                            Logger.i(response.body().toString());

                            alertDetailDialog("入库详情", msgs);

                        }

                    }

                    @Override
                    public void onFailure(Call<LibBeans<GoodsInList>> call, Throwable t) {
                        alertErrorDialog("获取入库列表失败");
                        Logger.i(t.getMessage());
                    }
                });


                break;

            case R.id.btn_out_detail:

                Call<LibBeans<GoodsOutList>> goodsOutList = api.getGoodsOutList(id);

                goodsOutList.enqueue(new Callback<LibBeans<GoodsOutList>>() {
                    @Override
                    public void onResponse(Call<LibBeans<GoodsOutList>> call, Response<LibBeans<GoodsOutList>> response) {

                        if (response.isSuccessful()) {

                            Logger.i(response.body().toString());
                            ArrayList<GoodsOutList> data = response.body().data;

                            SpannableString[] msg;
                            if (data.size() > 0) {
                                msg = new SpannableString[data.size()];
                                for (int i = 0; i < data.size(); i++) {
                                    GoodsOutList goodsOut = data.get(i);

                                    msg[i] = goodsOut.showInDialog();
                                }
                            } else {
                                msg = new SpannableString[1];
                                msg[0] = new SpannableString("暂时没有出库信息");
                            }

                            alertDetailDialog("出库详情", msg);

                        }
                    }

                    @Override
                    public void onFailure(Call<LibBeans<GoodsOutList>> call, Throwable t) {
                        alertErrorDialog("获取出库列表失败");
                    }
                });

                break;
        }
    }

    public void alertDetailDialog(String title, SpannableString[] items) {
        AlertDialog dialog = new AlertDialog.Builder(MMGoodsDetailToolActivity.this)
                .setTitle(title)
                .setItems(items, null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
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
