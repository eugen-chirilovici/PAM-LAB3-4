package com.example.lab34.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.lab34.R;
import com.example.lab34.model.PokemonCard;
import com.example.lab34.ui.CardActivity;

import java.util.List;

public class PokemonCardsAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<PokemonCard> pokemonCardsList;
    private PokemonCardClickListener pokemonCardClickListener;

    public PokemonCardsAdapter(Context context, List<PokemonCard> pokemonCardsList, PokemonCardClickListener pokemonCardClickListener) {
        this.context = context;
        this.pokemonCardsList = pokemonCardsList;
        this.pokemonCardClickListener = pokemonCardClickListener;
    }

    class PokemonCardsViewHolder extends RecyclerView.ViewHolder {

        private CardView cardItem;
        private ImageView cardImage, cardPlaceholder;
        private TextView cardName;

        PokemonCardsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardName = itemView.findViewById(R.id.card_item_name);
            cardImage = itemView.findViewById(R.id.card_item_image);
            cardItem = itemView.findViewById(R.id.card_item);
            cardPlaceholder = itemView.findViewById(R.id.card_item_image_placeholder);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cards_recycler_item, parent, false);
        return new PokemonCardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final PokemonCardsViewHolder cardsViewHolder = (PokemonCardsViewHolder) holder;

        if (pokemonCardsList != null) {
            cardsViewHolder.cardName.setText(pokemonCardsList.get(position).getName());
            Glide.with(context).load(pokemonCardsList.get(position).getImageUrlHiRes())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            cardsViewHolder.cardPlaceholder.setVisibility(View.VISIBLE);
                            showLoadingPlaceholder(cardsViewHolder,"start");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            showLoadingPlaceholder(cardsViewHolder,"stop");
                            cardsViewHolder.cardPlaceholder.setVisibility(View.INVISIBLE);
                            cardsViewHolder.cardImage.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(cardsViewHolder.cardImage);
            cardsViewHolder.cardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setupPreviewDialog(pokemonCardsList.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return pokemonCardsList.size();
    }

    public void updateAdapter(List<PokemonCard> pokemonCardsList) {
        this.pokemonCardsList = pokemonCardsList;
        notifyDataSetChanged();
    }

    public interface PokemonCardClickListener {
        void onClick(PokemonCard pokemonCard);
    }

    private void setupPreviewDialog(final PokemonCard pokemonCard) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.card_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.custom_dialog).create();

        ImageView cardPreviewImage = dialogView.findViewById(R.id.dialog_card_preview_image);
        ConstraintLayout openButton = dialogView.findViewById(R.id.open_preview_button);
        ConstraintLayout closeButton = dialogView.findViewById(R.id.close_preview_button);

        Glide.with(dialogView).load(pokemonCard.getImageUrlHiRes()).into(cardPreviewImage);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CardActivity.class);
                intent.putExtra("Card",pokemonCard);
                context.startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        //window.setAttributes(layoutParams);
        layoutParams.dimAmount = 0.3f;
        alertDialog.getWindow().setAttributes(layoutParams);
    }

    private void showLoadingPlaceholder(PokemonCardsViewHolder viewHolder, String action) {
        AnimationDrawable animationDrawable = (AnimationDrawable) viewHolder.cardPlaceholder.getDrawable();
        switch (action) {
            case "start":
                animationDrawable.start();
                break;
            case "stop":
                animationDrawable.stop();
        }
    }
}
