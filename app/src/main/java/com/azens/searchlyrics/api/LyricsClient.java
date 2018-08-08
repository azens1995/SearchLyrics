package com.azens.searchlyrics.api;

import com.azens.searchlyrics.model.Lyrics;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Azens Eklak on 8/8/18.
 */
public interface LyricsClient {
    @GET("{artist}/{title}")
    Call<Lyrics> getLyrics(
            @Path("artist") String artist, @Path("title") String title
    );
}
