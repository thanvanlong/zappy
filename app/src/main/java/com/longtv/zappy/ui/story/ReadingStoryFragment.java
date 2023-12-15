package com.longtv.zappy.ui.story;

import android.util.Log;

import androidx.viewpager2.widget.ViewPager2;

import com.longtv.zappy.R;

import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer2;

import java.util.ArrayList;

import butterknife.BindView;


public class ReadingStoryFragment extends BaseFragment {
    @BindView(R.id.viewPage2)
    protected ViewPager2 viewPager2;
    private ArrayList<PageStory> pages = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.fragment_reading_story;
    }

    @Override
    public void onPrepareLayout() {
        PageStoryAdapter adapter = new PageStoryAdapter(pages);
        BookFlipPageTransformer2 transformer2 = new BookFlipPageTransformer2();
        Log.e("anth", "onPrepareLayout: check sss");
        transformer2.setEnableScale(true);
        transformer2.setScaleAmountPercent(10f);

        viewPager2.setPageTransformer(transformer2);
        viewPager2.setAdapter(adapter);
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
