package com.longtv.zappy.ui.music;

import com.google.gson.JsonObject;
import com.longtv.zappy.base.BaseActivity;
import com.longtv.zappy.base.BaseCallback;
import com.longtv.zappy.base.BasePresenterImpl;
import com.longtv.zappy.base.BaseView;
import com.longtv.zappy.network.ServiceBuilder;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.DataListDTO;

public class HomeBoxMusicPresenterImpl extends BasePresenterImpl<HomeBoxMusicView> implements HomeBoxMusicPresenter{
    public HomeBoxMusicPresenterImpl(HomeBoxMusicView view) {
        super(view);
    }

    @Override
    public void getMusics(String search) {
        ServiceBuilder.getService().getMusics(search).enqueue(new BaseCallback<DataListDTO<Content>>() {
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
    public void getMusicDetail(int id) {
        ServiceBuilder.getService().getMusicDetail(id).enqueue(new BaseCallback<Content>() {
            @Override
            public void onError(String errorCode, String errorMessage) {

            }

            @Override
            public void onResponse(Content data) {
                mView.doLoadMusicDetail(data);
            }
        });
    }

    @Override
    public void getContentRelated(String id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("genres.id", "$in:" + id);
        ServiceBuilder.getService().getMusicByGenre(jsonObject).enqueue(new BaseCallback<DataListDTO<Content>>() {
            @Override
            public void onError(String errorCode, String errorMessage) {

            }

            @Override
            public void onResponse(DataListDTO<Content> data) {
                mView.doLoadContentRelated(data);
            }
        });
    }
}
