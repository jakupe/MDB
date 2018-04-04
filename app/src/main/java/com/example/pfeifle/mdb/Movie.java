package com.example.pfeifle.mdb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Pfeifle on 19.03.2018.
 */
@Entity(tableName = "movie")
class Movie {

    @PrimaryKey(autoGenerate = false)
    @NonNull private String id;


    //@NonNull private long dbid;

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

    public Movie(@NonNull String id, String title) {
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

    @NonNull
    public String getId() {
        return id;
    }

    /*@NonNull
    public long getDbid() {
        return dbid;
    }*/

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

    public void setId(@NonNull String id) {
        this.id = id;
    }

    /*public void setDbid(@NonNull long dbid) {
        this.dbid = dbid;
    }*/

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }
}
