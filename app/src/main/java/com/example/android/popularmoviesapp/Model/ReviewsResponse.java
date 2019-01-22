package com.example.android.popularmoviesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewsResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<Review> results;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("total_results")
    private int total_results;

    public int getId() { return id; }

    public int getPage() { return page; }

    public ArrayList<Review> getResults() { return results; }

    public int getTotalPages() { return total_pages; }

    public int getTotalResults() { return total_results; }
}
