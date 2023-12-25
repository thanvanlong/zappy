package com.longtv.zappy.ui.story;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.adapter.ContentBannerAdapter;
import com.longtv.zappy.network.dto.Chapter;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.film.HomeBoxFilmPresenter;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator3;

public class HomeBoxStoryFragment extends BaseFragment<HomeBoxStoryPresenter, HomeActivity> implements HomeBoxStoryView {
    @BindView(R.id.vp_banner)
    ViewPager2 vpBanner;
    @BindView(R.id.indicator)
    CircleIndicator3 mIndicator;
    @BindView(R.id.rcv_content)
    RecyclerView mRcvContent;
    @BindView(R.id.shimmer_view)
    ShimmerFrameLayout shimmerView;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_box_story;
    }

    @Override
    public void onPrepareLayout() {
        if (HomeActivity.getInstance().getCacheStory() != null) {
            onLoadGenreSuccess(HomeActivity.getInstance().getCacheStory());
            shimmerView.setVisibility(View.GONE);
        } else {
            shimmerView.setVisibility(View.VISIBLE);
            shimmerView.startShimmer();
            getPresenter().getStories();
        }
    }

    @Override
    public HomeBoxStoryPresenter onCreatePresenter() {
        return new HomeBoxStoryPresenterImpl(this);
    }

    @Override
    public void onLoadGenreSuccess(DataListDTO<ContentType> data) {
        shimmerView.setVisibility(View.GONE);
        ContentBannerAdapter contentNewsAdapter = new ContentBannerAdapter(getViewContext());
        vpBanner.setAdapter(contentNewsAdapter);
        mIndicator.setViewPager(vpBanner);
        mRcvContent.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
        mRcvContent.setAdapter(new HomeBoxStoryAdapter(data.getResults()));
        HomeActivity.getInstance().setCacheStory(data);
    }

    @Override
    public void onLoadChapterSuccess(DataListDTO<Chapter> data) {

    }
}
