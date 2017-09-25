package com.android.topartists.server;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Evgeniy on 24.02.2017.
 */

public class RetrofitApi {

    private static RetrofitApi instance;
    private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/";

    private ApiService apiService;

    public static RetrofitApi getInstance() {
        if (instance == null) {
            instance = new RetrofitApi();
        }

        return instance;
    }

    private RetrofitApi() {
        buildRetrofit(BASE_URL);
    }

    private void buildRetrofit(String url) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
