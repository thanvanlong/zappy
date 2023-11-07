package com.longtv.zappy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.longtv.zappy.R;
import com.longtv.zappy.ui.adapter.WalkthroughScreensAdapter;

import me.relex.circleindicator.CircleIndicator3;

public class WalkthroughScreensActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private CircleIndicator3 indicator3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough_screens);

        WalkthroughScreensAdapter adapter = new WalkthroughScreensAdapter(this);
        viewPager2 = findViewById(R.id.viewPage2);
        indicator3 = findViewById(R.id.indicator);

        viewPager2.setAdapter(adapter);
        indicator3.setViewPager(viewPager2);
    }
}