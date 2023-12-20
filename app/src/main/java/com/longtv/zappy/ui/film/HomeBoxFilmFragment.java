package com.longtv.zappy.ui.film;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.utils.PrefManager;

import java.util.HashMap;
import java.util.List;
import com.longtv.zappy.common.adapter.SearchAdapter;
import com.longtv.zappy.common.view.HorizontalItemDecoration;
import com.longtv.zappy.ui.HomeActivity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import me.relex.circleindicator.CircleIndicator3;

public class HomeBoxFilmFragment extends BaseFragment<HomeBoxFilmPresenter, HomeActivity> implements SwipeRefreshLayout.OnRefreshListener, HomeBoxFilmView {
    private static final int VOICE_SEARCH_REQUEST_CODE = 1000;
    @BindView(R.id.vp_banner)
    ViewPager2 vpBanner;
    @BindView(R.id.indicator)
    CircleIndicator3 mIndicator;
    @BindView(R.id.tab_category)
    TabLayout tbCategory;
    @BindView(R.id.vp_contents)
    ViewPager2 vpContents;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.iv_voice_search)
    ImageView ivVoiceSearch;
    @BindView(R.id.rcv_rs_search)
    RecyclerView rcvSearch;
    @BindView(R.id.shimmer_view)
    ShimmerFrameLayout shimmerFrameLayout;
    private int page = 0;
    private int currentPage = 0;
    private List<ContentType> contentTypes = new ArrayList<>();
    private ContentType currentType;
    private Map<String, List<Content>> data = new HashMap<>();
    private HomeBoxFilmAdapter homeBoxFilmAdapter;
    private boolean isFirstRender = true;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_box_video;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.getInstance().toggleTopBar(0);
    }

    @Override
    public void onPrepareLayout() {
        HomeActivity.getInstance().toggleTopBar(8);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
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

//        rcvSearch.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
//        rcvSearch.addItemDecoration(new HorizontalItemDecoration(20));
//        rcvSearch.setAdapter(new SearchAdapter());

        vpContents.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (!isFirstRender) {
                    JsonObject filter = new JsonObject();
                    currentType = contentTypes.get(position);
                    filter.addProperty("genres.id", currentType.getId() + "");
                    getPresenter().getMovies(filter);
                }
            }
        });

        loadData();
    }

    @OnTextChanged(R.id.edt_search)
    public void onSearch() {

    }

    @OnClick(R.id.iv_voice_search)
    public void onVoiceSearch() {
        Intent voiceSeachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        Locale locale = new Locale("vi");
        voiceSeachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale);
        voiceSeachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voiceSeachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Search voice");
        voiceSeachIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        voiceSeachIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 300);

        try {
            startActivityForResult(voiceSeachIntent, VOICE_SEARCH_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(getViewContext(), "Loi mat roi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_SEARCH_REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                edtSearch.setText(results.get(0));
            }
        }
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
    public void onLoadGenreSuccess(DataListDTO<ContentType> contentTypes) {
        JsonObject filter = new JsonObject();
        filter.addProperty("genres.id", contentTypes.getResults().get(0).getId() + "");
        this.contentTypes.addAll(contentTypes.getResults());
        for (ContentType contentType:
             this.contentTypes) {
            data.put(contentType.getId() + "", contentType.getContents());
        }
        currentType = contentTypes.getResults().get(0);
        homeBoxFilmAdapter = new HomeBoxFilmAdapter(getViewContext(), data);
        vpContents.setAdapter(homeBoxFilmAdapter);
        shimmerFrameLayout.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new TabLayoutMediator(tbCategory, vpContents,
                        (tab, position) -> tab.setText(HomeBoxFilmFragment.this.contentTypes.get(position).getName())
                ).attach();
            }
        }, 500);
    }

    private void initView(List<Content> contents) {
        shimmerFrameLayout.setVisibility(View.GONE);
        data.put(currentType.getId() + "", contents);
        homeBoxFilmAdapter = new HomeBoxFilmAdapter(getViewContext(), data);
        vpContents.setAdapter(homeBoxFilmAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isFirstRender = false;
            }
        }, 1000);
    }

    @Override
    public void onLoadGenreError(String message) {
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMoviesSuccess(DataListDTO<Content> contents) {
        data.put(currentType.getId() + "", contents.getResults());
        initView(contents.getResults());

    }

    @Override
    public void onLoadMoviesError(String message) {

    }
}
