package com.example.lab34.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonResponse {

    @SerializedName("cards")
    private List<PokemonCard> pokemonCardsList;

    public PokemonResponse(List<PokemonCard> pokemonCardsList) {
        this.pokemonCardsList = pokemonCardsList;
    }

    public List<PokemonCard> getPokemonCardsList() {
        return pokemonCardsList;
    }
}
