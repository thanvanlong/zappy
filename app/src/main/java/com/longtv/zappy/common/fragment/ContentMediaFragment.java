package com.longtv.zappy.common.fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.adapter.ContentMediaAdapter;

import butterknife.BindView;

public class ContentMediaFragment extends BaseFragment {
    @BindView(R.id.rcy_media)
    RecyclerView rcvMedia;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_content_media;
    }

    @Override
    public void onPrepareLayout() {
        ContentMediaAdapter contentMediaAdapter = new ContentMediaAdapter(getViewContext());
        rcvMedia.setLayoutManager(new GridLayoutManager(getViewContext(), 2));
        rcvMedia.setAdapter(contentMediaAdapter);
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
