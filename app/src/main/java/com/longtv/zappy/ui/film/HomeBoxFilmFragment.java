package com.longtv.zappy.ui.film;

import android.os.Handler;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.JsonObject;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.common.adapter.ContentBannerAdapter;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.utils.PrefManager;

import java.util.List;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator3;

public class HomeBoxFilmFragment extends BaseFragment<HomeBoxFilmPresenter, HomeActivity> implements SwipeRefreshLayout.OnRefreshListener, HomeBoxFilmView {
    @BindView(R.id.vp_banner)
    ViewPager2 vpBanner;
    @BindView(R.id.indicator)
    CircleIndicator3 mIndicator;
    @BindView(R.id.tab_category)
    TabLayout tbCategory;
    @BindView(R.id.vp_contents)
    ViewPager2 vpContents;
    @BindView(R.id.shimmer_view)
    ShimmerFrameLayout shimmerFrameLayout;
    private int page = 0;
    private int currentPage = 0;
    private List<ContentType> contentTypes;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_box_video;
    }

    @Override
    public void onPrepareLayout() {

        ContentBannerAdapter contentNewsAdapter = new ContentBannerAdapter(getViewContext());
        vpBanner.setAdapter(contentNewsAdapter);
        mIndicator.setViewPager(vpBanner);

        vpBanner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
                    vpBanner.setCurrentItem(page, true);
                } else {
                    page = -1;
                }

                handler.postDelayed(this::run, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);

        loadData();

//        vpContents.setAdapter(new HomeBoxFilmAdapter());
//        new TabLayoutMediator(tbCategory, vpContents,
//                (tab, position) -> tab.setText(Constants.category[position])
//        ).attach();
    }

    @Override
    public HomeBoxFilmPresenter onCreatePresenter() {
        return new HomeBoxFilmPresenterImpl(this);
    }

    private void loadData() {
        getPresenter().getGenre();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadGenreSuccess(List<ContentType> contentTypes) {
        this.contentTypes = contentTypes;
        JsonObject filter = new JsonObject();
        filter.addProperty("genres.id", contentTypes.get(0).getId());
        getPresenter().getMovies(filter);
    }

    @Override
    public void onLoadGenreError(String message) {

    }

    @Override
    public void onLoadMoviesSuccess(List<Content> contents) {

    }

    @Override
    public void onLoadMoviesError(String message) {

    }
}
