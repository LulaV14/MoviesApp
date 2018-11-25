package com.example.android.popularmoviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.popularmoviesapp.API.TMDBController;
import com.example.android.popularmoviesapp.API.TMDBInterface;
import com.example.android.popularmoviesapp.Model.Movie;
import com.example.android.popularmoviesapp.Model.MoviesResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String TMDB_API_KEY = BuildConfig.TMDB_ApiKey;

    @BindView(R.id.rv_movies)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TMDBInterface TMDB_service = TMDBController.getClient().create(TMDBInterface.class);
        Call<MoviesResponse> call = TMDB_service.getPopularMovies(TMDB_API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getResults();
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

                Log.e(TAG, "Errow while trying to load movies");
                t.printStackTrace();
            }
        });
    }
}
