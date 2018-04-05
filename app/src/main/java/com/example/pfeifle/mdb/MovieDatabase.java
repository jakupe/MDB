package com.example.pfeifle.mdb;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;


/**
 * Created by Foecking on 27.03.2018.
 */

@Database(entities = {Movie.class}, version = 2)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'movie' ADD COLUMN 'isSaved' TEXT");
        }
    };
}
