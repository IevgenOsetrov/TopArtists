package com.android.topartists.utils;

import com.android.topartists.R;
import com.android.topartists.model.CountryImage;

import java.util.ArrayList;

/**
 * Created by Evgeniy on 01.09.2017.
 */

public class Constants {

    public static final String API_KEY = "e81f61890b7ff8633ca024d0faa449e7";
    public static final String ARTIST_NAME = "artist_name";
    public static final String ALBUM_NAME = "album_name";
    public static final String ALBUM_IMAGE_URL = "album_image_url";
    public static final ArrayList<CountryImage> COUNTRIES = new ArrayList<CountryImage>() {{
        add(new CountryImage(R.drawable.ua, "ukraine"));
        add(new CountryImage(R.drawable.usa, "united states"));
        add(new CountryImage(R.drawable.hn, "honduras"));
    }};
}
