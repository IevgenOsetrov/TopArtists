package com.android.topartists.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Evgeniy on 01.09.2017.
 */

public class ArtistsDataObject {

    private TopArtistsData topartists;

    public ArtistsDataObject() {
    }

    public TopArtistsData getTopartists() {
        return topartists;
    }

    public void setTopartists(TopArtistsData topartists) {
        this.topartists = topartists;
    }

    @Override
    public String toString() {
        return "ArtistsDataObject{" +
                "topartists=" + topartists +
                '}';
    }
}
