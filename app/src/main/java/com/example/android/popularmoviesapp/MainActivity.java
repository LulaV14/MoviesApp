package com.example.android.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.example.android.popularmoviesapp.API.TMDBController;
import com.example.android.popularmoviesapp.API.TMDBInterface;
import com.example.android.popularmoviesapp.Adapters.MovieAdapter;
import com.example.android.popularmoviesapp.Model.Movie;
import com.example.android.popularmoviesapp.Model.MoviesResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieOnClickHandler {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String TMDB_API_KEY = BuildConfig.TMDB_ApiKey;

    private MovieAdapter movieAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Movie> movies;
    @BindView(R.id.rv_movies)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        int columns = calculateNoOfColumns(this);
        mLayoutManager = new GridLayoutManager(this, columns);
        recyclerView.setLayoutManager(mLayoutManager);

        TMDBInterface TMDB_service = TMDBController.getClient().create(TMDBInterface.class);
        Call<MoviesResponse> call = TMDB_service.getPopularMovies(TMDB_API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        movies = response.body().getResults();
                        movieAdapter = new MovieAdapter(movies, MainActivity.this::onClick);
                        recyclerView.setAdapter(movieAdapter);
                    } catch (NullPointerException e) {
                        Log.e(TAG, e.getMessage());
                    }
                } else {
                    Log.e(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Toast.makeText(
                    MainActivity.this,
                    "There was an error when loading movies. Please try again.",
                    Toast.LENGTH_LONG
                ).show();

                Log.e(TAG, "Error while trying to load movies");
                t.printStackTrace();
            }
        });
    }

    public int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 95);
    }

    @Override
    public void onClick(int movie_position) {
        Movie clickedMovie = movies.get(movie_position);
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
        detailIntent.putExtra("MOVIE_OBJECT", clickedMovie);
        startActivity(detailIntent);
    }
}
