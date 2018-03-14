package com.example.pfeifle.mdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    EditText movieName;
    Button searchBtn;

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
        Toast.makeText(getApplicationContext(), movieName.getText() , Toast.LENGTH_SHORT).show();
        
    }


}
