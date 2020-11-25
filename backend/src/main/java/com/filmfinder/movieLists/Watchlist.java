package com.filmfinder.movieLists;

import java.sql.SQLException;

import com.filmfinder.db.WatchlistDB;
import com.google.gson.annotations.JsonAdapter;

import javassist.NotFoundException;

@JsonAdapter(MovieListSerializer.class)
public class Watchlist extends MovieList {
    
    public Watchlist(int userId) throws NotFoundException, SQLException {
        dbUtil = new WatchlistDB(userId);
        movies = dbUtil.getMovies();
    }

}
