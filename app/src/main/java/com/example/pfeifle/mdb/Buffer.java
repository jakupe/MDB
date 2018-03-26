package com.example.pfeifle.mdb;

import org.json.JSONObject;

/**
 * Created by Pfeifle on 26.03.2018.
 */

public class Buffer {
    private static Movie m;

    protected Buffer(Movie movie, JSONObject jo) {
        movie.createDetails(jo);
        m = movie;
    }

    protected static Movie getMovie() {
        return m;
    }


}
