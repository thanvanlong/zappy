package com.longtv.zappy.common.fragment;

import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.adapter.ContentMediaAdapter;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.DataListDTO;

import butterknife.BindView;

public class ContentMediaFragment extends BaseFragment {
    @BindView(R.id.rcy_media)
    RecyclerView rcvMedia;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    private DataListDTO<Content> data;
    private String type;

    public ContentMediaFragment(DataListDTO<Content> data, String type) {
        this.data = data;
        this.type = type;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_content_media;
    }

    @Override
    public void onPrepareLayout() {
        ContentMediaAdapter contentMediaAdapter = new ContentMediaAdapter(getViewContext(), type, data.getResults());
        rcvMedia.setLayoutManager(new GridLayoutManager(getViewContext(), 2));
        rcvMedia.setAdapter(contentMediaAdapter);
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
