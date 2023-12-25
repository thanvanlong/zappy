package com.longtv.zappy.ui.story;

import com.longtv.zappy.base.BasePresenter;

public interface HomeBoxStoryPresenter extends BasePresenter {
    void getStories();

    void getChapter(String id);

}
