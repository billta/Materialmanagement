package com.ld.materialmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.activity.MMGoodsDetailActivity;
import com.ld.materialmanagement.activity.MMGoodsDetailToolActivity;
import com.ld.materialmanagement.bean.MMGoods;
import com.ld.materialmanagement.widget.DescriptionView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MMGoodsRecyclerViewAdapter extends RecyclerView.Adapter<MMGoodsRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<MMGoods> mDatas = new ArrayList<>();

    public MMGoodsRecyclerViewAdapter(Context context) {
        mContext = context;

        for (int i = 0; i < 5; i++) {
            // mDatas.add(new InLibBean());
        }
    }

    public void updateData(List<MMGoods> mDatas) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_backup, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MMGoods mmGoods = mDatas.get(position);

        holder.dvCode.setDescText(mmGoods.Code);
        holder.dvSpectProperty.setDescText(mmGoods.SpectProperty);
        holder.dvLocationName.setDescText(mmGoods.LocationName.replace("库房","-") + mmGoods.GoodsPlaceNo);
        //holder.dvGoodsPlaceNo.setDescText(mmGoods.GoodsPlaceNo);
        holder.dvCurrentBalance.setDescText(mmGoods.CurrentBalance + " " + mmGoods.MeasureUnit);
        //holder.dvMeasureUnit.setDescText(mmGoods.MeasureUnit);
        holder.dvName.setDescText(mmGoods.Name);
        holder.dvCategoryName.setDescText(mmGoods.CategoryName);

        int a = Integer.parseInt(mmGoods.CurrentBalance);
        int b = Integer.parseInt(mmGoods.StdStock);

        //if (a<b&&mmGoods.CategoryName.equals("工具")){

//        final LabelView labelView = new LabelView(mContext);
//        labelView.setText("借");
//        labelView.setBackgroundColor(0xffff5722);
//        labelView.setTargetView(holder.dvCategoryName, 5, LabelView.Gravity.RIGHT_TOP);
       // }


        holder.tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传入一个id
                if (mmGoods.CategoryName.equals("备件")) {
                    Intent intent = new Intent(mContext, MMGoodsDetailActivity.class);
                    intent.putExtra("id", mmGoods.Id);
                    mContext.startActivity(intent);
                } else if (mmGoods.CategoryName.equals("工具")) {
                    Intent intent = new Intent(mContext, MMGoodsDetailToolActivity.class);
                    intent.putExtra("id", mmGoods.Id);
                    mContext.startActivity(intent);
                }
                //                else if(mmGoods.CategoryName.equals(null)){
                //                    Intent intent=new Intent(mContext,MMGoodsDetailToolActivity.class);
                //                    intent.putExtra("id",mmGoods.Id);
                //                    mContext.startActivity(intent);
                //                   // Toast.makeText(mContext,"00000",Toast.LENGTH_SHORT).show();
                //                }

            }
        });
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tb_mm_goods_item)
        public TableLayout tableLayout;
        @BindView(R.id.dv_code)
        public DescriptionView dvCode;          //物资编码    "113",
        @BindView(R.id.dv_spect_property)
        public DescriptionView dvSpectProperty;  //规格型号    "/",
        @BindView(R.id.dv_location_name)
        public DescriptionView dvLocationName;    //仓库名称    "油",
        //@BindView(R.id.dv_goods_place_no)
        //public DescriptionView dvGoodsPlaceNo;    //位置号    "14",
        //@BindView(R.id.dv_measure_unit)
        //public DescriptionView dvMeasureUnit;     //计量单位   "个",
        @BindView(R.id.dv_current_balance)
        public DescriptionView dvCurrentBalance;   //数量    10,
        @BindView(R.id.dv_name)
        public DescriptionView dvName;         //物资名称     "量筒白色",
        //@BindView(R.id.tv_details)
        //public TextView tvDetail;
        @BindView(R.id.dv_category_name)
        public DescriptionView dvCategoryName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}