package com.longtv.zappy.ui.story;

import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.dto.Chapter;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.network.dto.DataListDTO;

public interface HomeBoxStoryView extends BaseView<HomeBoxStoryPresenter> {
    void onLoadGenreSuccess(DataListDTO<ContentType> data);
    void onLoadChapterSuccess(DataListDTO<Chapter> data);
}
