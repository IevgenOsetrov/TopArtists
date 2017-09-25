package com.android.topartists.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Evgeniy on 01.09.2017.
 */

public class Artist extends RealmObject {

    @PrimaryKey
    private String uuid;
    private String name;
    private String listeners;
    private RealmList<Image> image;
    private String country;

    public Artist() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public RealmList<Image> getImage() {
        return image;
    }

    public void setImage(RealmList<Image> image) {
        this.image = image;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", listeners='" + listeners + '\'' +
                ", image=" + image +
                ", country='" + country + '\'' +
                "} " + super.toString();
    }
}
