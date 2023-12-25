package com.longtv.zappy.ui.story;

import android.os.Bundle;
import android.util.Log;

import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.longtv.zappy.R;

import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.network.dto.Chapter;
import com.longtv.zappy.utils.PrefManager;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ReadingStoryFragment extends BaseFragment {
    @BindView(R.id.viewPage2)
    protected ViewPager2 viewPager2;
    private long timeOnStory;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_reading_story;
    }

    @Override
    public void onPrepareLayout() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String data = bundle.getString(Constants.DATA);
            Chapter chapter = new Gson().fromJson(data, Chapter.class);
            PageStoryAdapter adapter = new PageStoryAdapter(chapter.getImages());
            BookFlipPageTransformer2 transformer2 = new BookFlipPageTransformer2();
            transformer2.setEnableScale(true);
            transformer2.setScaleAmountPercent(10f);

            viewPager2.setPageTransformer(transformer2);
            viewPager2.setAdapter(adapter);
        }
        timeOnStory = System.currentTimeMillis();
    }

    @Override
    public void onPause() {
        super.onPause();
        timeOnStory = System.currentTimeMillis() - timeOnStory;
        PrefManager.saveTimeOnBook(getViewContext(), timeOnStory);
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
