package com.filmfinder.movieLists;

import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.db.WatchlistDB;
import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.movie.Movies;
import com.google.gson.annotations.Expose;

import javassist.NotFoundException;

public class Watchlist extends frontendObject {
    private WatchlistDB dbUtil;
    @Expose
    private Movies movies;

    public Watchlist(int userId) throws NotFoundException, SQLException {
        dbUtil = new WatchlistDB(userId);
        movies = dbUtil.getMovies();
    }

    public void addMovie(int movieId) throws NotFoundException, SQLException {
        dbUtil.addToList(movieId);
        movies = dbUtil.getMovies();
    }

    public void deleteMovie(int movieId) throws SQLException {
        dbUtil.deleteFromList(movieId);
        try {
            movies = dbUtil.getMovies();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    } 

    public ArrayList<Integer> getMovieList() {
        return getMovieList();
    }

    public Movies getMovies() {
        return movies;
    }

}
