package com.android.topartists.model;

import java.util.ArrayList;

/**
 * Created by Evgeniy on 02-Sep-17.
 */

public class SingleAlbum {

    private String name;
    private Tracks tracks;
    private ArrayList<Image> image;

    public SingleAlbum() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    public ArrayList<Image> getImage() {
        return image;
    }

    public void setImage(ArrayList<Image> image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "SingleAlbum{" +
                "name='" + name + '\'' +
                ", tracks=" + tracks +
                ", image=" + image +
                '}';
    }
}
