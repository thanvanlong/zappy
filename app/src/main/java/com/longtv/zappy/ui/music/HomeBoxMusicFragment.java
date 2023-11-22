package com.longtv.zappy.ui.music;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.view.HorizontalItemDecoration;
import com.longtv.zappy.ui.story.HomeBoxStoryAdapter;

import butterknife.BindView;

public class HomeBoxMusicFragment extends BaseFragment {
    @BindView(R.id.rcv_content)
    RecyclerView rcvContent;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_box_music;
    }

    @Override
    public void onPrepareLayout() {
        rcvContent.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
        rcvContent.setAdapter(new HomeBoxMusicAdapter());
        rcvContent.addItemDecoration(new HorizontalItemDecoration(40));
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
