package com.longtv.zappy.ui.stats;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.longtv.zappy.utils.PrefManager;
import com.longtv.zappy.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

public class StatsScreenFragment extends BaseFragment {
    @BindView(R.id.barChart)
    BarChart chart;
    @BindView(R.id.iv_next_day)
    ImageView ivNextDay;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_date_picker)
    TextView tvDatePicker;
    @BindView(R.id.iv_prev_day)
    ImageView ivPrevDay;
    @BindView(R.id.tv_time_on)
    TextView tvTimeOn;
    @BindView(R.id.tv_time_on_music)
    TextView tvTimeOnMusic;
    @BindView(R.id.tv_time_on_video)
    TextView tvTimeOnVideo;
    @BindView(R.id.tv_time_on_story)
    TextView tvTimeOnStory;


    private Map<Integer, Long> map = new HashMap<>();
    private int currentDay = 7;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_stats_screen;
    }

    @Override
    public void onPrepareLayout() {
        HomeActivity.getInstance().hideBottomBar();
        HomeActivity.getInstance().toggleCoin(View.GONE);
        HomeActivity.getInstance().handleBtnBack(true);
        trackAppUsage(getViewContext());
        ivNextDay.setAlpha(0.5f);
        ivNextDay.setEnabled(false);
        Log.e("anth", "onPrepareLayout: time " + PrefManager.getTimeOnFilm(getViewContext()) + " " + PrefManager.getTimeOnMusic(getViewContext()));
        tvTimeOnVideo.setText(StringUtils.covertSecondToHMS(PrefManager.getTimeOnFilm(getViewContext()) / 1000));
        tvTimeOnMusic.setText(StringUtils.covertSecondToHMS(PrefManager.getTimeOnMusic(getViewContext()) / 1000));
        tvTimeOnStory.setText(StringUtils.covertSecondToHMS(PrefManager.getTimeOnBook(getViewContext()) / 1000));
        ivNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDay < 7) {
                    currentDay ++;
                    chart.highlightValue(currentDay, 0);
                    tvTime.setText(StringUtils.convertMillisecondToHMS(map.get(currentDay)));
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.add(Calendar.DAY_OF_MONTH, currentDay - 8);
                    Date futureDate = calendar1.getTime();

                    String formattedDate = formatDate(futureDate);
                    tvDatePicker.setText(formattedDate);
                    tvDate.setText(formattedDate);
                }
            }
        });

        ivPrevDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDay > 1) {
                    currentDay --;
                    chart.highlightValue(currentDay, 0);
                    tvTime.setText(StringUtils.convertMillisecondToHMS(map.get(currentDay)));
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.add(Calendar.DAY_OF_MONTH, currentDay - 8);
                    Date futureDate = calendar1.getTime();

                    String formattedDate = formatDate(futureDate);
                    tvDatePicker.setText(formattedDate);
                    ivNextDay.setAlpha(1f);
                    ivNextDay.setEnabled(true);
                    tvDate.setText(formattedDate);
                }
            }
        });
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



    public void trackAppUsage(Context context) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        if (usageStatsManager == null) {
            return;
        }

        setupBarChart(chart);
        List<BarEntry> entries = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -6); // Lấy dữ liệu trong vòng 7 ngày

        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, calendar.getTimeInMillis(), System.currentTimeMillis());
        int count = 1;
        if (usageStatsList != null && !usageStatsList.isEmpty()) {
            for (UsageStats usageStats : usageStatsList) {
                if (usageStats.getPackageName().equals("com.longtv.zappy")) {
                    Log.e("anth", "trackAppUsage: " + StringUtils.covertSecondToHMS(usageStats.getTotalTimeInForeground() / 1000));
                    entries.add(new BarEntry(count, ((float) usageStats.getTotalTimeInForeground() / (3600000f))));
                    map.put(count, usageStats.getTotalTimeInForeground());
                    count ++;
                }
            }


            BarDataSet dataSet = new BarDataSet(entries, "Usage Time Chart");
            dataSet.setColor(ContextCompat.getColor(HomeActivity.getInstance(), R.color.light_pink_v2));
            BarData barData = new BarData(dataSet);
            barData.setBarWidth(0.5f);
            chart.setData(barData);
            chart.invalidate();
            chart.highlightValue(currentDay, 0);

            tvTime.setText(StringUtils.convertMillisecondToHMS(map.get(currentDay)));
            Calendar calendar1 = Calendar.getInstance();
            calendar1.add(Calendar.DAY_OF_MONTH, 0);
            Date futureDate = calendar1.getTime();

            String formattedDate = formatDate(futureDate);
            tvDate.setText(formattedDate);
            tvDatePicker.setText(formattedDate);
        }
    }

    public static String formatDate(Date date) {
        // Định dạng chuỗi theo "EEE, MMM d"
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
        return dateFormat.format(date);
    }
    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
