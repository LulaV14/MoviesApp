package com.example.android.popularmoviesapp;

import android.content.Intent;

import com.example.android.popularmoviesapp.Helpers.GradientTransformation;
import com.example.android.popularmoviesapp.Model.Movie;
import com.squareup.picasso.Picasso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        // show back arrow on toolbar
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch(NullPointerException e) {
            Log.e(TAG, e.getMessage());
        }

        // get intent data
        Intent intent = getIntent();
        if (intent.hasExtra("MOVIE_OBJECT")) {
            Movie movie = (Movie) intent.getSerializableExtra("MOVIE_OBJECT");
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
                tv_vote_average.setText("(" + movie.getVoteAverage() + ")");
                rb_vote_average.setRating(vote_average.floatValue());
                tv_release_date.setText(movie.getReleaseDate());
            } catch(NullPointerException e) {
                Log.e(TAG, e.getMessage());
            }
        } else {
            closeOnError();
            Log.e(TAG, "No intent extra data found");
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Error while trying to view movie details. \nPlease try again.", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
