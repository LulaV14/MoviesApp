package com.example.android.popularmoviesapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorite")
    LiveData<List<FavoriteMovie>> loadAllFavorites();

    @Insert
    void insertFavorite(FavoriteMovie favorite);

    @Delete
    void deleteFavorite(FavoriteMovie favorite);

    @Query("SELECT * FROM favorite WHERE id = :id")
    LiveData<FavoriteMovie> loadFavoriteById(int id);

}
