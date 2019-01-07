package com.ld.materialmanagement.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.adapter.InLibRecyclerViewAdapter;
import com.ld.materialmanagement.adapter.MMGoodsRecyclerViewAdapter;
import com.ld.materialmanagement.adapter.OutLibRecyclerViewAdapter;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.bean.InLibBean;
import com.ld.materialmanagement.bean.LibBeans;
import com.ld.materialmanagement.bean.MMGoods;
import com.ld.materialmanagement.bean.OutLibBean;
import com.ld.materialmanagement.model.APi;
import com.ld.materialmanagement.model.Network;
import com.ld.materialmanagement.utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {

    public static final int BACKUP = 0x001;
    public static final int IN_LIB = 0x002;
    public static final int OUT_LIB = 0x003;

    public static final int SHOW = 0x004;
    public static final int HIDE = 0x005;

    public static final String FLAG_IN_LIB = "入库";
    public static final String FLAG_OUT_LIB = "出库";
    public static final String FLAG_LOAN_IN = "归还";
    public static final String FLAG_LOAN_OUT = "借出";


    //搜索备件时的spinner
    public static final String[] GOODS = {
            "物资名称",
            "规格型号",
            "保管员",
            "物资编码"};
    public static final String[] GOODSMAP = {
            "Name",
            "SpectProperty",
            "StoreKeeperName",
            "Code"};

    //搜索入库时的spinner
    public static final String[] INLIB = {
            "入库单号",
            "合同编号",
            "公司编号",
            "供货单位",
            "仓库编号",
            "发票号"

    };
    public static final String[] INLIBMAP = {
            "Code",
            "ContactCode",
            "CompanyId",
            "SupplierName",
            "InvoiceNo",
            "LocationCode"

    };

    //搜索出库时的spinner
    public static final String[] OUTLIB = {
            "出库单号", "合同编号",
            "合同名称", "公司编号",
            "供货单位", "申请单位",
            "领用部门", "预算编号"

    };

    public static final String[] OUTLIBMAP = {
            "Code", "ContactCode",
            "ContactName", "CompanyId",
            "SupplierName", "ShenQingDanWei",
            "LingYongBuMen", "PlanCode"

    };

    @BindView(R.id.sv_search)
    SearchView mSearch;
    @BindView(R.id.spinner)
    AppCompatSpinner mSpinner;
    @BindView(R.id.rv_search_result)
    RecyclerView mRecyclerView;

    public int flag;

    SharedPreferences sp;
    String check;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SHOW:
                    showWaitingDialog("搜索中...");
                    break;

                case HIDE:
                    hideWaitingDialog();
                    break;
            }
        }
    };
    private ArrayAdapter<String> adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean initBundle(Bundle extras) {
        //
        sp=getSharedPreferences(AppConst.FLAG_CHECKED,MODE_PRIVATE);
        check = sp.getString(AppConst.FLAG_CHECKED, null);
        flag = extras.getInt(AppConst.FLAG_SEARCH);
        return true;
    }


    @Override
    protected void initWidget() {

        adapter = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_spinner_dropdown_item);

        switch (flag) {
            case BACKUP:
                mSearch.setQueryHint("搜物资台账");
                adapter.addAll(GOODS);
                break;
            case IN_LIB:
                mSearch.setQueryHint("搜备件");
                if (check.equals("入库")){
                    adapter.addAll(INLIB);
                }else if (check.equals("出库")){
                    adapter.addAll(OUTLIB);
                }

                break;
            case OUT_LIB:
                mSearch.setQueryHint("搜工具");
                if (check.equals("归还")){
                    adapter.addAll(INLIB);
                }else if (check.equals("借出")){
                    adapter.addAll(OUTLIB);
                }

                break;
        }

        mSpinner.setAdapter(adapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    search(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    //隐藏输入法键盘
    public void hideIMM() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearch.getWindowToken(), 0);
    }

    public boolean checkKeyWord() {
        CharSequence keyWord = mSearch.getQuery();
        if (TextUtils.isEmpty(keyWord)) {
            Toast.makeText(mContext, "你想搜点什么~", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void search(String query) {

        switch (flag) {
            case BACKUP:
                searchGoods(query);
                break;
            case IN_LIB:
                if (check.equals("入库")){
                    searchInLib(query);
                }else if (check.equals("出库")){
                    searchOutLib(query);
                }

                break;
            case OUT_LIB:
                if (check.equals("归还")){
                    searchInLibLoan(query);
                }else if (check.equals("借出")){
                    searchOutLibLoan(query);
                }
                break;
        }
    }

    /**
     * 搜索归还单
     */
    public void searchInLibLoan(String query) {
        handler.sendEmptyMessage(SHOW);

        APi api = Network.getInstance().getApi(mContext);

        Map<String, String> fields = new HashMap<>();
        fields.put(INLIBMAP[mSpinner.getSelectedItemPosition()], query);

        Call<LibBeans<InLibBean>> libBeansCall = api.searchInLib(fields,FLAG_LOAN_IN);

        libBeansCall.enqueue(new Callback<LibBeans<InLibBean>>() {
            @Override
            public void onResponse(Call<LibBeans<InLibBean>> call, Response<LibBeans<InLibBean>> response) {
                ArrayList<InLibBean> data = response.body().data;
                if (data.size() > 0) {
                    handler.sendEmptyMessage(HIDE);

                    InLibRecyclerViewAdapter adapter = new InLibRecyclerViewAdapter(mContext);
                    mRecyclerView.setAdapter(adapter);
                    adapter.updateData(data);

                } else {
                    onSearchFailure();
                }
            }

            @Override
            public void onFailure(Call<LibBeans<InLibBean>> call, Throwable t) {
                onConnectFailure();
            }
        });
    }
    /**
     * 搜索借出单
     */
    public void searchOutLibLoan(String query) {
        handler.sendEmptyMessage(SHOW);
        APi api = Network.getInstance().getApi(mContext);
        Map<String, String> fields = new HashMap<>();
        fields.put(OUTLIBMAP[mSpinner.getSelectedItemPosition()], query);
        Call<LibBeans<OutLibBean>> libBeansCall = api.searchOutLib(fields,FLAG_LOAN_OUT);
        libBeansCall.enqueue(new Callback<LibBeans<OutLibBean>>() {
            @Override
            public void onResponse(Call<LibBeans<OutLibBean>> call, Response<LibBeans<OutLibBean>> response) {
                ArrayList<OutLibBean> data = response.body().data;
                if (data.size() > 0) {
                    handler.sendEmptyMessage(HIDE);

                    OutLibRecyclerViewAdapter adapter = new OutLibRecyclerViewAdapter(mContext);
                    mRecyclerView.setAdapter(adapter);
                    adapter.updateData(data);

                } else {
                    onSearchFailure();
                }
            }

            @Override
            public void onFailure(Call<LibBeans<OutLibBean>> call, Throwable t) {
                onConnectFailure();
            }
        });
    }

    /**
     * 搜索入库单
     * @param query
     */
    public void searchInLib(String query) {
        handler.sendEmptyMessage(SHOW);

        APi api = Network.getInstance().getApi(mContext);

        Map<String, String> fields = new HashMap<>();
        fields.put(INLIBMAP[mSpinner.getSelectedItemPosition()], query);

        Call<LibBeans<InLibBean>> libBeansCall = api.searchInLib(fields,FLAG_IN_LIB);

        libBeansCall.enqueue(new Callback<LibBeans<InLibBean>>() {
            @Override
            public void onResponse(Call<LibBeans<InLibBean>> call, Response<LibBeans<InLibBean>> response) {
                ArrayList<InLibBean> data = response.body().data;
                if (data.size() > 0) {
                    handler.sendEmptyMessage(HIDE);

                    InLibRecyclerViewAdapter adapter = new InLibRecyclerViewAdapter(mContext);
                    mRecyclerView.setAdapter(adapter);
                    adapter.updateData(data);

                } else {
                    onSearchFailure();
                }
            }

            @Override
            public void onFailure(Call<LibBeans<InLibBean>> call, Throwable t) {
                onConnectFailure();
            }
        });
    }

    /**
     * 搜索出库单
     * @param query
     */
    public void searchOutLib(String query) {
        handler.sendEmptyMessage(SHOW);
        APi api = Network.getInstance().getApi(mContext);
        Map<String, String> fields = new HashMap<>();
        fields.put(OUTLIBMAP[mSpinner.getSelectedItemPosition()], query);
        Call<LibBeans<OutLibBean>> libBeansCall = api.searchOutLib(fields,FLAG_OUT_LIB);
        libBeansCall.enqueue(new Callback<LibBeans<OutLibBean>>() {
            @Override
            public void onResponse(Call<LibBeans<OutLibBean>> call, Response<LibBeans<OutLibBean>> response) {
                ArrayList<OutLibBean> data = response.body().data;
                if (data.size() > 0) {
                    handler.sendEmptyMessage(HIDE);

                    OutLibRecyclerViewAdapter adapter = new OutLibRecyclerViewAdapter(mContext);
                    mRecyclerView.setAdapter(adapter);
                    adapter.updateData(data);

                } else {
                    onSearchFailure();
                }
            }

            @Override
            public void onFailure(Call<LibBeans<OutLibBean>> call, Throwable t) {
                onConnectFailure();
            }
        });
    }

    /**
     * 搜索物资台账
     * @param query
     */
    public void searchGoods(String query) {
        handler.sendEmptyMessage(SHOW);

        APi api = Network.getInstance().getApi(mContext);
        Map<String, String> fields = new HashMap<>();
        fields.put(GOODSMAP[mSpinner.getSelectedItemPosition()], query);

        Call<LibBeans<MMGoods>> goodsList = api.searchGoods(fields);
        goodsList.enqueue(new Callback<LibBeans<MMGoods>>() {
            @Override
            public void onResponse(Call<LibBeans<MMGoods>> call, Response<LibBeans<MMGoods>> response) {
                handler.sendEmptyMessage(HIDE);

                ArrayList<MMGoods> data = response.body().data;
                L.e("bbbbbbbbbbbbb"+data);
                if (data.size() > 0) {
                    handler.sendEmptyMessage(HIDE);
                    MMGoodsRecyclerViewAdapter adapter = new MMGoodsRecyclerViewAdapter(mContext);
                    mRecyclerView.setAdapter(adapter);
                    adapter.updateData(data);

                } else {
                    onSearchFailure();
                }
            }

            @Override
            public void onFailure(Call<LibBeans<MMGoods>> call, Throwable t) {
                onConnectFailure();
            }
        });
    }

    public void onConnectFailure() {
        handler.sendEmptyMessage(HIDE);
        Toast.makeText(mContext, "网络连接失败", Toast.LENGTH_SHORT).show();
    }

    public void onSearchFailure() {
        handler.sendEmptyMessage(HIDE);
        Toast.makeText(mContext, "没有搜到想要的内容", Toast.LENGTH_SHORT).show();
    }
}
