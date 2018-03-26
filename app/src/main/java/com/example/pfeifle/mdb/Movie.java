package com.example.pfeifle.mdb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Pfeifle on 19.03.2018.
 */
@Entity(tableName = "movie")
class Movie {
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "genres")
    private String genres = "";

    @ColumnInfo(name = "homepage")
    private String homepage = "";

    @ColumnInfo(name = "originalLanguage")
    private String originalLanguage = "";

    @ColumnInfo(name = "originalTitle")
    private String originalTitle = "";

    @ColumnInfo(name = "overview")
    private String overview = "";

    @ColumnInfo(name = "population")
    private String population = "";

    @ColumnInfo(name = "releaseDate")
    private String releaseDate = "";

    @ColumnInfo(name = "runtime")
    private String runtime = "";

    @ColumnInfo(name = "voteAverage")
    private String voteAverage = "";

    @ColumnInfo(name = "voteCount")
    private String voteCount = "";

    public Movie(String id, String title) {
            this.id = id;
            this.title = title;
    }

    protected void createDetails(JSONObject jo) {
        try {
            homepage = jo.getString("homepage");
            originalLanguage = jo.getString("original_language");
            originalTitle = jo.getString("original_title");
            overview = jo.getString("overview");
            population = jo.getString("popularity");
            releaseDate = jo.getString("release_date");
            runtime = jo.getString("runtime");
            voteAverage = jo.getString("vote_average");
            voteCount = jo.getString("vote_count");

            // Get genres
            String gen = jo.getString("genres");
            gen = gen.substring(1);
            gen = gen.substring(0, gen.length() - 1);
            jo = new JSONObject("{\"gen\":[" + gen + "]}");

            // extracting genres from data
            JSONArray ja = jo.getJSONArray("gen");

            // loop to split and get genres
            for (int i = 0; i < ja.length(); i++)
                genres += new JSONObject(ja.getString(i)).getString("name") + ", ";

            genres = genres.substring(0, genres.length() - 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPopulation() {
        return population;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }
}
