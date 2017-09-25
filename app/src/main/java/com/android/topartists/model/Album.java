package com.android.topartists.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Evgeniy on 01.09.2017.
 */

public class Album extends RealmObject {

    @PrimaryKey
    private String uuId;
    private String name;
    private int playcount;
    private RealmList<Image> image;
    private String artistName;

    public Album() {
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public RealmList<Image> getImage() {
        return image;
    }

    public void setImage(RealmList<Image> image) {
        this.image = image;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "Album{" +
                "uuId='" + uuId + '\'' +
                ", name='" + name + '\'' +
                ", playcount=" + playcount +
                ", image=" + image +
                ", artistName='" + artistName + '\'' +
                "} " + super.toString();
    }
}
