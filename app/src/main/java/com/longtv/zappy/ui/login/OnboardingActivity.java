package com.longtv.zappy.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.longtv.zappy.R;
import com.longtv.zappy.common.adapter.OnboardingAdapter;
import com.longtv.zappy.utils.PrefManager;

import me.relex.circleindicator.CircleIndicator3;

public class OnboardingActivity extends AppCompatActivity {
    protected ViewPager2 viewPager2;
    private CircleIndicator3 indicator3;
    private TextView tv_skip;
    private ImageView iv_next;
    private Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        OnboardingAdapter adapter = new OnboardingAdapter(this);
        viewPager2 = findViewById(R.id.viewPage2);
        indicator3 = findViewById(R.id.indicator);
        viewPager2.setAdapter(adapter);
        indicator3.setViewPager(viewPager2);

        iv_next = findViewById(R.id.iv_next);
        btnNext = findViewById(R.id.tv_next);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                    iv_next.setVisibility(View.GONE);
                    btnNext.setVisibility(View.VISIBLE);
                }
            }
        });

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
                        iv_next.setVisibility(View.GONE);
                        btnNext.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        tv_skip = findViewById(R.id.tv_skip);
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager.setIsFirstStart(getApplicationContext(), false);
                startLoginActivity();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager.setIsFirstStart(getApplicationContext(), false);
                startLoginActivity();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}