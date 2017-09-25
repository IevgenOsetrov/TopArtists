package com.android.topartists.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Evgeniy on 02-Sep-17.
 */

public class Track extends RealmObject {

    @PrimaryKey
    private String uuid;
    private String name;
    private int duration;
    private String album;

    public Track() {
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Track{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", album='" + album + '\'' +
                "} " + super.toString();
    }
}
