package com.android.topartists.model;

/**
 * Created by Evgeniy on 02-Sep-17.
 */

public class TopArtistsAttribute {

    private String country;

    public TopArtistsAttribute() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "TopArtistsAttribute{" +
                "country='" + country + '\'' +
                '}';
    }
}
