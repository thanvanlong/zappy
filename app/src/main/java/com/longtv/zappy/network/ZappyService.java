package com.longtv.zappy.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ZappyService {

    @GET("users/{name}/commits")
    Call<List<String>> getCommitsByName(@Path("name") String name);

}
