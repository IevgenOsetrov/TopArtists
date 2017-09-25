package com.android.topartists.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Evgeniy on 01.09.2017.
 */

public class TopArtistsData {

    @SerializedName("artist")
    private ArrayList<Artist> artists;

    @SerializedName("@attr")
    private TopArtistsAttribute attribute;

    public TopArtistsData() {
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public TopArtistsAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(TopArtistsAttribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "TopArtistsData{" +
                "artists=" + artists +
                ", attribute=" + attribute +
                '}';
    }
}
