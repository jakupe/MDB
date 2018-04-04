package com.example.pfeifle.mdb;


import android.os.AsyncTask;
import android.util.Log;
import java.util.List;


/**
 * Created by Foecking on 28.03.2018.
 */

public class DatabaseInitializer  {

    private PopulateDbAsync task;
    private MovieDatabase mDb = null;
    private DbAccess dba = null;
    private Watchlist wl = null;
    private RefreshDisplay rd;
    private String input = null;


    public DatabaseInitializer() {
        finDba();
        task = new PopulateDbAsync(dba);
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
        if (input == "deleteAll")
            rd.refresh();

        //Intent i = new Intent(new Intent(Main.getMainContext(), Watchlist.class));
        //Main.getMainContext().startActivity(i);
    }

    protected void addMovie() {
        task.execute("push");
    }

    protected void deleteMovie(String id) { task.execute("delete", id); }

    protected void deleteAllMovies(RefreshDisplay rd) {
        this.rd = rd;
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
            MovieDatabase db = Main.mdb;

            input = params[0];
            int id=0;
            if(params.length > 1) {
                String sid = params[1];
                id = Integer.parseInt(sid);
            }

            switch(input) {
                case "push":
                    Log.i("DB", "PUSH");
                    mlist = db.movieDao().getAll();
                    m = Buffer.getMovie();
                    boolean same = false;
                    for(int j=0; j<mlist.size(); j++)
                        if (m.getId().equals(mlist.get(j).getId())) {
                            same = true;
                            break;
                        }
                    if(!same)
                        db.movieDao().insert(m);
                    break;

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


            if (db.movieDao().getAll()==null){
                mlist.add(new Movie("0", "Kein Film vorhanden"));
            } else{
                mlist = db.movieDao().getAll();
            }
            return mlist;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            dba.finish(movies);

        }

    }


}
