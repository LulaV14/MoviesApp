package com.example.android.popularmoviesapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.android.popularmoviesapp.Database.AppDatabase;
import com.example.android.popularmoviesapp.Database.FavoriteMovie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<FavoriteMovie>> favorites;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the favorite movies from the database");
        favorites = database.movieDao().loadAllFavorites();
    }

    public LiveData<List<FavoriteMovie>> getFavorites() { return favorites; }

}
