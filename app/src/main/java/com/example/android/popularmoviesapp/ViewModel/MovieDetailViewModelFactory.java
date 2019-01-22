package com.example.android.popularmoviesapp.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.popularmoviesapp.Database.AppDatabase;

public class MovieDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase database;
    private final int favoriteId;

    public MovieDetailViewModelFactory(AppDatabase database, int favoriteId) {
        this.database = database;
        this.favoriteId = favoriteId;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MovieDetailViewModel(database, favoriteId);
    }
}
