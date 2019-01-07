package com.ld.materialmanagement.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ld.materialmanagement.R;
import com.ld.materialmanagement.adapter.ChartRecyclerViewAdapter;
import com.ld.materialmanagement.application.AppConst;
import com.ld.materialmanagement.bean.FirstChart;
import com.ld.materialmanagement.bean.SecondChart;
import com.ld.materialmanagement.model.APi;
import com.ld.materialmanagement.model.Network;
import com.ld.materialmanagement.utils.CacheUtils;
import com.ld.materialmanagement.widget.XYMarkerView;
import com.orhanobut.logger.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页的Fragment
 * <p>
 * RecycleView 加载统计图
 */
public class HomeFragment extends BaseFragment {
    //        @Override
    //        protected int setLayoutResourceId() {
    //            return R.layout.fragment_home;
    //        }


    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    ChartRecyclerViewAdapter mAdapter;

    public static final int FIRST_CHART = 1;
    public static final int SECOND_CHART = 2;

    public static final int CACHE = 0x001;
    @BindView(R.id.first_chart_title)
    TextView mFirstChartTitle;
    @BindView(R.id.first_bar_chart)
    BarChart mFirstBarChart;
    @BindView(R.id.second_chart_title)
    TextView mSecondChartTitle;
    @BindView(R.id.second_bar_chart)
    BarChart mSecondBarChart;


    String firstY = "";
    String[] firstX = {"台账金额", "本月入库金额", "本月出库金额"};
    String secondY = "";
    String[] secondX = {"归还", "借出", "借还总计","入库数量","出库数量"};

    final DecimalFormat mFormat = new DecimalFormat("###,###,###,##0.0");

    IAxisValueFormatter firstYFormat = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            //格式化Y轴数据
            return mFormat.format(value) + firstY;
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    };

    IAxisValueFormatter secondYFormat = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            //格式化Y轴数据
            return mFormat.format(value) + secondY;
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    };

    IAxisValueFormatter firstXAxisFormatter = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int val = (int) value;
            if (val <= firstX.length) {
                return firstX[val - 1];
            } else {
                return value + "";
            }
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    };

    IAxisValueFormatter secondXAxisFormatter = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int val = (int) value;
            if (val <= secondX.length) {
                return secondX[val - 1];
            } else {
                return value + "";
            }
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    };

    public static final int SHOW = 11;
    public static final int HIDE = 22;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW:
                    mSwipeRefresh.setRefreshing(true);
                    break;
                case HIDE:
                    mSwipeRefresh.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

        //设置刷新时的颜色
        initSwipeRefresh(mSwipeRefresh);

        initChart(FIRST_CHART, mFirstBarChart);
        initChart(SECOND_CHART, mSecondBarChart);

        mFirstChartTitle.setText("本月出入库金额");
        mSecondChartTitle.setText("本月借还数量和出入库数量");

    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        //在这里联网请求数据
        mSwipeRefresh.setRefreshing(true);
        getCache();
        getChartData();
    }

    private void getCache() {
        //去内存中获取缓存
        // 读取缓存
        SecondChart cache = CacheUtils.getCache(AppConst.Cache.CHART_TWO, SecondChart.class);
        FirstChart cacheOne = CacheUtils.getCache(AppConst.Cache.CHART_ONE, FirstChart.class);
        if (cache != null) {
            updateSecondChart(cache);
        }

        if (cacheOne != null) {
            updateFirstChart(cacheOne);
        }
    }

    /**
     * 联网请求数据
     */
    protected void getChartData() {
        APi api = Network.getInstance().getApi(mActivity);
        handler.sendEmptyMessage(SHOW);

        Call<SecondChart> secondChart = api.getSecondChartData();
        secondChart.enqueue(new Callback<SecondChart>() {
            @Override
            public void onResponse(Call<SecondChart> call, Response<SecondChart> response) {
                SecondChart body = response.body();
                updateSecondChart(body);
                CacheUtils.setCache(AppConst.Cache.CHART_TWO, body);
                handler.sendEmptyMessage(HIDE);
            }

            @Override
            public void onFailure(Call<SecondChart> call, Throwable t) {
                handler.sendEmptyMessage(HIDE);
            }
        });



        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        Call<FirstChart> firstChartData = api.getFirstChartData();
        firstChartData.enqueue(new Callback<FirstChart>() {
            @Override
            public void onResponse(Call<FirstChart> call, Response<FirstChart> response) {
                handler.sendEmptyMessage(HIDE);

                FirstChart body = response.body();
                updateFirstChart(body);
                Logger.i(body.toString());
                CacheUtils.setCache(AppConst.Cache.CHART_ONE, body);

            }

            @Override
            public void onFailure(Call<FirstChart> call, Throwable t) {
                Logger.i(t.getMessage());

                handler.sendEmptyMessage(HIDE);
            }
        });

    }

    @Override
    protected void initListener() {
        super.initListener();

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getChartData();
            }
        });
    }


    private void updateFirstChart(FirstChart firstChart) {

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        yVals1.add(new BarEntry(1, (float) firstChart.money));
        yVals1.add(new BarEntry(2, (float) firstChart.imoney));
        yVals1.add(new BarEntry(3, (float) firstChart.omoney));

        BarDataSet set1;
        if (mFirstBarChart.getData() != null && mFirstBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mFirstBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            set1.setDrawValues(true);
            mFirstBarChart.getData().notifyDataChanged();
            mFirstBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            set1.setDrawValues(true);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            mFirstBarChart.setData(data);
        }

        mFirstBarChart.animateY(3000);
    }

    private void updateSecondChart(SecondChart bean) {

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        yVals1.add(new BarEntry(1, bean.getBack())); // 归还
        yVals1.add(new BarEntry(2, bean.getLend())); // 借出
        yVals1.add(new BarEntry(3, bean.getTotal())); // 总数
        yVals1.add(new BarEntry(4, bean.getInData()));//入库
        yVals1.add(new BarEntry(5, bean.getOutData()));//出库



        //mSecondChartTitle.setText("二期发电量统计 " + bean.adate);

        BarDataSet set1;

        if (mSecondBarChart.getData() != null &&
                mSecondBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mSecondBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mSecondBarChart.getData().notifyDataChanged();
            mSecondBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            mSecondBarChart.setData(data);
        }

        mSecondBarChart.animateY(3000);
    }


    private void initChart(int flag, BarChart mChart) {

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setNoDataText("暂时没有数据");
        mChart.getDescription().setEnabled(false);

        // 优化双指缩放  false 为只能上下或左右缩放  不能同时缩放
        mChart.setPinchZoom(true);

        // 如果多于 3个 entries are displayed in the chart, no values will be drawn
        mChart.setMaxVisibleValueCount(3);

        mChart.setDrawGridBackground(false);
        //mChart.setDrawYLabels(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(3);

        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        XYMarkerView mv = null;

        //根据flag的不同 对数据的格式化方式不同
        if (flag == FIRST_CHART) {
            //格式化第一个统计图的数据
            xAxis.setValueFormatter(firstXAxisFormatter);
            leftAxis.setValueFormatter(firstYFormat);
            mv = new XYMarkerView(mActivity, firstXAxisFormatter);
        } else if (flag == SECOND_CHART) {
            //格式化第二个统计图的数据
            xAxis.setValueFormatter(secondXAxisFormatter);
            leftAxis.setValueFormatter(secondYFormat);
            mv = new XYMarkerView(mActivity, secondXAxisFormatter);
        }

        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        mChart.getLegend().setEnabled(false);//关闭对颜色的描述
        /*Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(true); */
        //设置对颜色的解释
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
    }


    @Override
    public void onResume() {
        super.onResume();

        mFirstBarChart.notifyDataSetChanged();
        mSecondBarChart.notifyDataSetChanged();
    }
}
