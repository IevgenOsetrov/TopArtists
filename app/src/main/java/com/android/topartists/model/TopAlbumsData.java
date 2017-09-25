package com.android.topartists.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Evgeniy on 01.09.2017.
 */

public class TopAlbumsData {

    @SerializedName("album")
    private ArrayList<Album> albums;

    public TopAlbumsData() {
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        return "TopAlbumsData{" +
                "albums=" + albums +
                '}';
    }
}
