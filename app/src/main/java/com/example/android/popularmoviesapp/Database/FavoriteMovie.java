package com.example.android.popularmoviesapp.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.popularmoviesapp.Model.Movie;

@Entity(tableName = "favorite")
public class FavoriteMovie {

    @PrimaryKey
    private int id;
    private String title;
    private String overview;
    @ColumnInfo(name = "release_date")
    private String releaseDate;
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;
    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    public FavoriteMovie(int id, String title, String overview, String releaseDate,
                         String posterPath, String backdropPath, Double voteAverage) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getOverview() { return overview; }

    public void setOverview(String overview) { this.overview = overview; }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getPosterPath() { return posterPath; }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getBackdropPath() { return backdropPath; }

    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }

    public Double getVoteAverage() { return voteAverage; }

    public void setVoteAverage(Double voteAverage) { this.voteAverage = voteAverage; }

    public Movie toMovie() {
        return new Movie(id, title, overview, releaseDate, posterPath,
                backdropPath, voteAverage);
    }
}
