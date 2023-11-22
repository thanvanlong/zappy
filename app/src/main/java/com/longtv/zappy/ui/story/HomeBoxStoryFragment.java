package com.longtv.zappy.ui.story;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.adapter.ContentBannerAdapter;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator3;

public class HomeBoxStoryFragment extends BaseFragment {
    @BindView(R.id.vp_banner)
    ViewPager2 vpBanner;
    @BindView(R.id.indicator)
    CircleIndicator3 mIndicator;
    @BindView(R.id.rcv_content)
    RecyclerView mRcvContent;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_box_story;
    }

    @Override
    public void onPrepareLayout() {
        ContentBannerAdapter contentNewsAdapter = new ContentBannerAdapter(getViewContext());
        vpBanner.setAdapter(contentNewsAdapter);
        mIndicator.setViewPager(vpBanner);
        mRcvContent.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
        mRcvContent.setAdapter(new HomeBoxStoryAdapter());
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
