package com.example.android.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private final static String POPULAR_MOVIES = "popular";
    private final static String TOP_RATED_MOVIES = "top_rated";

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
        showMovies(POPULAR_MOVIES);
    }

    public int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 95);
    }

    public void showMovies(String type) {
        TMDBInterface TMDB_service = TMDBController.getClient().create(TMDBInterface.class);
        Call<MoviesResponse> call;
        switch(type) {
            case POPULAR_MOVIES:
                call = TMDB_service.getPopularMovies(TMDB_API_KEY);
                getSupportActionBar().setTitle(R.string.filter_most_popular);
                break;
            case TOP_RATED_MOVIES:
                call = TMDB_service.getTopRatedMovies(TMDB_API_KEY);
                getSupportActionBar().setTitle(R.string.filter_top_rated);
                break;
            default:
                call = TMDB_service.getPopularMovies(TMDB_API_KEY);
                getSupportActionBar().setTitle(R.string.filter_most_popular);
                break;
        }
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
                    Log.e(TAG, response.message());
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

    @Override
    public void onClick(int movie_position) {
        Movie clickedMovie = movies.get(movie_position);
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
        detailIntent.putExtra("MOVIE_OBJECT", clickedMovie);
        startActivity(detailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.most_popular_filter:
                showMovies(POPULAR_MOVIES);
                break;
            case R.id.top_rated_filter:
                showMovies(TOP_RATED_MOVIES);
                break;
            default:
                showMovies(POPULAR_MOVIES);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
