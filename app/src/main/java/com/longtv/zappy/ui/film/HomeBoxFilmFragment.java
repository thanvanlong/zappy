package com.longtv.zappy.ui.film;

import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.common.adapter.ContentBannerAdapter;
import com.longtv.zappy.common.adapter.SearchAdapter;
import com.longtv.zappy.common.view.HorizontalItemDecoration;
import com.longtv.zappy.ui.HomeActivity;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import me.relex.circleindicator.CircleIndicator3;

public class HomeBoxFilmFragment extends BaseFragment {
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
    private int page = 0;
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

        vpContents.setAdapter(new HomeBoxFilmAdapter());
        new TabLayoutMediator(tbCategory, vpContents,
                (tab, position) -> tab.setText(Constants.category[position])
        ).attach();

        rcvSearch.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
        rcvSearch.addItemDecoration(new HorizontalItemDecoration(20));
        rcvSearch.setAdapter(new SearchAdapter());
    }

    @OnTextChanged(R.id.edt_search)
    public void onSearch() {
        Log.e("anth", "onSearch: " + edtSearch.getText().toString());
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
            Log.e("anth", "onVoiceSearch: ", e);
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
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
