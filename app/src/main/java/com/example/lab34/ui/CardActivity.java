package com.example.lab34.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.lab34.R;
import com.example.lab34.model.PokemonCard;

public class CardActivity extends AppCompatActivity {

    private PokemonCard pokemonCard;
    private ImageView pokemonCardImage;
    private LinearLayout cardType;
    private TextView cardTypeText;
    private ImageView pokemonCardPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ImageView backButton = findViewById(R.id.card_bar_back_arrow);

        Toolbar toolbar = findViewById(R.id.card_toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = findViewById(R.id.card_toolbar_title);

        cardType = findViewById(R.id.activity_card_type);
        cardTypeText = findViewById(R.id.activity_card_type_text);

        pokemonCardImage = findViewById(R.id.activity_card_image);
        pokemonCardPlaceholder = findViewById(R.id.activity_card_placeholder);

        if (getIntent().getExtras() != null) {
            pokemonCard = getIntent().getParcelableExtra("Card");
        }

        if (pokemonCard != null) {
            toolbarTitle.setText(pokemonCard.getName());
            if (!pokemonCard.getTypes().get(0).equals("null")){
                setupCardType(pokemonCard.getTypes().get(0));
            }
            Glide.with(this)
                    .load(pokemonCard.getImageUrlHiRes())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pokemonCardPlaceholder.setVisibility(View.VISIBLE);
                            showLoadingPlaceholder("start");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            showLoadingPlaceholder("stop");
                            pokemonCardPlaceholder.setVisibility(View.INVISIBLE);
                            pokemonCardImage.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(pokemonCardImage);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setupCardType(String typeOfCard){
        switch (typeOfCard){
            case "Bug":
                cardType.setBackgroundResource(R.drawable.type_bug);
                cardTypeText.setText("Bug");
                break;
            case "Colorless":
                cardType.setBackgroundResource(R.drawable.type_colorless);
                cardTypeText.setText("Colorless");
                break;
            case "Darkness":
                cardType.setBackgroundResource(R.drawable.type_dark);
                cardTypeText.setText("Darkness");
                break;
            case "Dragon":
                cardType.setBackgroundResource(R.drawable.type_dragon);
                cardTypeText.setText("Dragon");
                break;
            case "Lightning":
                cardType.setBackgroundResource(R.drawable.type_electric);
                cardTypeText.setText("Lightning");
                break;
            case "Fairy":
                cardType.setBackgroundResource(R.drawable.type_fairy);
                cardTypeText.setText("Fairy");
                break;
            case "Fighting":
                cardType.setBackgroundResource(R.drawable.type_fighting);
                cardTypeText.setText("Fighting");
                break;
            case "Fire":
                cardType.setBackgroundResource(R.drawable.type_fire);
                cardTypeText.setText("Fire");
                break;
            case "Flying":
                cardType.setBackgroundResource(R.drawable.type_flying);
                cardTypeText.setText("Flying");
                break;
            case "Ghost":
                cardType.setBackgroundResource(R.drawable.type_ghost);
                cardTypeText.setText("Ghost");
                break;
            case "Grass":
                cardType.setBackgroundResource(R.drawable.type_grass);
                cardTypeText.setText("Grass");
                break;
            case "Ground":
                cardType.setBackgroundResource(R.drawable.type_ground);
                cardTypeText.setText("Ground");
                break;
            case "Ice":
                cardType.setBackgroundResource(R.drawable.type_ice);
                cardTypeText.setText("Ice");
                break;
            case "Poison":
                cardType.setBackgroundResource(R.drawable.type_poison);
                cardTypeText.setText("Poison");
                break;
            case "Psychic":
                cardType.setBackgroundResource(R.drawable.type_psychic);
                cardTypeText.setText("Psychic");
                break;
            case "Rock":
                cardType.setBackgroundResource(R.drawable.type_rock);
                cardTypeText.setText("Rock");
                break;
            case "Metal":
                cardType.setBackgroundResource(R.drawable.type_steel);
                cardTypeText.setText("Metal");
                break;
            case "Water":
                cardType.setBackgroundResource(R.drawable.type_water);
                cardTypeText.setText("Water");
                break;
        }
    }

    private void showLoadingPlaceholder(String action) {
        AnimationDrawable animationDrawable = (AnimationDrawable) pokemonCardPlaceholder.getDrawable();
        switch (action) {
            case "start":
                animationDrawable.start();
                break;
            case "stop":
                animationDrawable.stop();
                break;
        }
    }
}
