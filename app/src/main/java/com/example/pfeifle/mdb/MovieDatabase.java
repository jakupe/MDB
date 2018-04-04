package com.example.pfeifle.mdb;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

/**
 * Created by Foecking on 27.03.2018.
 */

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
/*
@Database(entities = {Movie.class}, version = 1)
//@TypeConverters({DateTypeConverter.class})
public abstract class MovieDatabase extends RoomDatabase {

    //private static MovieDatabase INSTANCE;

    public abstract MovieDao movieDao();

    /*public static MovieDatabase getMovieDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "movie-database").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {


            // enable flag to force update products
            //Main.get().setForceUpdate(true);
        }
    };



}

*/