package com.example.pfeifle.mdb;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

public class Main extends AppCompatActivity {
    private String apiKey   = "0e81ea650bb87e98021985bb7e90350d"
            ,searchMovieUrl = "https://api.themoviedb.org/3/search/movie?api_key="+apiKey+"&page=1&include_adult=true&language=de&query="
            ,getDetailsUrl1 = "https://api.themoviedb.org/3/movie/"
            ,getDetailsUrl2 = "?language=de&api_key="+apiKey
            ,noMovieFoudMsg = "Kein Film gefunden!"
            ,noMovieMsg     = "Bitte Filmname eingeben!"
            ,networkErrorMsg= "Bitte Netzwerkverbindung prüfen!";

    private ListView lv;
    private EditText movieName;
    private Button searchBtn;
    private Button watchlistBtn;


    private int idIs = 0;
    private Movie movies[];
    private ApiResponse ar = null;
    private ApiAccess aa = null;
    //public static DatabaseInitializer di;
	public static MovieDatabase mdb;

    //public static Main INSTANCE;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init
        lv              = findViewById(R.id.listView);
        movieName       = findViewById(R.id.movieName);
        searchBtn       = findViewById(R.id.searchBtn);
        watchlistBtn    = findViewById(R.id.watchlistBtn);

        apiRes();

        // Add Onclick Listener
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                searchMovie(v);
            }
        });

        // Add Onclick Listener for Watchlist Btn
        watchlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // new watchlist activity
                startActivity(new Intent(Main.this, Watchlist.class));
            }
        });

        // create database
        DbAsyncTask dbat = new DbAsyncTask();
        dbat.execute();



        Main.context = getApplicationContext();

   }

    void searchMovie(View v) {
        aa = new ApiAccess(ar);
        if(!movieName.getText().toString().matches(""))
            aa.execute(searchMovieUrl+movieName.getText().toString());
        else
            Toast.makeText(this, noMovieMsg, Toast.LENGTH_SHORT).show();
    }

    private void listFinish(JSONObject jo) {
        // clear field
        movieName.setText("");

        // get results
        try {
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
        catch (JSONException e) { e.printStackTrace(); }
        display();
    }

    private void detailFinish(JSONObject jo) {
        // clicked for more details
        new Buffer(movies[idIs], jo);
        Intent intent = new Intent(this, DisplayDetail.class);
        intent.putExtra("extraData", "main");
        startActivity(intent);
        //startActivity(new Intent(this, DisplayDetail.class));
    }

    private void fail() {
        // Error if JSON is null
        Toast.makeText(this, networkErrorMsg, Toast.LENGTH_SHORT).show();
    }

    private void display() {
        // List movie names
        List valueList = new ArrayList<String>();
        if (movies.length > 0)
            for (int i=0; i<movies.length; i++)
                valueList.add(movies[i].getTitle());
        else
            valueList.add(noMovieFoudMsg);

        // Show list and make clickable
        lv.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, valueList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // Onclick Listener for Details
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                aa = new ApiAccess(ar);
                aa.execute(getDetailsUrl1+movies[i].getId()+getDetailsUrl2);
                idIs = i;
            }
        });
    }

    private void apiRes() {
        ar = new ApiResponse() {
            @Override
            public void finish(JSONObject jo) {
                if (jo == null)
                    fail();
                else if (jo.has("adult"))
                    detailFinish(jo);
                else
                    listFinish(jo);
            }
        };
    }
	
	
	    public class DbAsyncTask extends AsyncTask<Void, Void, MovieDatabase> {


        @Override
        protected MovieDatabase doInBackground(Void... params) {

            MovieDatabase db;

            final String DATABASE_NAME = "MovieDatabase";


            db = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, DATABASE_NAME).build();

            return db;


        }

        @Override
        protected void onPostExecute(MovieDatabase db) {


mdb = db;
// Load saved movies from database
            new DatabaseInitializer().fillBuffer();

        }







    }


    protected static Context getMainContext() {
        return Main.context;
    }




}
