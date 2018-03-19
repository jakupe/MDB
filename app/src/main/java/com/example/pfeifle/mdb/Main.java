package com.example.pfeifle.mdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main extends AppCompatActivity {
    private String apiKey = "0e81ea650bb87e98021985bb7e90350d";
    private String searchMovieUrl = "https://api.themoviedb.org/3/search/movie?api_key="+apiKey+"&page=1&include_adult=true&language=de&query=";
    private String getDetailsUrl1 = "https://api.themoviedb.org/3/movie/";
    private String getDetailsUrl2 = "?language=de&api_key=" + apiKey;

    // text and buttons
    EditText movieName;
    Button searchBtn;
    ListView lv;

    // needed classes
    Movie movies[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init Text und Buttons
        movieName = (EditText) findViewById(R.id.movieName);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        lv = (ListView) findViewById(R.id.listView);

        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                searchMovie(v);
            }
        });

    }

    void searchMovie(View v) {
        if(!movieName.getText().toString().matches("")) {
            getMovies(movieName.getText().toString());
            display();
        } else
            Toast.makeText(this, "Bitte Filmname eingeben", Toast.LENGTH_SHORT).show();

        /*
        for (int i=0; i<movies.length; i++)
            Log.i("MDB", "movie Name= "+movies[i].title);
        */

    }

    private void getMovies(String name) {
        // clear field
        movieName.setText("");

        try {
            // get results
            JSONObject jo = new ApiAccess().execute(searchMovieUrl+name).get();
            String results = jo.getString("results");
            results = results.substring(1);
            results = results.substring(0, results.length() - 1);
            jo = new JSONObject("{\"res\":["+results+"]}");

            // extracting results from data
            JSONArray ja = jo.getJSONArray("res");
            int len = ja.length();
            movies = new Movie[len];

            // loop to split results and get movies
            for(int i=0; i<len; i++)
                movies[i] = new Movie(new ApiAccess().execute(getDetailsUrl1
                        + new JSONObject(ja.getString(i)).getString("id") // get id
                        + getDetailsUrl2).get());
        }
        catch (InterruptedException e)  { e.printStackTrace(); }
        catch (ExecutionException e)    { e.printStackTrace(); }
        catch (JSONException e)         { e.printStackTrace(); }
    }


    private void display() {
        List valueList = new ArrayList<String>();
        for(int i=0; i<10; i++)
        valueList.add(movies[i].title);

        ListAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, valueList);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO details

            }
        });


    }

}
