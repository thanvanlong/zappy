package com.longtv.zappy.network;

import com.google.gson.JsonObject;
import com.longtv.zappy.network.dto.Chapter;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.network.dto.LoginData;
import com.longtv.zappy.network.dto.PackagePayment;
import com.longtv.zappy.network.dto.Profile;
import com.longtv.zappy.network.dto.LoginRequest;
import com.longtv.zappy.network.dto.ResponseDTO;
import com.longtv.zappy.network.dto.SignupRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ZappyService {

    @GET("users/{name}/commits")
    Call<List<String>> getCommitsByName(@Path("name") String name);
    @POST("user/login")
    Call<ResponseDTO<LoginData>> login(@Body LoginRequest request);
    @POST("user/register")
    Call<ResponseDTO<Object>> signup(@Body SignupRequest request);

    @PATCH("user/active")
    Call<ResponseDTO<Object>> active(@Query("email") String email, @Query("accessToken") String token);

    @POST("user/profile")
    Call<ResponseDTO<Object>> createProfile(@Body Profile profile);
    @GET("genre/list/gr?type=2")
    Call<ResponseDTO<DataListDTO<ContentType>>> getGenreMovie();

    @GET("genre/list/gr?type=0")
    Call<ResponseDTO<DataListDTO<ContentType>>> getGenreStory();


    @GET("movie")
    Call<ResponseDTO<DataListDTO<Content>>> getMovies(@Query("filter") JsonObject object);

    @GET("movie")
    Call<ResponseDTO<DataListDTO<Content>>> searchMovies(@Query("search") String object);

    @POST("profile/login")
    Call<ResponseDTO<LoginData>> loginProfile(@Body JsonObject id);

    @GET("package")
    Call<ResponseDTO<DataListDTO<PackagePayment>>> getPackage();

    @POST("payment")
    Call<ResponseDTO<Object>> doPayment(@Body JsonObject jsonObject);

    @POST("payment/return")
    Call<ResponseDTO<Integer>> verifyPayment(@Body JsonObject jsonObject);

    @GET("music")
    Call<ResponseDTO<DataListDTO<Content>>> getMusics(@Query("search") String object);

    @GET("user/me")
    Call<ResponseDTO<LoginData>> getMe();
    @POST("movie/buy")
    Call<ResponseDTO<Boolean>> buyMovie(@Body JsonObject jsonObject);

    @GET("movie/{id}")
    Call<ResponseDTO<Content>> getMovieDetail(@Path("id") String id);

    @POST("library/add-library-name")
    Call<ResponseDTO<Object>> addLibraryName(@Body JsonObject jsonObject);

    @GET("movie")
    Call<ResponseDTO<DataListDTO<Content>>> getMoviesByGenre(@Query("filter") JsonObject object);

    @GET("music")
    Call<ResponseDTO<DataListDTO<Content>>> getMusicByGenre(@Query("filter") JsonObject object);

    @GET("music/{id}")
    Call<ResponseDTO<Content>> getMusicDetail(@Path("id") int id);

    @POST("profile")
    Call<ResponseDTO<Profile>> saveProfile(@Body Profile profile);

    @GET("movie?sortBy=desc:views")
    Call<ResponseDTO<DataListDTO<Content>>> getBannerMovie();
    @GET("comics?sortBy=desc:views")
    Call<ResponseDTO<DataListDTO<Content>>> getBannerStory();
    @GET("music?sortBy=desc:views")
    Call<ResponseDTO<DataListDTO<Content>>> getBannerMusic();
    @GET("chapter/{id}")
    Call<ResponseDTO<DataListDTO<Chapter>>> getChapter(@Path("id") String id);
}
