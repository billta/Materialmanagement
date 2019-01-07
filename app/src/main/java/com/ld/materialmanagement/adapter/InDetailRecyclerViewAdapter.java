package com.ld.materialmanagement.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.activity.MMGoodsDetailActivity;
import com.ld.materialmanagement.bean.GoodsInList;
import com.ld.materialmanagement.widget.DescriptionView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InDetailRecyclerViewAdapter extends RecyclerView.Adapter<InDetailRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<GoodsInList> mDatas = new ArrayList<>();

    public InDetailRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void updateData(ArrayList<GoodsInList> mDatas) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_in_lib_backup, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final GoodsInList mmGoods = mDatas.get(position);

        //显示一个数量 一个名称
        holder.dvCode.setDescText(mmGoods.GoodsCode);
        holder.dvName.setDescText(mmGoods.Name);
        holder.dvCurrentBalance.setDescText(mmGoods.StorageCount+mmGoods.MeasureName);

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
        @BindView(R.id.dv_code)
        public DescriptionView dvCode;          //物资编码    "113",
        @BindView(R.id.dv_current_balance)
        public DescriptionView dvCurrentBalance;   //数量    10,
        @BindView(R.id.dv_name)
        public DescriptionView dvName;         //物资名称     "量筒白色",


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}