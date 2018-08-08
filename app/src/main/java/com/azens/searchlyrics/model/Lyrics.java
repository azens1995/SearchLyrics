package com.azens.searchlyrics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Azens Eklak on 8/8/18.
 */
public class Lyrics {

    @SerializedName("lyrics")
    @Expose
    private String lyrics;

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
