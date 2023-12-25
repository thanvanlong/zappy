package com.longtv.zappy.ui.home;

import com.longtv.zappy.base.BaseCallback;
import com.longtv.zappy.base.BasePresenterImpl;
import com.longtv.zappy.network.ServiceBuilder;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.DataListDTO;

public class HomeBoxPresenterImpl extends BasePresenterImpl<HomeBoxView> implements HomeBoxPresenter{
    public HomeBoxPresenterImpl(HomeBoxView view) {
        super(view);
    }

    @Override
    public void getMusics() {
        ServiceBuilder.getService().getBannerMusic().enqueue(new BaseCallback<DataListDTO<Content>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {

            }

            @Override
            public void onResponse(DataListDTO<Content> data) {
                mView.onLoadMusicsSuccess(data);
            }
        });
    }

    @Override
    public void getFilms() {
        ServiceBuilder.getService().getBannerMovie().enqueue(new BaseCallback<DataListDTO<Content>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {

            }

            @Override
            public void onResponse(DataListDTO<Content> data) {
                mView.onLoadFilmsSuccess(data);
            }
        });
    }

    @Override
    public void getStories() {
        ServiceBuilder.getService().getBannerStory().enqueue(new BaseCallback<DataListDTO<Content>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {

            }

            @Override
            public void onResponse(DataListDTO<Content> data) {
                mView.onLoadStoriesSuccess(data);
            }
        });
    }
}
