package com.example.pfeifle.mdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;



public class Watchlist extends AppCompatActivity {

    private ListView listViewWatchlist;
    private Button deleteAllBtn;
    private List<Movie> ml = null;
    private String noMovieSaved = "Es wurde noch kein Film gespeichert";
    private int idIs = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        listViewWatchlist = (ListView) findViewById(R.id.listViewWatchlist);
        deleteAllBtn      = (Button)   findViewById(R.id.deleteAllBtn);

        // Onclick listener for delete btn
        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatabaseInitializer().deleteAllMovies();
                //Main.di.deleteAllMovies();
            }
        });

        display();
    }

    //TODO implement listView with movies from db

    private void display() {
        ml = Buffer.getMovieList();
        List valueList = new ArrayList<String>();
        if (ml.size() > 0)
            for (int i=0; i<ml.size(); i++)
                valueList.add(ml.get(i).getTitle().toString());
        else
            valueList.add(noMovieSaved);

        listViewWatchlist.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, valueList));
        listViewWatchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idIs = i;
                detail();
            }
        });

    }

    private void detail() {
        new Buffer(ml.get(idIs), null);
        Intent intent = new Intent(this, DisplayDetail.class);
        intent.putExtra("extraData", "watchlist");
        startActivity(intent);
    }

}