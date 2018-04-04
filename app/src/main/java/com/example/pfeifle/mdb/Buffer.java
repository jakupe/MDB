package com.example.pfeifle.mdb;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Pfeifle on 26.03.2018.
 */

public class Buffer {
    private static Movie m;
    private static List<Movie> ml;

    protected Buffer(Movie movie, JSONObject jo) {
        if (jo != null)
            movie.createDetails(jo);
        m = movie;
    }

    protected Buffer(List<Movie> ml) {
        this.ml = ml;
    }

    protected static Movie getMovie() {
        return m;
    }

    protected static List<Movie> getMovieList() { return ml;}

}
