package com.azens.searchlyrics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LyricsActivity extends AppCompatActivity {
    TextView tvLyrics;
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvLyrics = (TextView) findViewById(R.id.tvLyrics);
        String songsLyrics =getIntent().getExtras().getString("lyrics");
        tvLyrics.setText(songsLyrics);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
