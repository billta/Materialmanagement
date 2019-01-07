package com.ld.materialmanagement.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.bean.GoodsInList;
import com.ld.materialmanagement.bean.GoodsOutList;
import com.ld.materialmanagement.widget.DescriptionView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OutDetailRecyclerViewAdapter extends RecyclerView.Adapter<OutDetailRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<GoodsOutList> mDatas = new ArrayList<>();

    public OutDetailRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void updateData(ArrayList<GoodsOutList> mDatas) {
        //this.mDatas.removeAll(this.mDatas);
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_out_lib_backup, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final GoodsOutList mmGoods = mDatas.get(position);

        //显示一个数量 一个名称
        holder.dvCode.setDescText(mmGoods.GoodsCode);
        holder.dvName.setDescText(mmGoods.Name);
        holder.dvCurrentBalance.setDescText(TextUtils.isEmpty(mmGoods.CurrentBalance)?"未知":mmGoods.CurrentBalance);
        holder.dvADCount.setDescText(mmGoods.ADCount + mmGoods.MeasureName);
        holder.dvODCount.setDescText(mmGoods.ODCount + mmGoods.MeasureName);
        holder.dvSpectProperty.setDescText(mmGoods.SpectProperty);

        holder.tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  弹出dialog显示详情

            }
        });
    }

    public void alertDetailDialog(String title, SpannableString items) {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(items)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tb_mm_goods_item)
        public TableLayout tableLayout;
        @BindView(R.id.dv_goods_code)
        public DescriptionView dvCode;          //物资编码    "113",
        @BindView(R.id.dv_current_balance)
        public DescriptionView dvCurrentBalance;   //数量    10,
        @BindView(R.id.dv_name)
        public DescriptionView dvName;         //物资名称     "量筒白色",
        @BindView(R.id.dv_spect_property)
        public DescriptionView dvSpectProperty;
        @BindView(R.id.dv_ad_count)
        public DescriptionView dvADCount;
        @BindView(R.id.dv_od_count)
        public DescriptionView dvODCount;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}