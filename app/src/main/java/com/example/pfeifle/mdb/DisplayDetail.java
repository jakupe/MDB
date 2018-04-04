package com.example.pfeifle.mdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pfeifle on 24.03.2018.
 */

public class DisplayDetail extends AppCompatActivity {
    private ListView lv;
    private Button saveBtn;
    private String extra = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        extra = intent.getStringExtra("extraData");

        // TODO: View
        lv      = findViewById(R.id.detailView);
        saveBtn = findViewById(R.id.addToWatchList);
        // TODO Btn ändern
        if(extra.equals("watchlist")) {
            saveBtn.setText("Film löschen");
        }

        // Add Onclick Listener for save btn
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Main.di.addMovie();
                new DatabaseInitializer().addMovie();
            }
        });
        display();

    }

    protected void display() {
        // get Movie Data
        Movie m = Buffer.getMovie();

        // Create List
        List valueList = new ArrayList<String>();

        // Add Text to List
        if(!m.getTitle().matches(""))
            valueList.add("Title \n"        + m.getTitle());
        if(!m.getGenres().matches(""))
            valueList.add("Genres \n"       + m.getGenres());
        if(!m.getOverview().matches(""))
            valueList.add("Overview \n"     + m.getOverview());
        if(!m.getHomepage().matches(""))
            valueList.add("Homepage \n"     + m.getHomepage());
        if(!m.getRuntime().matches(""))
            valueList.add("Runtime \n"      + m.getRuntime() + " Minutes");
        if(!m.getReleaseDate().matches(""))
            valueList.add("Release Date \n" + m.getReleaseDate());
        if(!m.getPopulation().matches(""))
            valueList.add("Population \n"   + m.getPopulation());
        if(!m.getVoteAverage().matches(""))
            valueList.add("Vote \n"         + m.getVoteAverage() + " / 10");
        if(!m.getVoteCount().matches(""))
            valueList.add("Vote Count \n"   + m.getVoteCount());
        if(!m.getOriginalLanguage().matches(""))
            valueList.add("Original Language \n"    + m.getOriginalLanguage());
        if(!m.getOriginalTitle().matches(""))
            valueList.add("Original Title \n"       + m.getOriginalTitle());

        // Show list
        lv.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, valueList));

    }

}
