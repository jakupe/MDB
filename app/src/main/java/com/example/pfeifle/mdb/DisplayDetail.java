package com.example.pfeifle.mdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by Pfeifle on 24.03.2018.
 */

public class DisplayDetail extends AppCompatActivity {
    private TextView name, runtime, originalName, language, year, vote, count, population, genre, overview;
    private Button saveBtn;
    private String extra = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // info aus intentaufruf fuer fall unterscheidung
        Intent intent = getIntent();
        extra = intent.getStringExtra("extraData");

        // TextViews
        name        = findViewById(R.id.name);
        runtime     = findViewById(R.id.runtime);
        originalName= findViewById(R.id.originalName);
        language    = findViewById(R.id.language);
        year        = findViewById(R.id.year);
        vote        = findViewById(R.id.vote);
        count       = findViewById(R.id.count);
        population  = findViewById(R.id.population);
        genre       = findViewById(R.id.genre);
        overview    = findViewById(R.id.overview);
        overview.setMovementMethod(new ScrollingMovementMethod());

        saveBtn = findViewById(R.id.addToWatchList);
        //wenn displaydetail aus watchlist aufgerufen dann btn text aendern
        if(extra.equals("watchlist"))
            saveBtn.setText(getString(R.string.deletemovie_btn));


        // Add Onclick Listener for save btn
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //wenn displaydetail aus watchlist aufgerufen dann deletemovie aufruf und aktivity beenden
                if(extra.equals("watchlist")){
                    new DatabaseInitializer().deleteMovie(Buffer.getMovie().getId(), Watchlist.getRefreshDisplay());
                    finish();
                //ansonsten addmovie befehl ausfuehren
                } else {
                    new DatabaseInitializer().addMovie();
                }
            }
        });
        display();

    }

    protected void display() {
        // get Movie Data
        Movie m = Buffer.getMovie();

        // Set attributes
        if (!m.getTitle().matches(""))
            name.setText(m.getTitle());
        else name.setText("???");

        if (!m.getRuntime().matches("0"))
            runtime.setText(m.getRuntime() + " Min.");
        else runtime.setText("???");

        if (!m.getOriginalTitle().matches(""))
            originalName.setText(m.getOriginalTitle());
        else originalName.setText("???");

        if (!m.getOriginalLanguage().matches(""))
            language.setText(m.getOriginalLanguage());
        else language.setText("???");

        if (!m.getReleaseDate().matches(""))
            year.setText(m.getReleaseDate());
        else year.setText("???");

        if (!m.getVoteAverage().matches("0.0"))
            vote.setText(m.getVoteAverage());
        else vote.setText("???");

        if (!m.getVoteCount().matches("0"))
            count.setText(m.getVoteCount());
        else count.setText("???");

        if (!m.getPopulation().matches("0.0"))
            population.setText(m.getPopulation());
        else population.setText("???");

        if (!m.getGenres().matches(""))
            genre.setText(m.getGenres());
        else genre.setText("???");

        if (!m.getOverview().matches(""))
            overview.setText(m.getOverview());
        else overview.setText("???");

    }

}
