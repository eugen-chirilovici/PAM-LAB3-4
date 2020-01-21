package com.example.lab34.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab34.R;
import com.example.lab34.adapters.PokemonCardsAdapter;
import com.example.lab34.model.PokemonCard;
import com.example.lab34.model.PokemonResponse;
import com.example.lab34.viewModel.CardsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CardsViewModel cardsViewModel;
    private PokemonCardsAdapter cardsAdapter;
    private RecyclerView recyclerView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        final ProgressBar requestProgressBar = findViewById(R.id.main_request_progress_bar);
        requestProgressBar.setIndeterminate(true);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = findViewById(R.id.pokemon_toolbar_title);
        toolbarTitle.setText("Pokémon Cards");

        cardsViewModel = ViewModelProviders.of(this).get(CardsViewModel.class);
        cardsViewModel.loadAllCardsFromPublicApi();
        LiveData<PokemonResponse> liveData = cardsViewModel.getLiveData();
        liveData.observe(this, new Observer<PokemonResponse>() {
            @Override
            public void onChanged(PokemonResponse pokemonResponse) {
                // TODO: Custom animation
                requestProgressBar.setVisibility(View.INVISIBLE);
                List<PokemonCard> pokemonCards = pokemonResponse.getPokemonCardsList();
                for (int i = 0; i < pokemonCards.size(); i++) {
                    Log.d("Poker", "onChanged() returned: " + pokemonCards.get(i).getName());
                    Log.d("Poker", "onChanged() returned: " + pokemonCards.get(i).getTypes());
                }
                setupAdapter(pokemonResponse.getPokemonCardsList());

            }
        });
    }

    private void setupAdapter(List<PokemonCard> cardList) {
        cardsAdapter = new PokemonCardsAdapter(this, cardList, new PokemonCardsAdapter.PokemonCardClickListener() {
            @Override
            public void onClick(PokemonCard pokemonCard) {
            }
        });
        recyclerView = findViewById(R.id.cards_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2,
                RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(cardsAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
