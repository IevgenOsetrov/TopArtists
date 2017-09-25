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
import com.android.topartists.activity.AlbumsActivity;
import com.android.topartists.model.Artist;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.android.topartists.utils.Constants.ARTIST_NAME;

/**
 * Created by Evgeniy on 01.09.2017.
 */

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Artist> artists;

    private static final String TAG = ArtistsAdapter.class.getSimpleName();

    public ArtistsAdapter(Context context, ArrayList<Artist> artists) {
        this.context = context;
        this.artists = artists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artists_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Artist artist = artists.get(position);

        Glide.with(context).load(artist.getImage().get(2).getUrl()).into(holder.photoImageView);
        holder.nameTextView.setText(artist.getName());
        holder.listenersTextView.setText("(" + artist.getListeners() + " listeners)");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlbumsActivity.class);
                intent.putExtra(ARTIST_NAME, artist.getName());
                context.startActivity(intent);
                ((AppCompatActivity) context).overridePendingTransition(R.anim.activity_forward_in, R.anim.activity_forward_in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        TextView nameTextView;
        TextView listenersTextView;

        ViewHolder(View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.image);
            nameTextView = itemView.findViewById(R.id.name);
            listenersTextView = itemView.findViewById(R.id.listeners);
        }
    }
}
