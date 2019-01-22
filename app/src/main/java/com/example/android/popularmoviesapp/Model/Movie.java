package com.example.android.popularmoviesapp.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Movie implements Parcelable {
    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_average")
    private Double voteAverage;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
            Date parsedReleaseDate = formatter.parse(releaseDate);
            formatter = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
            return formatter.format(parsedReleaseDate);
        } catch (Exception e) {
            Log.e("Movie model", e.getMessage());
            return "";
        }
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    // get correct image url
    public String getPosterImageUrl() {
        return "http://image.tmdb.org/t/p/w185" + getPosterPath();
    }

    public String getBackdropImageUrl() { return "http://image.tmdb.org/t/p/w780" + getBackdropPath(); }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.overview);
        parcel.writeString(this.releaseDate);
        parcel.writeString(this.posterPath);
        parcel.writeString(this.backdropPath);
        parcel.writeDouble(this.voteAverage);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie> () {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.voteAverage = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isValid() {
        return this.id != null && this.title != null;
    }

    public Movie(int id, String title, String overview, String releaseDate,
                 String posterPath, String backdropPath, Double voteAverage) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
    }
}
