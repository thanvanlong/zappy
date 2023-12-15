package com.longtv.zappy.ui.film;

import com.google.gson.JsonObject;
import com.longtv.zappy.base.BasePresenter;

public interface HomeBoxFilmPresenter extends BasePresenter {
    void getGenre();
    void getMovies(JsonObject filter);
}
