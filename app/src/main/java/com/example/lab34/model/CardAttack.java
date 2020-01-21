package com.example.lab34.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CardAttack implements Parcelable {

    @SerializedName("name")
    private String name;
    @SerializedName("text")
    private String text;

    public CardAttack(String name, String text) {
        this.name = name;
        this.text = text;
    }

    protected CardAttack(Parcel in) {
        name = in.readString();
        text = in.readString();
    }

    public static final Creator<CardAttack> CREATOR = new Creator<CardAttack>() {
        @Override
        public CardAttack createFromParcel(Parcel in) {
            return new CardAttack(in);
        }

        @Override
        public CardAttack[] newArray(int size) {
            return new CardAttack[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(text);
    }
}
