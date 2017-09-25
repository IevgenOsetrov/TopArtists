package com.android.topartists.model;

/**
 * Created by Evgeniy on 02-Sep-17.
 */

public class CountryImage {

    private int resId;
    private String name;

    public CountryImage() {
    }

    public CountryImage(int resId, String name) {
        this.resId = resId;
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
