package com.example.pfeifle.mdb;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        lv = (ListView) findViewById(R.id.detailView);
        saveBtn = (Button) findViewById(R.id.addToWatchList);

        // Add Onclick Listener
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO
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
        if(!m.title.matches(""))
            valueList.add("Title \n"        + m.title);
        if(!m.genres.matches(""))
            valueList.add("Genres \n"       + m.genres);
        if(!m.overview.matches(""))
            valueList.add("Overview \n"     + m.overview);
        if(!m.homepage.matches(""))
            valueList.add("Homepage \n"     + m.homepage);
        if(!m.runtime.matches(""))
            valueList.add("Runtime \n"      + m.runtime + " Minutes");
        if(!m.releaseDate.matches(""))
            valueList.add("Release Date \n" + m.releaseDate);
        if(!m.population.matches(""))
            valueList.add("Population \n"   + m.population);
        if(!m.voteAverage.matches(""))
            valueList.add("Vote \n"         + m.voteAverage + " / 10");
        if(!m.voteCount.matches(""))
            valueList.add("Vote Count \n"   + m.voteCount);
        if(!m.originalLanguage.matches(""))
            valueList.add("Original Language \n"    + m.originalLanguage);
        if(!m.originalTitle.matches(""))
            valueList.add("Original Title \n"       + m.originalTitle);

        // Show list
        lv.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, valueList));


    }

}
