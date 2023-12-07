package com.longtv.zappy.network;

import com.longtv.zappy.network.dto.LoginData;
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
}
