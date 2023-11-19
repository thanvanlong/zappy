package com.longtv.zappy.ui.home;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.common.adapter.ContentMediaPagerAdapter;
import com.longtv.zappy.common.adapter.ContentBannerAdapter;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator3;

public class HomeBoxFragment extends BaseFragment {
    @BindView(R.id.vp_news)
    ViewPager2 vpNews;
    @BindView(R.id.indicator)
    CircleIndicator3 indicator3;
    @BindView(R.id.tab_category)
    TabLayout tbCategory;
    @BindView(R.id.vp_contents)
    ViewPager2 vpContents;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    int page = 0;
    @Override
    public void onPrepareLayout() {
        ContentBannerAdapter contentNewsAdapter = new ContentBannerAdapter(getViewContext());
        vpNews.setAdapter(contentNewsAdapter);
        indicator3.setViewPager(vpNews);

        vpNews.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                page = position;
            }
        });

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (page < 6) {
                    page ++;
                    vpNews.setCurrentItem(page, true);
                } else {
                    page = -1;
                }

                handler.postDelayed(this::run, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);


        ContentMediaPagerAdapter contentMediaPagerAdapter = new ContentMediaPagerAdapter(getViewContext());
        vpContents.setAdapter(contentMediaPagerAdapter);
        new TabLayoutMediator(tbCategory, vpContents,
                (tab, position) -> tab.setText(Constants.category[position])
        ).attach();

    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
