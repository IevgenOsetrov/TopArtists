package com.android.topartists.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.topartists.R;
import com.android.topartists.model.Track;

import java.util.List;

/**
 * Created by Evgeniy on 02-Sep-17.
 */

public class TracksAdapter extends ArrayAdapter<Track> {

    public TracksAdapter(@NonNull Context context, @NonNull List<Track> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Track track = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tracks_list, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.track);
        int minutes = track.getDuration() / 60;
        int seconds = track.getDuration() - (track.getDuration() / 60) * 60;
        tvName.setText((position + 1) + ". " + track.getName() + " (" + (minutes < 10 ? "0" + minutes : minutes) +
                ":" + (seconds < 10 ? "0" + seconds : seconds) + ")");
        return convertView;
    }
}
