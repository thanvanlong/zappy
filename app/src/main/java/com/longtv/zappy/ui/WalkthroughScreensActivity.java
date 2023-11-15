package com.longtv.zappy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.longtv.zappy.R;
import com.longtv.zappy.ui.adapter.WalkthroughScreensAdapter;

import me.relex.circleindicator.CircleIndicator3;

public class WalkthroughScreensActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private CircleIndicator3 indicator3;
    private TextView tv_skip;
    private ImageView iv_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough_screens);

        WalkthroughScreensAdapter adapter = new WalkthroughScreensAdapter(this);
        viewPager2 = findViewById(R.id.viewPage2);
        indicator3 = findViewById(R.id.indicator);
        viewPager2.setAdapter(adapter);
        indicator3.setViewPager(viewPager2);

        iv_next = findViewById(R.id.iv_next);
        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager2.getCurrentItem();
                switch (currentItem){
                    case 0:
                        viewPager2.setCurrentItem(1);
                        break;
                    case 1:
                        viewPager2.setCurrentItem(2);
                        break;
                    case 2:
                        viewPager2.setCurrentItem(0);;
                        break;
                }
            }
        });

        tv_skip = findViewById(R.id.tv_skip);
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAccountActivity();
            }
        });
    }

    private void startAccountActivity() {
//        Intent intent = new Intent(this, AccountActivity.class);
//        startActivity(intent);
//        finish();
    }
}