package com.example.android.popularmoviesapp.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmoviesapp.Database.AppDatabase;
import com.example.android.popularmoviesapp.Database.FavoriteMovie;

public class MovieDetailViewModel extends ViewModel {

    private LiveData<FavoriteMovie> favorite;

    public MovieDetailViewModel(AppDatabase database, int favoriteId) {
        favorite = database.movieDao().loadFavoriteById(favoriteId);
    }

    public LiveData<FavoriteMovie> getFavorite() { return favorite; }

}
