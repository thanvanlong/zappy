package com.longtv.zappy.ui.home;

import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.DataListDTO;

public interface HomeBoxView extends BaseView<HomeBoxPresenter> {
    void onLoadMusicsSuccess(DataListDTO<Content> data);
    void onLoadFilmsSuccess(DataListDTO<Content> data);
    void onLoadStoriesSuccess(DataListDTO<Content> data);
}
