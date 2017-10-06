package com.android.topartists.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.topartists.R;
import com.android.topartists.adapter.TracksAdapter;
import com.android.topartists.model.NetworkDisableMessage;
import com.android.topartists.model.NetworkEnableMessage;
import com.android.topartists.model.SingleAlbumDataObject;
import com.android.topartists.model.Track;
import com.android.topartists.server.ApiService;
import com.android.topartists.server.RetrofitApi;
import com.android.topartists.utils.Constants;
import com.android.topartists.utils.Utils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.topartists.utils.Constants.ALBUM_IMAGE_URL;
import static com.android.topartists.utils.Constants.ALBUM_NAME;
import static com.android.topartists.utils.Constants.ARTIST_NAME;

public class TracksActivity extends AppCompatActivity {

    private static final String TAG = TracksActivity.class.getSimpleName();
    private ListView tracksListView;
    private TracksAdapter tracksAdapter;
    private ArrayList<Track> tracks;
    private String artist;
    private String album;
    private String imageUrl;
    private ImageView poster;
    private Realm realm;
    private ApiService apiService;
    private ImageView indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        EventBus.getDefault().register(this);
        realm = Realm.getDefaultInstance();

        if (getIntent().getExtras() != null) {
            artist = getIntent().getExtras().getString(ARTIST_NAME);
            album = getIntent().getExtras().getString(ALBUM_NAME);
            imageUrl = getIntent().getExtras().getString(ALBUM_IMAGE_URL);
        }

        indicator = Utils.showToolbarInfo(this, album);

        tracksListView = findViewById(R.id.tracks);
        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_HORIZONTAL);
        poster = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300);
        layoutParams.setMargins(0, 20, 0, 20);
        header.addView(poster, layoutParams);
        tracksListView.addHeaderView(header);

        Glide.with(TracksActivity.this).load(imageUrl).into(poster);

        apiService = RetrofitApi.getInstance().getApiService();

        if (isTracksInRealm()) {
            tracksAdapter = new TracksAdapter(TracksActivity.this, tracks);
            tracksListView.setAdapter(tracksAdapter);
        } else {
            if (Utils.isConnectedToNetwork(this)) {
                getTracks(apiService);
            } else {
                Toast.makeText(TracksActivity.this, "Please enable internet connection to get data", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getTracks(ApiService apiService) {
        Call<SingleAlbumDataObject> tracksCall = apiService.getTracksOfAlbum(Constants.API_KEY, artist, album);
        findViewById(R.id.progress_layout).setVisibility(View.VISIBLE);
        tracksCall.enqueue(new Callback<SingleAlbumDataObject>() {
            @Override
            public void onResponse(Call<SingleAlbumDataObject> call, Response<SingleAlbumDataObject> response) {
                findViewById(R.id.progress_layout).setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    SingleAlbumDataObject responseData = response.body();
                    Log.d(TAG, "Tracks " + response.body());
                    tracks = responseData.getAlbum().getTracks().getTracks();

                    tracksAdapter = new TracksAdapter(TracksActivity.this, tracks);
                    tracksListView.setAdapter(tracksAdapter);

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            for (Track track : tracks) {
                                track.setUuid(UUID.randomUUID().toString());
                                track.setAlbum(album);
                                realm.copyToRealmOrUpdate(track);
                            }
                        }
                    });
                } else {
                    Log.d(TAG, "Response error " + response.raw().toString());
                    Toast.makeText(TracksActivity.this, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleAlbumDataObject> call, Throwable t) {
                Log.d(TAG, "Error " + t.getMessage());
                Toast.makeText(TracksActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                findViewById(R.id.progress_layout).setVisibility(View.GONE);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NetworkEnableMessage event) {

        Glide.with(this).load(R.drawable.green_indi).into(indicator);
        if (!isTracksInRealm()) {
            getTracks(apiService);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkDisableEvent(NetworkDisableMessage event) {
        Glide.with(this).load(R.drawable.red_indi).into(indicator);
    }

    private boolean isTracksInRealm() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tracks = new ArrayList<>(realm.where(Track.class).equalTo(
                        "album", album).findAll());

                Log.d(TAG, "Realm tracks " + tracks);
            }
        });

        return tracks != null && tracks.size() > 0;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }
}
