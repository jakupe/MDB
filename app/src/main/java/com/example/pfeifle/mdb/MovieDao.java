package com.example.pfeifle.mdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Foecking on 27.03.2018.
 */
//db access interface
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("SELECT * FROM movie where title LIKE  :title")
    Movie findByName(String title);

    @Query("SELECT * FROM movie where id LIKE :id")
    Movie findById(int id);

    @Query("SELECT COUNT(*) from movie")
    int countMovies();

    @Query("DELETE FROM movie")
    void deleteAll();

    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

}
