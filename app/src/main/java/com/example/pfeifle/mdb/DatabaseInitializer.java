package com.example.pfeifle.mdb;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Foecking on 28.03.2018.
 */

public class DatabaseInitializer  {

    private PopulateDbAsync task = null;
    private MovieDatabase mDb = null;
    private DbAccess dba = null;

    //protected static MovieDatabase database;

    public static DatabaseInitializer INSTANCE;
    private static final String DATABASE_NAME = "MovieDatabase";
   /* private static final String PREFERENCES = "RoomDemo.preferences";
    private static final String KEY_FORCE_UPDATE = "force_update";*/




    public DatabaseInitializer(/*MovieDatabase db*/) {
        finDba();
        task = new PopulateDbAsync(/*db,*/ dba);
    }

    private void finDba() {
        dba = new DbAccess() {
            @Override
            public void finish(List<Movie> movies) {
                finishDbAccess(movies);
            }
        };
    }

    protected void finishDbAccess(List<Movie> movieList) {
        new Buffer(movieList);
        //Context c = new Main().getMainContext();
        Intent i = new Intent(new Intent(Main.getMainContext(), Watchlist.class));
        Main.getMainContext().startActivity(i);
    }

    protected void addMovie()   {
        task.execute("push");
    }

    protected void deleteMovie() { task.execute("delete"); }

    protected void deleteAllMovies()    {
        task.execute("deleteAll");
    }



    private class PopulateDbAsync extends AsyncTask<String, Void, List<Movie>> {
        public DbAccess dba = null;
        private List<Movie> movies = null;
        private Movie m = null;

        public PopulateDbAsync(/*MovieDatabase db,*/ DbAccess dba) {
            //mDb = db;
            this.dba = dba;
        }

        @Override
        protected List<Movie> doInBackground(final String... params) {
            MovieDatabase db = Main.mdb;

            String input = params[0];
            int id=0;
            if(params.length>1) {
                String sid = params[1];
                id = Integer.parseInt(sid);
            }

            switch(input) {
                case "push":
List<Movie> mlist = db.movieDao().getAll();
                    m = Buffer.getMovie();
                    boolean same = false;
                    for(int i=0; i<mlist.size(); i++) {

                            if (m.getId().equals(mlist.get(i).getId())) {
                                same = true;
                                break;
                            }

                    }


                    if(same=false) {
                        db.movieDao().insert(m);
                    }
                    break;
                case "delete":
                    m = mDb.movieDao().findById(id);
                    db.movieDao().delete(m);
                    break;
                case "deleteAll":
                    db.movieDao().deleteAll();
                    break;
                default:
            }
            movies = db.movieDao().getAll();
            /*if(movies.size()==0)    {
                movies.add(new Movie("0", "kein Film vorhanden"));
            }*/
            //Log.i("First movie name", movies.get(0).getTitle());
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            dba.finish(movies);
        }

    }
/*
    public MovieDatabase getDB() {
        return database;
    }

    public boolean isForceUpdate() {
        return getSP().getBoolean(KEY_FORCE_UPDATE, true);
    }

    public void setForceUpdate(boolean force) {
        SharedPreferences.Editor edit = getSP().edit();
        edit.putBoolean(KEY_FORCE_UPDATE, force);
        edit.apply();
    }

    private SharedPreferences getSP() {
        return getSharedPreferences(PREFERENCES, MODE_PRIVATE);
    }
*/

}
