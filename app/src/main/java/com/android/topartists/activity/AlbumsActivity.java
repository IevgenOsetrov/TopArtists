package com.android.topartists.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.topartists.R;
import com.android.topartists.adapter.AlbumsAdapter;
import com.android.topartists.model.Album;
import com.android.topartists.model.AlbumsDataObject;
import com.android.topartists.model.NetworkDisableMessage;
import com.android.topartists.model.NetworkEnableMessage;
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

public class AlbumsActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView albumsRecyclerView;
    private AlbumsAdapter adapter;
    private ArrayList<Album> albums;
    private String artistName;
    private Realm realm;
    private ApiService apiService;
    private ImageView indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        EventBus.getDefault().register(this);
        realm = Realm.getDefaultInstance();

        if (getIntent().getExtras() != null) {
            artistName = getIntent().getExtras().getString(Constants.ARTIST_NAME);
        }

        indicator = Utils.showToolbarInfo(this, artistName);

        albumsRecyclerView = (RecyclerView) findViewById(R.id.albums_list);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        albumsRecyclerView.setLayoutManager(mLayoutManager);
        albumsRecyclerView.addItemDecoration(new SpacesItemDecoration(5));
        albumsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        albumsRecyclerView.setAdapter(adapter);

        apiService = RetrofitApi.getInstance().getApiService();
        if (isAlbumsInRealm()) {
            adapter = new AlbumsAdapter(AlbumsActivity.this, albums);
            albumsRecyclerView.setAdapter(adapter);
        } else {
            if (Utils.isConnectedToNetwork(this)) {
                getAlbums(apiService);
            } else {
                Toast.makeText(AlbumsActivity.this, "Please enable internet connection to get data", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getAlbums(ApiService apiService) {
        Call<AlbumsDataObject> albumsCall = apiService.getTopAlbums(Constants.API_KEY, artistName);
        findViewById(R.id.progress_layout).setVisibility(View.VISIBLE);
        albumsCall.enqueue(new Callback<AlbumsDataObject>() {
            @Override
            public void onResponse(Call<AlbumsDataObject> call, Response<AlbumsDataObject> response) {
                findViewById(R.id.progress_layout).setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    AlbumsDataObject responseData = response.body();
                    Log.d(TAG, "Albums " + response.body());
                    albums = responseData.getTopalbums().getAlbums();

                    adapter = new AlbumsAdapter(AlbumsActivity.this, albums);
                    albumsRecyclerView.setAdapter(adapter);

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            for (Album album : albums) {
                                album.setUuId(UUID.randomUUID().toString());
                                album.setArtistName(artistName);
                                realm.copyToRealmOrUpdate(album);
                            }
                        }
                    });
                } else {
                    Log.d(TAG, "Response error " + response.raw().toString());
                    Toast.makeText(AlbumsActivity.this, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlbumsDataObject> call, Throwable t) {
                Log.d(TAG, "Error " + t.getMessage());
                Toast.makeText(AlbumsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                findViewById(R.id.progress_layout).setVisibility(View.GONE);
            }
        });
    }

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NetworkEnableMessage event) {
        Glide.with(this).load(R.drawable.green_indi).into(indicator);
        if (!isAlbumsInRealm()) {
            getAlbums(apiService);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkDisableEvent(NetworkDisableMessage event) {
        Glide.with(this).load(R.drawable.red_indi).into(indicator);
    }

    private boolean isAlbumsInRealm() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                albums = new ArrayList<>(realm.where(Album.class).equalTo(
                        "artistName", artistName).findAll());

                Log.d(TAG, "Realm albums " + albums);
            }
        });

        return albums != null && albums.size() > 0;
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
