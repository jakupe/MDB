package com.example.pfeifle.mdb;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pfeifle on 24.03.2018.
 */

public class DisplayDetail extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        lv = (ListView) findViewById(R.id.detailView);

    }

    protected void display(Movie m, JSONObject jo, Context appCon) {
        m.createDetails(jo);

        List valueList = new ArrayList<String>();
        Log.i("MDB", "title= "+m.title);
        Log.i("MDB", "genres= "+m.genres);
        Log.i("MDB", "overview= "+m.overview);

        valueList.add("Title \n"        + m.title);
        valueList.add("Genres \n"       + m.genres);
        //valueList.add("Overview \n"     + m.overview);
        //valueList.add("Homepage \n"     + m.homepage);
        //valueList.add("Runtime \n"      + m.runtime);
        //valueList.add("Release Date \n" + m.releaseDate);
        //valueList.add("Population \n"   + m.population);
        //valueList.add("Vote \n"         + m.voteAverage);
        //valueList.add("Vote Count \n"   + m.voteCount);
        //valueList.add("Original Language \n"    + m.originalLanguage);
        //valueList.add("Original Title \n"       + m.originalTitle);

        // Show list
        Log.i("MDB", "appC: \n"+appCon);
        Log.i("MDB", "appC: \n"+lv);
        //lv.setAdapter(new ArrayAdapter(appCon, android.R.layout.simple_list_item_1, valueList));


    }

}
