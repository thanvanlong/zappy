package com.longtv.zappy.ui.film;

import android.util.Log;

import com.google.gson.JsonObject;
import com.longtv.zappy.base.BaseCallback;
import com.longtv.zappy.base.BasePresenterImpl;
import com.longtv.zappy.network.ServiceBuilder;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.utils.DialogUtils;

import java.util.List;

public class HomeBoxFilmPresenterImpl extends BasePresenterImpl<HomeBoxFilmView> implements HomeBoxFilmPresenter{
    public HomeBoxFilmPresenterImpl(HomeBoxFilmView view) {
        super(view);
    }

    @Override
    public void getGenre() {
        ServiceBuilder.getService().getGenreMovie().enqueue(new BaseCallback<DataListDTO<ContentType>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onLoadGenreError(errorMessage);
            }

            @Override
            public void onResponse(DataListDTO<ContentType> data) {
                mView.onLoadGenreSuccess(data);
            }
        });
    }

    @Override
    public void getMovies(JsonObject filter) {
        Log.e("anth", "getMovies: " + filter);
        ServiceBuilder.getService().getMovies(filter).enqueue(new BaseCallback<DataListDTO<Content>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onLoadMoviesError(errorMessage);
            }

            @Override
            public void onResponse(DataListDTO<Content> data) {
                Log.e("anth", "onResponse: movie" + data.getResults());
                mView.onLoadMoviesSuccess(data);
            }
        });
    }
}
