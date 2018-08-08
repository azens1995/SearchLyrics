package com.azens.searchlyrics;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    TextView tvLyrics;
    Call<Lyrics> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputArtist = (EditText) findViewById(R.id.inputArtist);
        inputSongsName = (EditText) findViewById(R.id.inputSongName);
        btnSearch = (Button) findViewById(R.id.buttonSearch);
        tvLyrics = (TextView) findViewById(R.id.lyrics);
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
                    tvLyrics.setText("No internet connection");
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
                    tvLyrics.append(lyrics.getLyrics());
                }
            }

            @Override
            public void onFailure(Call<Lyrics> call, Throwable t) {
                tvLyrics.append("Error occurred while getting request!");
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
