package com.longtv.zappy.ui.film;

import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.network.dto.DataListDTO;

import java.util.List;

public interface HomeBoxFilmView extends BaseView<HomeBoxFilmPresenter> {
    void onLoadGenreSuccess(DataListDTO<ContentType> contentTypes);
    void onLoadGenreError(String message);
    void onLoadMoviesSuccess(DataListDTO<Content> data);
    void onLoadMoviesError(String message);

    void onLoadSearchMoviesSuccess(DataListDTO<Content> data);
    void onLoadSearchMoviesError(String message);
    void onBuySuccess(Boolean data);
    void doLoadFilmDetail(Content content);
}
