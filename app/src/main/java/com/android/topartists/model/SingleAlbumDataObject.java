package com.android.topartists.model;

/**
 * Created by Evgeniy on 02-Sep-17.
 */

public class SingleAlbumDataObject {

    private SingleAlbum album;

    public SingleAlbumDataObject() {
    }

    public SingleAlbum getAlbum() {
        return album;
    }

    public void setAlbum(SingleAlbum album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "SingleAlbumDataObject{" +
                "album=" + album +
                '}';
    }
}
