package com.longtv.zappy.network;

import com.google.gson.JsonObject;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.LoginRequest;
import com.longtv.zappy.network.dto.ResponseDTO;
import com.longtv.zappy.network.dto.SignupRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ZappyService {

    @GET("users/{name}/commits")
    Call<List<String>> getCommitsByName(@Path("name") String name);
    @POST("/")
    Call<ResponseDTO<LoginData>> login(@Body LoginRequest request);
    @POST("/")
    Call<ResponseDTO<LoginData>> signup(@Body SignupRequest request);

    @GET("genre/movie")
    Call<ResponseDTO<List<ContentType>>> getGenreMovie();

    @GET("movie")
    Call<ResponseDTO<List<Content>>> getMovies(@Query("filter")JsonObject filter);
}
