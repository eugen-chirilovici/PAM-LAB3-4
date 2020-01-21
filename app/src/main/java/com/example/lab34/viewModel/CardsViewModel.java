package com.example.lab34.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.lab34.model.PokemonResponse;
import com.example.lab34.retrofit.RetrofitCall;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CardsViewModel extends AndroidViewModel {

    public CardsViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<PokemonResponse> pokemonResponseLiveData = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void loadAllCardsFromPublicApi() {
        compositeDisposable.add(loadRemoteCards()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PokemonResponse>() {
                    @Override
                    public void accept(PokemonResponse pokemonResponse) {
                        Log.i("VModel", "accept: Success!");
                        pokemonResponseLiveData.postValue(pokemonResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("VModel", "accept: Failed!", throwable);
                    }
                }));
    }

    private Single<PokemonResponse> loadRemoteCards() {
        return RetrofitCall.getInstance().getAllCardsData(RetrofitCall.BASE_URL);
    }

    public MutableLiveData<PokemonResponse> getLiveData() {
        return pokemonResponseLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
