package com.example.pfeifle.mdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private String noMovieFoudMessage = "Kein Film gefunden!";
    private String noMovie = "Bitte Filmname eingeben!";
    private String noNetworkMessage = "Bitte Netzwerkverbindung prüfen!";

    private ListView lv;
    private EditText movieName;
    private Button searchBtn;

    private JSONObject jo;
    private Movie movies[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init
        lv = (ListView) findViewById(R.id.listView);
        movieName = (EditText) findViewById(R.id.movieName);
        searchBtn = (Button) findViewById(R.id.searchBtn);

        // Add Onclick Listener
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                searchMovie(v);
            }
        });

    }

    void searchMovie(View v) {
        if(!movieName.getText().toString().matches(""))
            getMovies(movieName.getText().toString());
        else
            Toast.makeText(this, noMovie, Toast.LENGTH_SHORT).show();
    }

    private void getMovies(String name) {
        // clear field
        movieName.setText("");

        try {
            // get results
            jo = new ApiAccess().execute(searchMovieUrl+name).get();
            if (jo == null) {
                Toast.makeText(this, noNetworkMessage, Toast.LENGTH_SHORT).show();
                return;
            }
            String results = jo.getString("results");
            results = results.substring(1);
            results = results.substring(0, results.length() - 1);

            // extracting results from data
            JSONArray ja = new JSONObject("{\"res\":["+results+"]}").getJSONArray("res");
            movies = new Movie[ja.length()];

            // loop to split results and get movies
            for(int i=0; i<ja.length(); i++) {
                jo = new JSONObject(ja.getString(i));
                movies[i] = new Movie(jo.getString("id"), jo.getString("title"));
            }
        }
        catch (InterruptedException e)  { e.printStackTrace(); }
        catch (ExecutionException e)    { e.printStackTrace(); }
        catch (JSONException e)         { e.printStackTrace(); }

        display();
    }

    private void display() {
        // List movie names
        List valueList = new ArrayList<String>();
        if (movies.length > 0)
            for (int i=0; i<movies.length; i++)
                valueList.add(movies[i].getTitle());
        else
            valueList.add(noMovieFoudMessage);

        // Show list and make clickable
        lv.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, valueList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                detail(i);
            }
        });

    }

    private void detail(int i) {
        try {
            jo= new ApiAccess().execute(getDetailsUrl1+movies[i].getId()+getDetailsUrl2).get();
            if (jo == null) {
                Toast.makeText(this, noNetworkMessage, Toast.LENGTH_SHORT).show();
                return;
            }
            new Buffer(movies[i], jo);
            startActivity(new Intent(this, DisplayDetail.class));
        }
        catch (InterruptedException e)  { e.printStackTrace(); }
        catch (ExecutionException e)    { e.printStackTrace(); }

    }

}
