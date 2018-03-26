package com.example.pfeifle.mdb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pfeifle on 19.03.2018.
 */

class Movie {
    protected String id, title, genres="", homepage="", originalLanguage="", originalTitle="", overview="",
            population="", releaseDate="", runtime="", voteAverage="", voteCount="";

    public Movie(String id, String title) {
            this.id = id;
            this.title = title;
    }

    protected void createDetails(JSONObject jo) {
        try {
            homepage            = jo.getString("homepage");
            originalLanguage    = jo.getString("original_language");
            originalTitle       = jo.getString("original_title");
            overview            = jo.getString("overview");
            population          = jo.getString("popularity");
            releaseDate         = jo.getString("release_date");
            runtime             = jo.getString("runtime");
            voteAverage         = jo.getString("vote_average");
            voteCount           = jo.getString("vote_count");

            // Get genres
            String gen = jo.getString("genres");
            gen = gen.substring(1);
            gen = gen.substring(0, gen.length() - 1);
            jo = new JSONObject("{\"gen\":["+gen+"]}");

            // extracting genres from data
            JSONArray ja = jo.getJSONArray("gen");

            // loop to split and get genres
            for(int i= 0; i<ja.length(); i++)
                genres += new JSONObject(ja.getString(i)).getString("name") + ", ";

            genres = genres.substring(0, genres.length() - 2);
        } catch (JSONException e) { e.printStackTrace(); }

    }

}
