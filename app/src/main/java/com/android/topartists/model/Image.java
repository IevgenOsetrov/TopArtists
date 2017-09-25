package com.android.topartists.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Evgeniy on 01.09.2017.
 */

public class Image extends RealmObject {

    @SerializedName("#text")
    private String url;

    private String size;

    public Image() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
