package com.example.pfeifle.mdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.api.client.http.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends AppCompatActivity {
    private String apiKey = "0e81ea650bb87e98021985bb7e90350d";
    private String searchMovieUrl = "https://api.themoviedb.org/3/search/movie?api_key="+apiKey+"&page=1&include_adult=true&language=de&query=";
    private String movieId;

    // text and buttons
    EditText movieName;
    Button searchBtn;

    // needed classes
    ApiAccess getRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init Text und Buttons
        movieName = (EditText) findViewById(R.id.movieName);
        searchBtn = (Button) findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                searchMovie(v);
            }
        });

    }

    void searchMovie(View v) {
        // Instantiate new instance of our class
        getRequest = new ApiAccess();

        movieId = getMovieId("red%20sparrow");

    }

    private String getMovieId(String name) {
        String id = null;
        try {
            // String = new ApiAccess().execute(url, name).get();
            id = new ApiAccess().execute(searchMovieUrl+name, "results").get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.i("MDB: ", "movieId= "+movieId);

        return id;
    }

}
