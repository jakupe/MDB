package com.example.pfeifle.mdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

public class Watchlist extends AppCompatActivity {

    private ListView listViewWatchlist;
    private Button deleteAllBtn;
    private List<Movie> ml = null;
    private static RefreshDisplay rd = null;
    private int idIs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        listViewWatchlist = findViewById(R.id.listViewWatchlist);
        deleteAllBtn      = findViewById(R.id.deleteAllBtn);

        // Interface
        finRefresh();

        // Onclick listener for delete btn
        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatabaseInitializer().deleteAllMovies(rd);
            }
        });

        display();
    }

    private void finRefresh() {
        rd = new RefreshDisplay() {
            @Override
            public void refresh() {
                display();
            }
        };
    }


    protected void display() {
        ml = Buffer.getMovieList();
        List valueList = new ArrayList<String>();
        if (ml.size() > 0) {
            deleteAllBtn.setEnabled(true);
            for (int i = 0; i < ml.size(); i++)
                valueList.add(ml.get(i).getTitle().toString());
        }
        else {
            valueList.add(getString(R.string.noMovieSaved));
            deleteAllBtn.setEnabled(false);
        }

        listViewWatchlist.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, valueList));
        listViewWatchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    idIs = i;
                    detail();
                } catch(Exception e){
                    Log.i("NoDetails", "No Details in ListView Object");
                    detailFail();
                }
            }
        });

    }

    private void detail() {
        new Buffer(ml.get(idIs), null);
        Intent intent = new Intent(this, DisplayDetail.class);
        intent.putExtra("extraData", "watchlist");
        startActivity(intent);
    }

    protected static RefreshDisplay getRefreshDisplay() {
        return rd;
    }

    private void detailFail() {
        //error if no details
        Toast.makeText(this, getString(R.string.noDetails), Toast.LENGTH_SHORT).show();
    }


}