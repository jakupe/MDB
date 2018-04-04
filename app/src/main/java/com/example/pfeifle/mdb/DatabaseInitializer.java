package com.example.pfeifle.mdb;


import android.os.AsyncTask;
import android.util.Log;

import java.util.List;


/**
 * Created by Foecking on 28.03.2018.
 */

public class DatabaseInitializer {

    private PopulateDbAsync task;
    private DbAccess dba = null;

    public DatabaseInitializer() {
        finDba();
        task = new PopulateDbAsync(dba);
    }

    private void finDba() {
        dba = new DbAccess() {
            @Override
            //wird nach asynctask augefuehrt
            public void finish(List<Movie> movies) {
                finishDbAccess(movies);
            }
        };
    }

    //wird ausgefuehrt wenn asynctask fertig
    protected void finishDbAccess(List<Movie> movieList) {
        new Buffer(movieList);
        Watchlist.ready = true;
        //Intent i = new Intent(new Intent(Main.getMainContext(), Watchlist.class));
        //Main.getMainContext().startActivity(i);
    }

    protected void addMovie() {
        task.execute("push");
    }

    protected void deleteMovie(String id) {
        task.execute("delete", id);
    }

    protected void deleteAllMovies() {
        task.execute("deleteAll");
    }

    protected void fillBuffer() {
        task.execute("");
    }

    private class PopulateDbAsync extends AsyncTask<String, Void, List<Movie>> {
        public DbAccess dba;
        private Movie m = null;
        private List<Movie> mlist;

        public PopulateDbAsync(DbAccess dba) {
            this.dba = dba;
        }

        @Override
        protected List<Movie> doInBackground(final String... params) {
            //init db
            MovieDatabase db = Main.mdb;

            //parameter auslesen
            String input = params[0];
            int id = 0;
            if (params.length > 1) {
                String sid = params[1];
                id = Integer.parseInt(sid);
            }

            //switch case um aktion zuzuordnen
            switch (input) {
                //daten in db pushen
                case "push":
                    Log.i("DB", "PUSH");
                    mlist = db.movieDao().getAll();
                    //movie aus buffer
                    m = Buffer.getMovie();
                    boolean same = false;
                    for (int j = 0; j < mlist.size(); j++)
                        //wenn film schon in db dann break
                        if (m.getId().equals(mlist.get(j).getId())) {
                            same = true;
                            break;
                        }
                    //wenn film nicht in db dann zu db hinzufuegen
                    if (!same)
                        db.movieDao().insert(m);
                    break;

                //einzelnen film anhand id loeschen
                case "delete":
                    Log.i("DB", "DELETE");
                    m = db.movieDao().findById(id);
                    db.movieDao().delete(m);
                    break;

                case "deleteAll":
                    Log.i("DB", "DELETE ALL");
                    db.movieDao().deleteAll();
                    break;

                default:
            }

            //wenn db leer dann dummy movie einfuegen
            if (db.movieDao().getAll() == null) {
                mlist.add(new Movie("0", "Kein Film vorhanden"));
            } else {
                mlist = db.movieDao().getAll();
            }
            //return movielist aus db und uebergebe zu onpostexecute
            return mlist;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            //movielist der finish methode uebergeben
            dba.finish(movies);
        }
    }
}
