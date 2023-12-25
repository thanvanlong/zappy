package com.longtv.zappy.ui.music;

import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.DataListDTO;

public interface HomeBoxMusicView extends BaseView<HomeBoxMusicPresenter> {
    void onLoadMusicsSuccess(DataListDTO<Content> data);
    void doLoadMusicDetail(Content content);
    void doLoadContentRelated(DataListDTO<Content> data);
}
