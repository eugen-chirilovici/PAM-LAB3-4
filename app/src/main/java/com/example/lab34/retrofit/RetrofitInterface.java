package com.example.lab34.retrofit;

import com.example.lab34.model.PokemonResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RetrofitInterface {

    @GET("v1/cards")
    Single<PokemonResponse> loadPokemonData();

}
