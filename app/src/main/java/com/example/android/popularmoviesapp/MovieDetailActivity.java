package com.example.android.popularmoviesapp;

import android.content.Intent;
import com.example.android.popularmoviesapp.Model.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        TextView tv_movie_title = (TextView) findViewById(R.id.tv_example_movie_title);

        //get intent data
        Intent intent = getIntent();
        if (intent.hasExtra("MOVIE_OBJECT")) {
            Movie movie = (Movie) intent.getSerializableExtra("MOVIE_OBJECT");
            tv_movie_title.setText(movie.getTitle());
        }
    }
}
