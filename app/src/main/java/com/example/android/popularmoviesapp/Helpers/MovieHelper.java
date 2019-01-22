package com.example.android.popularmoviesapp.Helpers;

import com.example.android.popularmoviesapp.Database.FavoriteMovie;
import com.example.android.popularmoviesapp.Model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieHelper {

    public static ArrayList<Movie> convertToMovie(List<FavoriteMovie> favorites) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (FavoriteMovie favorite: favorites) {
            movies.add(favorite.toMovie());
        }
        return movies;
    }
}
