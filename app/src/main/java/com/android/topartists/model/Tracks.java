package com.android.topartists.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Evgeniy on 02-Sep-17.
 */

public class Tracks {

    @SerializedName("track")
    private ArrayList<Track> tracks;

    public Tracks() {
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "Tracks{" +
                "tracks=" + tracks +
                '}';
    }
}
