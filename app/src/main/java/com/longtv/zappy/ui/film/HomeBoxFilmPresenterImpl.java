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
import com.longtv.zappy.utils.PrefManager;

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

    @Override
    public void searchMovies(String filter) {
        ServiceBuilder.getService().searchMovies(filter).enqueue(new BaseCallback<DataListDTO<Content>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                mView.onLoadSearchMoviesError(errorMessage);
            }

            @Override
            public void onResponse(DataListDTO<Content> data) {
                mView.onLoadSearchMoviesSuccess(data);
            }
        });
    }

    @Override
    public void buyMovie(JsonObject jsonObject) {
        DialogUtils.showProgressDialog(getViewContext());
        ServiceBuilder.getService().buyMovie(jsonObject).enqueue(new BaseCallback<Boolean>() {
            @Override
            public void onError(String errorCode, String errorMessage) {
                DialogUtils.dismissProgressDialog(getViewContext());
            }

            @Override
            public void onResponse(Boolean data) {
                DialogUtils.dismissProgressDialog(getViewContext());
                mView.onBuySuccess(data);
            }
        });
    }

    @Override
    public void getMoviesDetail(String id) {
        ServiceBuilder.getService().getMovieDetail(id).enqueue(new BaseCallback<Content>() {
            @Override
            public void onError(String errorCode, String errorMessage) {

            }

            @Override
            public void onResponse(Content data) {
                mView.doLoadFilmDetail(data);
            }
        });
    }

    @Override
    public void likeMovie(JsonObject body) {
        ServiceBuilder.getService().addLibraryName(body).enqueue(new BaseCallback<Object>() {
            @Override
            public void onError(String errorCode, String errorMessage) {

            }

            @Override
            public void onResponse(Object data) {

            }
        });
    }

    @Override
    public void getContentRelated(JsonObject id) {
        ServiceBuilder.getService().getMoviesByGenre(id).enqueue(new BaseCallback<DataListDTO<Content>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {

            }

            @Override
            public void onResponse(DataListDTO<Content> data) {
                mView.onLoadMoviesSuccess(data);
            }
        });
    }

    @Override
    public void getBanner() {
//        ServiceBuilder.getService().getBannerMovie().enqueue(new BaseCallback<DataListDTO<Content>>() {
//            @Override
//            public void onError(String errorCode, String errorMessage) {
//
//            }
//
//            @Override
//            public void onResponse(DataListDTO<Content> data) {
//                mView.onLoadBannerSuccess(data);
//            }
//        });
    }
}
