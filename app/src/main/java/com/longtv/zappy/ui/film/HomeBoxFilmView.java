package com.longtv.zappy.ui.film;

import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.ContentType;

import java.util.List;

public interface HomeBoxFilmView extends BaseView<HomeBoxFilmPresenter> {
    void onLoadGenreSuccess(List<ContentType> contentTypes);
    void onLoadGenreError(String message);
    void onLoadMoviesSuccess(List<Content> contents);
    void onLoadMoviesError(String message);
}
