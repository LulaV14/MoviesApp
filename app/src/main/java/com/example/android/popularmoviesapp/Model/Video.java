package com.example.android.popularmoviesapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Video implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    @SerializedName("site")
    private String site;

    @SerializedName("type")
    private String type;

    @SerializedName("size")
    private int size;

    @SerializedName("iso_639_1")
    private String iso_639_1;

    @SerializedName("iso_3166_1")
    private String iso_3166_1;

    public String getId() { return id; }

    public String getKey() { return key; }

    public String getName() { return name; }

    public String getSite() { return site; }

    public String getType() { return type; }

    public int getSize() { return size; }

    public String getIso_639_1() { return iso_639_1; }

    public String getIso_3166_1() { return iso_3166_1; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeString(type);
        parcel.writeInt(size);
        parcel.writeString(iso_639_1);
        parcel.writeString(iso_3166_1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Video(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.type = in.readString();
        this.size = in.readInt();
        this.iso_639_1 = in.readString();
        this.iso_3166_1 = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        public Video createFromParcel(Parcel in) { return new Video(in); }

        public Video[] newArray(int size) { return new Video[size]; }
    };
}
