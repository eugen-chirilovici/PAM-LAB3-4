package com.example.lab34.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonCard implements Parcelable {


    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("imageUrlHiRes")
    private String imageUrlHiRes;

    @SerializedName("types")
    private List<String> types;

    @SerializedName("attacks")
    private List<CardAttack> cardAttacks;

    protected PokemonCard(Parcel in) {
        id = in.readString();
        name = in.readString();
        imageUrlHiRes = in.readString();
        types = in.createStringArrayList();
        cardAttacks = in.createTypedArrayList(CardAttack.CREATOR);
    }

    public static final Creator<PokemonCard> CREATOR = new Creator<PokemonCard>() {
        @Override
        public PokemonCard createFromParcel(Parcel in) {
            return new PokemonCard(in);
        }

        @Override
        public PokemonCard[] newArray(int size) {
            return new PokemonCard[size];
        }
    };

    public List<CardAttack> getCardAttacks() {
        return cardAttacks;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getName() {
        return name;
    }

    public String getImageUrlHiRes() {
        return imageUrlHiRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(imageUrlHiRes);
        parcel.writeStringList(types);
        parcel.writeTypedList(cardAttacks);
    }
}
