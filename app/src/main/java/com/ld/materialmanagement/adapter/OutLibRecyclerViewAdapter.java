package com.ld.materialmanagement.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.bean.OutLibBean;
import com.ld.materialmanagement.utils.StringUtil;
import com.ld.materialmanagement.utils.UiUtils;
import com.ld.materialmanagement.widget.DescriptionView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OutLibRecyclerViewAdapter extends RecyclerView.Adapter<OutLibRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<OutLibBean> mDatas = new ArrayList<>();
    SharedPreferences sp;

    public OutLibRecyclerViewAdapter(Context context) {
        mContext = context;

        for (int i = 0; i < 5; i++) {
            // mDatas.add(new InLibBean());
        }
    }

    public void updateData(ArrayList<OutLibBean> mDatas) {
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
        //设置内容布局的宽为屏幕宽度
        //holder.layout_content.getLayoutParams().width = UiUtils.getScreenWidth(mContext);

        final OutLibBean outLibBean = mDatas.get(position);

        holder.dvOrderNum.setDescText(outLibBean.Code); //单号
        holder.dvDate.setDescText(StringUtil.formatDateString(outLibBean.OutDate)); //日期


        /*
        holder.dvName.setDescText(outLibBean.dvName); // 名称
        holder.dvType.setDescText(outLibBean.dvType);//规格型号
        holder.dvUnit.setDescText(outLibBean.dvUnit);//计量单位
        holder.dvCount.setDescText(outLibBean.dvCount);//数量
        holder.dvUnitPrice.setDescText(outLibBean.dvUnitPrice);//单价
        holder.dvMoneySum.setDescText(outLibBean.dvMoneySum);//金额
        */
        sp = mContext.getSharedPreferences("checked", Context.MODE_PRIVATE);
        String checked = sp.getString("checked", null);
        holder.tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UiUtils.showOutLibDetailActivity(mContext, outLibBean.Id + "");
            }
        });
        //一周未还标红
        //获取毫秒值
//        long currentTimeMillis = System.currentTimeMillis();
//        String s = StringUtil.formatDateString(outLibBean.OutDate);
//        Calendar calendar = Calendar.getInstance();
//        try {
//            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(s));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long timeInMillis = calendar.getTimeInMillis();
//        long l = currentTimeMillis - timeInMillis;
//        L.e("LLLLLLLLLLLLLl"+l);
//        if (checked.equals("借出") && (l > 604800000)) {
//            //holder.tableLayout.setBackgroundColor(Color.parseColor("#e11a1a"));
//
//            APi api = Network.getInstance().getApi(mContext);
//            Call<LoanBeans<LoanRed>> userBack = api.getUserBack();
//            userBack.enqueue(new Callback<LoanBeans<LoanRed>>() {
//                @Override
//                public void onResponse(Call<LoanBeans<LoanRed>> call, Response<LoanBeans<LoanRed>> response) {
//                    LoanBeans<LoanRed> body = response.body();
//                    L.e("body>>>>>>>>>"+body);
//                    // holder.tableLayout.setBackgroundColor(Color.parseColor("#e11a1a"));
//                    //holder.tableLayout.setBackgroundColor(Color.rgb(255, 0, 0));
//                }
//
//                @Override
//                public void onFailure(Call<LoanBeans<LoanRed>> call, Throwable t) {
//
//                }
//            });
//
//
//        }



    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_out_lib, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tb_out_lib_item)
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