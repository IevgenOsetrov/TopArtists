package com.android.topartists.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.topartists.R;
import com.android.topartists.activity.TracksActivity;
import com.android.topartists.model.Album;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.android.topartists.utils.Constants.ALBUM_IMAGE_URL;
import static com.android.topartists.utils.Constants.ALBUM_NAME;
import static com.android.topartists.utils.Constants.ARTIST_NAME;

/**
 * Created by Evgeniy on 01.09.2017.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Album> albums;

    private static final String TAG = ArtistsAdapter.class.getSimpleName();

    public AlbumsAdapter(Context context, ArrayList<Album> albums) {
        this.context = context;
        this.albums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_albums_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Album album = albums.get(position);

        Glide.with(context).load(album.getImage().get(3).getUrl()).into(holder.photoImageView);
        holder.nameTextView.setText(album.getName());
        holder.countTextView.setText("(" + album.getPlaycount() + " times played)");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TracksActivity.class);
                intent.putExtra(ARTIST_NAME, album.getArtistName());
                intent.putExtra(ALBUM_NAME, album.getName());
                intent.putExtra(ALBUM_IMAGE_URL, album.getImage().get(3).getUrl());
                context.startActivity(intent);
                ((AppCompatActivity) context).overridePendingTransition(R.anim.activity_forward_in, R.anim.activity_forward_in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        TextView nameTextView;
        TextView countTextView;

        ViewHolder(View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.image);
            nameTextView = itemView.findViewById(R.id.name);
            countTextView = itemView.findViewById(R.id.count);
        }
    }
}
