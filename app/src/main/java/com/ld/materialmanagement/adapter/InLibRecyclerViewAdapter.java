package com.ld.materialmanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.bean.InLibBean;
import com.ld.materialmanagement.utils.StringUtil;
import com.ld.materialmanagement.utils.UiUtils;
import com.ld.materialmanagement.widget.DescriptionView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InLibRecyclerViewAdapter extends RecyclerView.Adapter<InLibRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<InLibBean> mDatas = new ArrayList<>();

    public InLibRecyclerViewAdapter(Context context) {
        mContext = context;

        for (int i = 0; i < 5; i++) {
            // mDatas.add(new InLibBean());
        }
    }

    public void updateData(ArrayList<InLibBean> mDatas) {
        //this.mDatas.removeAll(this.mDatas);
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final InLibBean inLibBean = mDatas.get(position);

        holder.dvOrderNum.setDescText(inLibBean.Code); //单号
        holder.dvDate.setDescText(StringUtil.formatDateString(inLibBean.InDate)); //日期

       /*
        holder.dvName.setDescText(inLibBean.dvName); // 名称
        holder.dvType.setDescText(inLibBean.dvType);//规格型号
        holder.dvUnit.setDescText(inLibBean.dvUnit);//计量单位
        holder.dvCount.setDescText(inLibBean.dvCount);//数量
        holder.dvUnitPrice.setDescText(inLibBean.dvUnitPrice);//单价
        holder.dvMoneySum.setDescText(inLibBean.dvMoneySum);//金额
        */

        holder.tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showInLibDetailActivity(mContext, inLibBean.Id + "");
            }
        });

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_in_lib, parent, false);
        return new MyViewHolder(view);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tb_in_lib_item)
        public TableLayout tableLayout;
        @BindView(R.id.dv_order_num)
        public DescriptionView dvOrderNum; //单号
        @BindView(R.id.dv_date)
        public DescriptionView dvDate; //日期
       /*
        @BindView(R.id.dv_name)
        public DescriptionView dvName; // 名称
        @BindView(R.id.dv_type)
        public DescriptionView dvType;//规格型号
        @BindView(R.id.dv_unit)
        public DescriptionView dvUnit;//计量单位
        @BindView(R.id.dv_count)
        public DescriptionView dvCount;//数量
        @BindView(R.id.dv_unit_price)
        public DescriptionView dvUnitPrice;//单价
        @BindView(R.id.dv_money_sum)
        public DescriptionView dvMoneySum;//金额
        */


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}