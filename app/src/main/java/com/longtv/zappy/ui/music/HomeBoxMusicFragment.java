package com.longtv.zappy.ui.music;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.view.HorizontalItemDecoration;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.story.HomeBoxStoryAdapter;

import butterknife.BindView;
import butterknife.OnTextChanged;

public class HomeBoxMusicFragment extends BaseFragment<HomeBoxMusicPresenter, HomeActivity> implements HomeBoxMusicView {
    @BindView(R.id.rcv_content)
    RecyclerView rcvContent;
    @BindView(R.id.shimmer_view)
    ShimmerFrameLayout shimmerView;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_box_music;
    }

    @Override
    public void onPrepareLayout() {
        if (HomeActivity.getInstance().getCacheMusic() != null) {
            onLoadMusicsSuccess(HomeActivity.getInstance().getCacheMusic());
            shimmerView.setVisibility(View.GONE);
        } else {
            shimmerView.setVisibility(View.VISIBLE);
            shimmerView.startShimmer();
            loadData();
        }
        rcvContent.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
        rcvContent.setAdapter(new HomeBoxMusicAdapter());
        rcvContent.addItemDecoration(new HorizontalItemDecoration(40));
    }

    @Override
    public HomeBoxMusicPresenter onCreatePresenter() {
        return new HomeBoxMusicPresenterImpl(this);
    }

    private void loadData() {
        shimmerView.setVisibility(View.VISIBLE);
        shimmerView.startShimmer();
        getPresenter().getMusics("");
    }

    @Override
    public void onLoadMusicsSuccess(DataListDTO<Content> data) {
        shimmerView.stopShimmer();
        shimmerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        rcvContent.setVisibility(View.VISIBLE);
        getViewContext().hideSoftKeyboard();
        ((HomeBoxMusicAdapter) rcvContent.getAdapter()).setmContents(data.getResults());
    }

    @Override
    public void doLoadMusicDetail(Content content) {

    }

    @Override
    public void doLoadContentRelated(DataListDTO<Content> data) {

    }

    @OnTextChanged(R.id.edt_search)
    public void onTextChanged(CharSequence text) {
        if (text.length() > 2 || text.length() == 0) {
            rcvContent.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            getPresenter().getMusics(text.toString());
        }
    }
}
