package com.longtv.zappy.ui.home;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.ui.HomeActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator3;

public class HomeBoxFragment extends BaseFragment<HomeBoxPresenter, HomeActivity> implements HomeBoxView {
    @BindView(R.id.vp_news)
    ViewPager2 vpNews;
    @BindView(R.id.indicator)
    CircleIndicator3 indicator3;
    @BindView(R.id.tab_category)
    TabLayout tbCategory;
    @BindView(R.id.vp_contents)
    ViewPager2 vpContents;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.container_main)
    LinearLayout containerMain;
    int count = 0;
    Handler handler;
    Runnable runnable;
    private Map<String, DataListDTO<Content>> dataMap = new HashMap<>();
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    int page = 0;
    @Override
    public void onPrepareLayout() {
        HomeActivity.getInstance().showBottomBar();
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

        handler = new Handler();
        runnable = new Runnable() {
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

        if (HomeActivity.getInstance().getCacheHome() == null) {
            getPresenter().getFilms();
            getPresenter().getMusics();
            getPresenter().getStories();
            progressBar.setVisibility(View.VISIBLE);
            containerMain.setVisibility(View.GONE);
        } else {
            dataMap = HomeActivity.getInstance().getCacheHome();
            ContentMediaPagerAdapter contentMediaPagerAdapter = new ContentMediaPagerAdapter(getViewContext());
            vpContents.setAdapter(contentMediaPagerAdapter);
            contentMediaPagerAdapter.setDataListDTOMap(dataMap);
            new TabLayoutMediator(tbCategory, vpContents,
                    (tab, position) -> tab.setText(Constants.category[position])
            ).attach();
            containerMain.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.getInstance().toggleSetting(1);
//        HomeActivity.getInstance().showBottomBar();
//        HomeActivity.getInstance().toggleTopBar(0);
//        HomeActivity.getInstance().handleBtnBack(false);
    }

    @Override
    public HomeBoxPresenter onCreatePresenter() {
        return new HomeBoxPresenterImpl(this);
    }

    @Override
    public void onLoadMusicsSuccess(DataListDTO<Content> data) {
        count ++;
        dataMap.put("MUSIC",data);
        if (count == 3) {
            HomeActivity.getInstance().setCacheHome(dataMap);
            ContentMediaPagerAdapter contentMediaPagerAdapter = new ContentMediaPagerAdapter(getViewContext());
            contentMediaPagerAdapter.setDataListDTOMap(dataMap);
            vpContents.setAdapter(contentMediaPagerAdapter);
            new TabLayoutMediator(tbCategory, vpContents,
                    (tab, position) -> tab.setText(Constants.category[position])
            ).attach();
            containerMain.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        Log.e("anth", "onLoadMusicsSuccess: " + count);
    }

    @Override
    public void onLoadFilmsSuccess(DataListDTO<Content> data) {
        count ++;
        dataMap.put("VOD",data);
        if (count == 3) {
            HomeActivity.getInstance().setCacheHome(dataMap);
            ContentMediaPagerAdapter contentMediaPagerAdapter = new ContentMediaPagerAdapter(getViewContext());
            vpContents.setAdapter(contentMediaPagerAdapter);
            contentMediaPagerAdapter.setDataListDTOMap(dataMap);
            new TabLayoutMediator(tbCategory, vpContents,
                    (tab, position) -> tab.setText(Constants.category[position])
            ).attach();
            containerMain.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadStoriesSuccess(DataListDTO<Content> data) {
        count ++;
        dataMap.put("STORY",data);
        Log.e("anth", "onLoadStoriesSuccess: " + count);
        if (count == 3) {
            HomeActivity.getInstance().setCacheHome(dataMap);
            ContentMediaPagerAdapter contentMediaPagerAdapter = new ContentMediaPagerAdapter(getViewContext());
            vpContents.setAdapter(contentMediaPagerAdapter);
            contentMediaPagerAdapter.setDataListDTOMap(dataMap);
            new TabLayoutMediator(tbCategory, vpContents,
                    (tab, position) -> tab.setText(Constants.category[position])
            ).attach();
            containerMain.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
