package com.android.topartists.server;

import com.android.topartists.model.AlbumsDataObject;
import com.android.topartists.model.ArtistsDataObject;
import com.android.topartists.model.SingleAlbumDataObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("?method=geo.gettopartists&format=json")
    Call<ArtistsDataObject> getTopArtists(@Query("api_key") String apiKey,
                                          @Query("country") String country);

    @Headers("Content-Type: application/json")
    @GET("?method=artist.gettopalbums&format=json")
    Call<AlbumsDataObject> getTopAlbums(@Query("api_key") String apiKey,
                                        @Query("artist") String artistName);

    @Headers("Content-Type: application/json")
    @GET("?method=album.getinfo&format=json")
    Call<SingleAlbumDataObject> getTracksOfAlbum(@Query("api_key") String apiKey,
                                                 @Query("artist") String artistName,
                                                 @Query("album") String albumName);
}