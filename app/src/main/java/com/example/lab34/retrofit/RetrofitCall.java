package com.example.lab34.retrofit;

import com.example.lab34.model.PokemonResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCall {

    private static RetrofitCall retrofitInstance;
    private static Retrofit retrofit;

    public static String BASE_URL = "https://api.pokemontcg.io/";

    public static RetrofitCall getInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new RetrofitCall();
        }
        return retrofitInstance;
    }

    public static Retrofit getRetrofit(String url) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .writeTimeout(30, TimeUnit.MILLISECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    public static Single<PokemonResponse> getAllCardsData(String baseUrl) {
        RetrofitInterface retrofitInterface = getRetrofit(baseUrl).create(RetrofitInterface.class);
        return retrofitInterface.loadPokemonData();
    }
}
