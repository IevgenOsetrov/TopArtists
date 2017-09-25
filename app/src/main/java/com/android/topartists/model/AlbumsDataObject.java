package com.android.topartists.model;

/**
 * Created by Evgeniy on 01.09.2017.
 */

public class AlbumsDataObject {

    private TopAlbumsData topalbums;

    public AlbumsDataObject() {
    }

    public TopAlbumsData getTopalbums() {
        return topalbums;
    }

    public void setTopalbums(TopAlbumsData topalbums) {
        this.topalbums = topalbums;
    }

    @Override
    public String toString() {
        return "AlbumsDataObject{" +
                "topalbums=" + topalbums +
                '}';
    }
}
