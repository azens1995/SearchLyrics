package com.azens.searchlyrics;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.azens.searchlyrics.api.LyricsClient;
import com.azens.searchlyrics.api.ServiceGenerator;
import com.azens.searchlyrics.model.Lyrics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "";
    EditText inputArtist, inputSongsName;
    Button btnSearch;
    Call<Lyrics> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputArtist = (EditText) findViewById(R.id.inputArtist);
        inputSongsName = (EditText) findViewById(R.id.inputSongName);
        btnSearch = (Button) findViewById(R.id.buttonSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()){
                    String artist = inputArtist.getText().toString();
                    String name = inputSongsName.getText().toString();
                    LyricsClient client = ServiceGenerator.createService(LyricsClient.class);
                    call = client.getLyrics(artist,name);
                    loadLyrics();
                }else {
                    Toast.makeText(MainActivity.this, "No Internet connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void loadLyrics() {
        call.enqueue(new Callback<Lyrics>() {
            @Override
            public void onResponse(Call<Lyrics> call, Response<Lyrics> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "No lyrics found...", Toast.LENGTH_SHORT).show();
                }else {
                    Lyrics lyrics = response.body();
                    Intent intent = new Intent(MainActivity.this, LyricsActivity.class);
                    intent.putExtra("lyrics", lyrics.getLyrics());
                    MainActivity.this.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Lyrics> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error occured...", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private boolean isNetworkConnected () {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
