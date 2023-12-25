package com.longtv.zappy.ui.music;

import com.longtv.zappy.base.BasePresenter;

public interface HomeBoxMusicPresenter extends BasePresenter {
    void getMusics(String search);
    void getMusicDetail(int id);
    void getContentRelated(String id);
}
