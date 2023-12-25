package com.longtv.zappy.ui.film;

import com.google.gson.JsonObject;
import com.longtv.zappy.base.BasePresenter;

public interface HomeBoxFilmPresenter extends BasePresenter {
    void getGenre();
    void getMovies(JsonObject filter);
    void searchMovies(String filter);
    void buyMovie(JsonObject jsonObject);
    void getMoviesDetail(String id);
    void likeMovie(JsonObject body);
    void getContentRelated(JsonObject id);
    void getBanner();
}
