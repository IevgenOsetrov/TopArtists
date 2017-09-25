package com.android.topartists.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.android.topartists.R;
import com.android.topartists.model.CountryImage;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Evgeniy on 02-Sep-17.
 */

public class ImageArrayAdapter extends ArrayAdapter<CountryImage> {

    private Context context;

    public ImageArrayAdapter(Context context, List<CountryImage> images) {
        super(context, 0, images);
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    private View getImageForPosition(int position, View convertView, ViewGroup parent) {
        CountryImage countryImage = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_country_spinner, parent, false);
        }

        ImageView iconImageView = convertView.findViewById(R.id.icon);
        Glide.with(context).load(countryImage.getResId()).into(iconImageView);
        return convertView;
    }
}
