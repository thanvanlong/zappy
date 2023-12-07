package com.longtv.zappy.ui.stats;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.ui.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StatsScreenFragment extends BaseFragment {
    @BindView(R.id.barChart)
    BarChart chart;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_stats_screen;
    }

    @Override
    public void onPrepareLayout() {
        HomeActivity.getInstance().hideBottomBar();
        HomeActivity.getInstance().toggleCoin(View.GONE);
        setupBarChart(chart);
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 50));
        entries.add(new BarEntry(2, 80));
        entries.add(new BarEntry(3, 60));
        entries.add(new BarEntry(4, 30));
        entries.add(new BarEntry(5, 30));
        entries.add(new BarEntry(6, 30));
        entries.add(new BarEntry(7, 30));

        BarDataSet dataSet = new BarDataSet(entries, "Bar Chart Example");
        dataSet.setColor(ContextCompat.getColor(HomeActivity.getInstance(), R.color.light_pink_v2));
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.5f);
        chart.setData(barData);
        chart.invalidate(); // refre
    }

    private void setupBarChart(BarChart barChart) {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new MyXAxisValueFormatter());

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(6, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);
//        leftAxis.setDrawGridLines(false);
    }

    private class MyXAxisValueFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return String.valueOf((int) value);
        }
    }

    @OnClick(R.id.iv_next_day)
    public void nextDay() {
        Log.e("anth", "nextDay: chekc log");
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
