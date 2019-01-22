package com.example.android.popularmoviesapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import com.example.android.popularmoviesapp.API.TMDBController;
import com.example.android.popularmoviesapp.API.TMDBInterface;
import com.example.android.popularmoviesapp.Adapters.ReviewAdapter;
import com.example.android.popularmoviesapp.Adapters.VideoAdapter;
import com.example.android.popularmoviesapp.Database.AppDatabase;
import com.example.android.popularmoviesapp.Database.FavoriteMovie;
import com.example.android.popularmoviesapp.Helpers.AppExecutors;
import com.example.android.popularmoviesapp.Helpers.GradientTransformation;
import com.example.android.popularmoviesapp.Model.Movie;
import com.example.android.popularmoviesapp.Model.Review;
import com.example.android.popularmoviesapp.Model.ReviewsResponse;
import com.example.android.popularmoviesapp.Model.Video;
import com.example.android.popularmoviesapp.Model.VideosResponse;
import com.example.android.popularmoviesapp.ViewModel.MovieDetailViewModel;
import com.example.android.popularmoviesapp.ViewModel.MovieDetailViewModelFactory;
import com.squareup.picasso.Picasso;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity
        implements VideoAdapter.VideoOnClickHandler {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    private static final String MOVIE_INTENT_KEY = "MOVIE_OBJECT";
    private static final String TMDB_API_KEY = BuildConfig.TMDB_ApiKey;

    private ArrayList<Video> videos;
    private VideoAdapter videoAdapter;
    private ArrayList<Review> reviews;
    private ReviewAdapter reviewAdapter;
    private int movieId;

    private Movie movie;
    private AppDatabase database;
    private FavoriteMovie favorite;

    @BindView(R.id.iv_backdrop_image)
    ImageView iv_backdrop_image;
    @BindView(R.id.tv_movie_title)
    TextView tv_title;
    @BindView(R.id.tv_movie_overview)
    TextView tv_overview;
    @BindView(R.id.tv_vote_average)
    TextView tv_vote_average;
    @BindView(R.id.rb_vote_average)
    RatingBar rb_vote_average;
    @BindView(R.id.tv_release_date)
    TextView tv_release_date;
    @BindView(R.id.rv_videos)
    RecyclerView rv_videos;
    @BindView(R.id.rv_reviews)
    RecyclerView rv_reviews;
    @BindView(R.id.iv_favorite_icon)
    ImageView iv_favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        database = AppDatabase.getInstance(getApplicationContext());

        // show back arrow on toolbar
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch(NullPointerException e) {
            Log.e(TAG, e.getMessage());
        }

        // get intent data
        Intent intent = getIntent();
        if (intent.hasExtra(MOVIE_INTENT_KEY)) {
            movie = intent.getParcelableExtra(MOVIE_INTENT_KEY);
            if (movie == null) {
                closeOnError();
                Log.e(TAG, "No movie object serialized");
            }
            try {
                Picasso.get()
                        .load(movie.getBackdropImageUrl())
                        .placeholder(R.drawable.backdrop_image_loading)
                        .error(R.drawable.backdrop_image_error)
                        .fit()
                        .centerInside()
                        .transform(new GradientTransformation())
                        .into(iv_backdrop_image);
                tv_title.setText(movie.getTitle());
                tv_overview.setText(movie.getOverview());
                Double vote_average = movie.getVoteAverage() / 2;
                tv_vote_average.setText(movie.getVoteAverage() + "/10");
                rb_vote_average.setRating(vote_average.floatValue());
                tv_release_date.setText(movie.getReleaseDate());
                movieId = movie.getId();

                // set favorite
                iv_favorite.setOnClickListener(favoriteListener);
                MovieDetailViewModelFactory factory = new MovieDetailViewModelFactory(database, movieId);
                final MovieDetailViewModel viewModel =
                        ViewModelProviders.of(this, factory).get(MovieDetailViewModel.class);
                viewModel.getFavorite().observe(this, new Observer<FavoriteMovie>() {
                    @Override
                    public void onChanged(@Nullable FavoriteMovie favoriteMovie) {
                        favorite = favoriteMovie;
                        if (favoriteMovie == null) {
                            iv_favorite.setImageResource(android.R.drawable.star_off);
                            return;
                        }
                        iv_favorite.setImageResource(android.R.drawable.star_on);
                    }
                });
            } catch(NullPointerException e) {
                Log.e(TAG, e.getMessage());
            }

        } else {
            closeOnError();
            Log.e(TAG, "No intent extra data found");
        }


        // show videos
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_videos.setLayoutManager(mLayoutManager);
        rv_videos.addItemDecoration(
                new DividerItemDecoration(
                        this,
                        DividerItemDecoration.VERTICAL
                )
        );

        if (savedInstanceState != null && savedInstanceState.containsKey("videos_list")) {
            videos = savedInstanceState.getParcelableArrayList("videos_list");
            videoAdapter= new VideoAdapter(videos, MovieDetailActivity.this::onClick);
            rv_videos.setAdapter(videoAdapter);
        } else {
            showVideos(movieId);
        }

        // show reviews
        RecyclerView.LayoutManager reviewsLayoutManager = new LinearLayoutManager(this);
        rv_reviews.setLayoutManager(reviewsLayoutManager);

        if (savedInstanceState != null && savedInstanceState.containsKey("reviews_list")) {
            reviews = savedInstanceState.getParcelableArrayList("reviews_list");
            reviewAdapter= new ReviewAdapter(reviews);
            rv_reviews.setAdapter(reviewAdapter);
        } else {
            showReviews(movieId);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(
                this,
                "Error while trying to view movie details. \nPlease try again.",
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("videos_list", videos);
        outState.putParcelableArrayList("reviews_list", reviews);
        super.onSaveInstanceState(outState);
    }

    private void showVideos(int movie_id) {
        TMDBInterface TMDB_service = TMDBController.getClient().create(TMDBInterface.class);
        Call<VideosResponse> call = TMDB_service.getMovieVideos(movie_id, TMDB_API_KEY);
        call.enqueue(new Callback<VideosResponse>() {
            @Override
            public void onResponse(Call<VideosResponse> call, Response<VideosResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        videos = response.body().getResults();
                        videoAdapter = new VideoAdapter(videos, MovieDetailActivity.this::onClick);
                        rv_videos.setAdapter(videoAdapter);
                    } catch (NullPointerException e) {
                        Log.e(TAG, e.getMessage());
                    }
                } else {
                    Log.e(TAG, response.errorBody().toString());
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<VideosResponse> call, Throwable t) {
                Toast.makeText(
                        MovieDetailActivity.this,
                        "Error while loading movie videos. Please try again.",
                        Toast.LENGTH_LONG
                ).show();

                Log.e(TAG, "Error while trying to load movie videos");
                t.printStackTrace();
            }
        });
    }

    private void showReviews(int movie_id) {
        TMDBInterface TMDB_service = TMDBController.getClient().create(TMDBInterface.class);
        Call<ReviewsResponse> call = TMDB_service.getMovieReviews(movie_id, TMDB_API_KEY);
        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        reviews = response.body().getResults();
                        reviewAdapter = new ReviewAdapter(reviews);
                        rv_reviews.setAdapter(reviewAdapter);
                    } catch (NullPointerException e) {
                        Log.e(TAG, e.getMessage());
                    }
                } else {
                    Log.e(TAG, response.errorBody().toString());
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                Toast.makeText(
                        MovieDetailActivity.this,
                        "Error while loading movie reviews.\nPlease try again",
                        Toast.LENGTH_LONG
                ).show();

                Log.e(TAG, "Error while trying to load movie reviews");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(int video_position) {
        Video clickedVideo = videos.get(video_position);
        if (clickedVideo.getSite().equals("YouTube")) {
            Uri appUri = Uri.parse("vnd.youtube:" + clickedVideo.getKey());
            Uri browserUri = Uri.parse("http://www.youtube.com/watch?v=" + clickedVideo.getKey());
            Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, appUri);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
            try {
                this.startActivity(youtubeIntent);
            } catch (ActivityNotFoundException e) {
                this.startActivity(browserIntent);
            }
        }
    }

    private View.OnClickListener favoriteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final FavoriteMovie favoriteMovie =
                    new FavoriteMovie(
                            movie.getId(),
                            movie.getTitle(),
                            movie.getOverview(),
                            movie.getReleaseDate(),
                            movie.getPosterPath(),
                            movie.getBackdropPath(),
                            movie.getVoteAverage()
                    );
            AppExecutors.getInstance().diskIO().execute(getUpdateDbRunnable(favoriteMovie));
            AppExecutors.getInstance().mainThread().execute(updateUITextRunnable);
        }
    };

    private Runnable getUpdateDbRunnable(FavoriteMovie favoriteMovie) {
        return new Runnable() {
            @Override
            public void run() {
                if (favorite == null) {
                    database.movieDao().insertFavorite(favoriteMovie);
                } else {
                    database.movieDao().deleteFavorite(favoriteMovie);
                }
            }
        };
    }

    private Runnable updateUITextRunnable = new Runnable() {
        @Override
        public void run() {
            String textMessage = favorite == null ?
                    "favorite movie added!" :
                    "favorite movie deleted!";
            Toast.makeText(getApplicationContext(), textMessage, Toast.LENGTH_SHORT).show();
        }
    };
}
