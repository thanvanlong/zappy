package com.longtv.zappy.network;

import android.util.Log;


import com.longtv.zappy.App;
import com.longtv.zappy.BuildConfig;
import com.longtv.zappy.utils.PrefManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {
    private static final String BASE_URL = BuildConfig.BASE_URL;
//    private static final String BASE_URL = "https://d6da-171-224-177-51.ngrok-free.app/";

    private static Retrofit sInstance;
    private static Retrofit sInstancePayment;
    private static ZappyService dreamService;
    private synchronized static Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        long nomalTimeout = 15;
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(nomalTimeout, TimeUnit.SECONDS)
                .writeTimeout(nomalTimeout, TimeUnit.SECONDS)
                .connectTimeout(nomalTimeout, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder();
                        builder.addHeader("Content-Type", "application/json");
                        if (!PrefManager.getAccessToken(App.getInstance()).matches("")){
                            builder.addHeader("Authorization", "Bearer " + PrefManager.getAccessToken(App.getInstance()));
                        }
                        Request request = builder.method(original.method(), original.body()).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        if (sInstance == null) {
            // User agent
            sInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }
        return sInstance;
    }

    private static Retrofit getRetrofitWithoutAuth(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        long nomalTimeout = 15;
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(nomalTimeout, TimeUnit.SECONDS)
                .writeTimeout(nomalTimeout, TimeUnit.SECONDS)
                .connectTimeout(nomalTimeout, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();

        if (sInstance == null) {
            // User agent
            sInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }
        return sInstance;
    }

    public static ZappyService getServiceWithoutAuth(){
        if(dreamService == null) {
            dreamService = getRetrofitWithoutAuth().create(ZappyService.class);
        }
        return dreamService;
    }

    public static ZappyService getService() {
        if (dreamService == null) {
            dreamService = getRetrofit().create(ZappyService.class);
        }
        return dreamService;
    }

}
