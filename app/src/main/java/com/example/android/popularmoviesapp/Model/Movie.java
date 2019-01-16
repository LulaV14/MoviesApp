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

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("video")
    private Boolean video;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("vote_count")
    private Integer voteCount;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("adult")
    private Boolean adult;

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

    public Double getPopularity() {
        return popularity;
    }

    public Boolean getVideo() {
        return video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public Boolean getAdult() {
        return adult;
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
        parcel.writeDouble(this.popularity);
        parcel.writeInt(this.video ? 1 : 0);
        parcel.writeDouble(this.voteAverage);
        parcel.writeInt(this.voteCount);
        parcel.writeString(this.originalLanguage);
        parcel.writeString(this.originalTitle);
        parcel.writeList(this.genreIds);
        parcel.writeInt(this.adult ? 1 : 0);
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
        this.popularity = in.readDouble();
        this.video = in.readInt() != 0;
        this.voteAverage = in.readDouble();
        this.voteCount = in.readInt();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.genreIds = new ArrayList<>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.adult = in.readInt() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isValid() {
        return this.id != null && this.title != null;
    }
}
