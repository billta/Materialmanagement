package com.ld.materialmanagement.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ld.materialmanagement.R;
import com.ld.materialmanagement.bean.FirstChart;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;


public class ChartRecyclerViewAdapter extends RecyclerView.Adapter<ChartRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private FirstChart firstChart = new FirstChart();

    String[] mParties = new String[]{
            "台账金额", "本月入库", "本月出库"
    };

    public ChartRecyclerViewAdapter(Context context) {
        mContext = context;

        for (int i = 0; i < 5; i++) {
            // mDatas.add(new InLibBean());
        }

    }

    public void updateData(FirstChart firstChart) {
        //this.firstChart = firstChart;
        Logger.i("updateData"+firstChart.toString());

        this.firstChart.imoney = firstChart.imoney;
        this.firstChart.omoney = firstChart.omoney;
        this.firstChart.money = firstChart.money;

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       /* //设置内容布局的宽为屏幕宽度
        //holder.layout_content.getLayoutParams().width = UiUtils.getScreenWidth(mContext);
        if (0 == position && getItemCount() == 1) {
            //如果是第一个 让查看更多显示
            holder.mTvChartTitle.setText("");
        } else {
            holder.mTvChartTitle.setText("");
        }

        //初始化统计图
        setData(holder.pieChart);

        // add a selection listener
        holder.pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                if (e == null)
                    return;

                Logger.i("Value: " + e.getY() + ", index: " + h.getX() + ", DataSet index: " + h.getDataSetIndex());
                Snackbar.make(holder.pieChart.getRootView(), "金额:" + e.getY() + "元", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
*/

    }


    private void setData(PieChart mChart) {

        Logger.i("这个方法走了"+firstChart.toString());
        ArrayList<PieEntry> entries = new ArrayList<>();


        entries.add(new PieEntry((float) firstChart.money, mParties[0]));
        entries.add(new PieEntry((float) firstChart.imoney, mParties[1]));
        entries.add(new PieEntry((float) firstChart.omoney, mParties[2]));


        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);


        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chart, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }

      /*  @BindView(R.id.item_bar_chart)
        public BarChart barChart;

        @BindView(R.id.chart_title)
        public TextView mTvChartTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            initChart(barChart);
        }

        private void initChart(BarChart mChart) {
            mChart.setDrawBarShadow(false);
            mChart.setDrawValueAboveBar(true);
            mChart.setNoDataText("请刷新...");
            mChart.getDescription().setEnabled(false);

            // if more than 3 entries are displayed in the chart, no values will be
            // drawn
            mChart.setMaxVisibleValueCount(3);

            // 优化双指缩放
            mChart.setPinchZoom(false);

            mChart.setDrawGridBackground(false);
            //mChart.setDrawYLabels(false);

            IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            //xAxis.setTypeface(mTfLight);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f); // only intervals of 1 day
            xAxis.setLabelCount(7);
            xAxis.setValueFormatter(xAxisFormatter);

            DecimalFormat mFormat = new DecimalFormat("###,###,###,##0.0");

            IAxisValueFormatter custom = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return mFormat.format(value) + " ";;
                }

                @Override
                public int getDecimalDigits() {
                    return 0;
                }
            };

            YAxis leftAxis = mChart.getAxisLeft();
            //leftAxis.setTypeface(mTfLight);
            leftAxis.setLabelCount(8, false);
            leftAxis.setValueFormatter(custom);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);
            leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            //rightAxis.setTypeface(mTfLight);
            rightAxis.setLabelCount(8, false);
            rightAxis.setValueFormatter(custom);
            rightAxis.setSpaceTop(15f);
            rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            Legend l = mChart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setForm(Legend.LegendForm.SQUARE);
            l.setFormSize(9f);
            l.setTextSize(11f);
            l.setXEntrySpace(4f);
            // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
            // "def", "ghj", "ikl", "mno" });
            // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
            // "def", "ghj", "ikl", "mno" });

            XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
            mv.setChartView(mChart); // For bounds control
            mChart.setMarker(mv); // Set the marker to the chart

            setData(3, 50);

            // setting data

            // mChart.setDrawLegend(false);


        }

        private void initPieChart(PieChart mChart) {
            mChart.setNoDataText("请刷新...");
            mChart.setUsePercentValues(true);
            mChart.getDescription().setEnabled(false);
            mChart.setExtraOffsets(5, 10, 5, 5);

            mChart.setDragDecelerationFrictionCoef(0.95f);

            mChart.setCenterText("本月资金统计");

            mChart.setDrawHoleEnabled(true);
            mChart.setHoleColor(Color.WHITE);

            mChart.setTransparentCircleColor(Color.WHITE);
            mChart.setTransparentCircleAlpha(110);

            mChart.setHoleRadius(58f);
            mChart.setTransparentCircleRadius(61f);

            mChart.setDrawCenterText(true);

            mChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            mChart.setRotationEnabled(true);
            mChart.setHighlightPerTapEnabled(true);

            // mChart.setUnit(" ￥");
            // mChart.setDrawUnitsInChart(true);

            mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
            // mChart.spin(2000, 0, 360);

            Legend l = mChart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(false);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

            // entry label styling
            mChart.setEntryLabelColor(Color.WHITE);
            mChart.setEntryLabelTextSize(12f);
        }*/

    }

}