package com.ld.materialmanagement.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ld.materialmanagement.R;
import com.ld.materialmanagement.bean.ScanBean;
import com.ld.materialmanagement.utils.UiUtils;
import com.ld.materialmanagement.widget.AmountView;
import com.ld.materialmanagement.widget.SlidingButtonView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ScanRecyclerViewAdapter extends RecyclerView.Adapter<ScanRecyclerViewAdapter.MyViewHolder> implements SlidingButtonView.IonSlidingButtonListener {
    private Context mContext;
    private SlidingItemClickListener mIDeleteBtnClickListener;
    private List<ScanBean> mDatas = new ArrayList<>();
    private SlidingButtonView mMenu = null;
    SharedPreferences sp;

    public ScanRecyclerViewAdapter(Context context) {
        mContext = context;
        mIDeleteBtnClickListener = (SlidingItemClickListener) context;
        for (int i = 0; i < 20; i++) {
            //mDatas.add("第" + i + "个测试");
        }
    }



    public void updateData(List<ScanBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public void updateData(int goodsId) {

        for (ScanBean bean : mDatas) {
            if (bean.goodsId == goodsId) {
                bean.selectCount = bean.selectCount + 1;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ScanBean scanBean = mDatas.get(position);
        //做一个标记
        sp=mContext.getSharedPreferences("checked", Context.MODE_PRIVATE);
        final String checked = sp.getString("checked", null);
        holder.textView.setText(scanBean.toString());
        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = UiUtils.getScreenWidth(mContext);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }
                Toast.makeText(mContext,checked,Toast.LENGTH_SHORT).show();
            }
        });
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnClick(v, n);
            }
        });

        //设置最大数为库存余量
        holder.amountView.setGoods_storage(99);

        holder.amountView.setAmountCount(scanBean.selectCount);

        holder.amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                //当这里发生改变
                scanBean.selectCount = amount;
                //Toast.makeText(mContext, "Amount=>  " + amount, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_scan_result, arg0, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public List<ScanBean> getData() {
        return mDatas;
    }

    public void clearList() {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_delete)
        public TextView btn_Delete;
        @BindView(R.id.text)
        public TextView textView;
        @BindView(R.id.av_count)
        AmountView amountView;
        @BindView(R.id.layout_content)
        public ViewGroup layout_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ((SlidingButtonView) itemView).setSlidingButtonListener(ScanRecyclerViewAdapter.this);

        }
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     *
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if (menuIsOpen()) {
            if (mMenu != slidingButtonView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;
    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }

    public interface SlidingItemClickListener {
        void onItemClick(View view, int position);

        void onDeleteBtnClick(View view, int position);
    }

}