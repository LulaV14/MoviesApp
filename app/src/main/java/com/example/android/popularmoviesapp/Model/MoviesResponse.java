package com.example.android.popularmoviesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesResponse {
    @SerializedName("results")
    private ArrayList<Movie> results;

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    public ArrayList<Movie> getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
