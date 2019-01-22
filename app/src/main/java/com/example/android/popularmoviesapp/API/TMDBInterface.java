package com.example.android.popularmoviesapp.API;

import com.example.android.popularmoviesapp.Model.MoviesResponse;
import com.example.android.popularmoviesapp.Model.ReviewsResponse;
import com.example.android.popularmoviesapp.Model.VideosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBInterface {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<VideosResponse> getMovieVideos(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewsResponse> getMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);
}
