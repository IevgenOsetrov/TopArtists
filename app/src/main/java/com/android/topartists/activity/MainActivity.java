package com.android.topartists.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.topartists.R;
import com.android.topartists.adapter.ArtistsAdapter;
import com.android.topartists.adapter.ImageArrayAdapter;
import com.android.topartists.model.Artist;
import com.android.topartists.model.ArtistsDataObject;
import com.android.topartists.model.CountryImage;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.topartists.utils.Constants.COUNTRIES;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView artistsRecyclerView;
    private ArtistsAdapter adapter;
    private ArrayList<Artist> artists;
    private ApiService apiService;
    private Realm realm;
    private Spinner countrySpinner;
    private ImageView indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView titleTextView = toolbar.findViewById(R.id.toolbar_title);
        titleTextView.setText(R.string.pop_artists);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        indicator = toolbar.findViewById(R.id.indicator);
        if (Utils.isConnectedToNetwork(MainActivity.this)) {
            Glide.with(this).load(R.drawable.green_indi).into(indicator);
        } else {
            Glide.with(this).load(R.drawable.red_indi).into(indicator);
        }

        countrySpinner = toolbar.findViewById(R.id.spinner);
        countrySpinner.setVisibility(View.VISIBLE);

        ImageArrayAdapter imageArrayAdapter = new ImageArrayAdapter(this, COUNTRIES);
        countrySpinner.setAdapter(imageArrayAdapter);

        realm = Realm.getDefaultInstance();

        artistsRecyclerView = (RecyclerView) findViewById(R.id.artists_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(artistsRecyclerView.getContext(),
                manager.getOrientation());
        artistsRecyclerView.addItemDecoration(dividerItemDecoration);
        artistsRecyclerView.setLayoutManager(manager);

        apiService = RetrofitApi.getInstance().getApiService();

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int position, long l) {

                if (isArtistsInRealm()) {
                    adapter = new ArtistsAdapter(MainActivity.this, sort(artists));
                    artistsRecyclerView.setAdapter(adapter);
                } else {
                    if (Utils.isConnectedToNetwork(MainActivity.this)) {
                        getTopArtistByCountry(apiService, COUNTRIES.get(position).getName());
                    } else {
                        Toast.makeText(MainActivity.this, "Please enable internet connection to get data", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private ArrayList<Artist> sort(ArrayList<Artist> artists) {
        Collections.sort(artists, new Comparator<Artist>() {
            @Override
            public int compare(Artist artist1, Artist artist2) {
                return (artist1.getName()).compareTo(artist2.getName());
            }
        });

        return artists;
    }

    private void getTopArtistByCountry(ApiService apiService, String country) {
        Call<ArtistsDataObject> artistsCall = apiService.getTopArtists(Constants.API_KEY, country);
        findViewById(R.id.progress_layout).setVisibility(View.VISIBLE);
        artistsCall.enqueue(new Callback<ArtistsDataObject>() {
            @Override
            public void onResponse(Call<ArtistsDataObject> call, Response<ArtistsDataObject> response) {
                findViewById(R.id.progress_layout).setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    final ArtistsDataObject responseData = response.body();
                    Log.d(TAG, "Artists " + response.body());
                    artists = responseData.getTopartists().getArtists();

                    adapter = new ArtistsAdapter(MainActivity.this, sort(artists));
                    artistsRecyclerView.setAdapter(adapter);

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            for (Artist artist : artists) {
                                artist.setUuid(UUID.randomUUID().toString());
                                artist.setCountry(responseData.getTopartists().getAttribute().getCountry().toLowerCase());
                                realm.copyToRealmOrUpdate(artist);
                            }
                        }
                    });
                } else {
                    Log.d(TAG, "Response error " + response.raw().toString());
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArtistsDataObject> call, Throwable t) {
                Log.d(TAG, "Error " + t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                findViewById(R.id.progress_layout).setVisibility(View.GONE);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkEnableEvent(NetworkEnableMessage event) {
        Glide.with(this).load(R.drawable.green_indi).into(indicator);
        if (!isArtistsInRealm()) {
            getTopArtistByCountry(apiService, ((CountryImage) countrySpinner.getSelectedItem()).getName());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkDisableEvent(NetworkDisableMessage event) {
        Glide.with(this).load(R.drawable.red_indi).into(indicator);
    }

    private boolean isArtistsInRealm() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                artists = new ArrayList<>(realm.where(Artist.class).equalTo(
                        "country", ((CountryImage) countrySpinner.getSelectedItem()).getName()).findAll());

                Log.d(TAG, "Realm artists " + artists);
            }
        });

        return artists != null && artists.size() > 0;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
