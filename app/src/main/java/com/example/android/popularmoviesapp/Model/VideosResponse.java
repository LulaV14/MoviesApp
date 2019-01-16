package com.example.android.popularmoviesapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VideosResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private ArrayList<Video> results;

    public int getId() { return id; }

    public ArrayList<Video> getResults() { return results; }
}
