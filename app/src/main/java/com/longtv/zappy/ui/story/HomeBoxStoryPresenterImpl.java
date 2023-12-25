package com.longtv.zappy.ui.story;

import com.longtv.zappy.base.BaseCallback;
import com.longtv.zappy.base.BasePresenterImpl;
import com.longtv.zappy.network.ServiceBuilder;
import com.longtv.zappy.network.dto.Chapter;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.network.dto.DataListDTO;

public class HomeBoxStoryPresenterImpl extends BasePresenterImpl<HomeBoxStoryView> implements HomeBoxStoryPresenter{
    public HomeBoxStoryPresenterImpl(HomeBoxStoryView view) {
        super(view);
    }

    @Override
    public void getStories() {
        ServiceBuilder.getService().getGenreStory().enqueue(new BaseCallback<DataListDTO<ContentType>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
            }

            @Override
            public void onResponse(DataListDTO<ContentType> data) {
                mView.onLoadGenreSuccess(data);
            }
        });
    }

    @Override
    public void getChapter(String id) {
        ServiceBuilder.getService().getChapter(id).enqueue(new BaseCallback<DataListDTO<Chapter>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
            }

            @Override
            public void onResponse(DataListDTO<Chapter> data) {
                mView.onLoadChapterSuccess(data);
            }
        });
    }
}
