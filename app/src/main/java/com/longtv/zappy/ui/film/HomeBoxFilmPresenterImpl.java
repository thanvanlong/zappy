package com.longtv.zappy.ui.film;

import com.google.gson.JsonObject;
import com.longtv.zappy.base.BaseCallback;
import com.longtv.zappy.base.BasePresenterImpl;
import com.longtv.zappy.network.ServiceBuilder;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.utils.DialogUtils;

import java.util.List;

public class HomeBoxFilmPresenterImpl extends BasePresenterImpl<HomeBoxFilmView> implements HomeBoxFilmPresenter{
    public HomeBoxFilmPresenterImpl(HomeBoxFilmView view) {
        super(view);
    }

    @Override
    public void getGenre() {
        DialogUtils.showProgressDialog(getViewContext());
        ServiceBuilder.getService().getGenreMovie().enqueue(new BaseCallback<List<ContentType>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                DialogUtils.dismissProgressDialog(getViewContext());
                mView.onLoadGenreError(errorMessage);
            }

            @Override
            public void onResponse(List<ContentType> data) {
                DialogUtils.dismissProgressDialog(getViewContext());
                mView.onLoadGenreSuccess(data);
            }
        });
    }

    @Override
    public void getMovies(JsonObject filter) {
        DialogUtils.showProgressDialog(getViewContext());
        ServiceBuilder.getService().getMovies(filter).enqueue(new BaseCallback<List<Content>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onLoadMoviesError(errorMessage);
                DialogUtils.dismissProgressDialog(getViewContext());
            }

            @Override
            public void onResponse(List<Content> data) {
                DialogUtils.dismissProgressDialog(getViewContext());
                mView.onLoadMoviesSuccess(data);
            }
        });
    }
}
