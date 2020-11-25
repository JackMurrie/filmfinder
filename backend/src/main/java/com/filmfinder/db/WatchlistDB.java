package com.filmfinder.db;

import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.movie.Movies;

import javassist.NotFoundException;

public class WatchlistDB extends MovieListDB {
    private String list = "watched";
    private int userId;

    public WatchlistDB(int userId) {
        this.userId = userId;
    }

    @Override
    public void addToList(int movieId) throws SQLException {
        addToList(list, userId, movieId);
    }

    @Override
    public int deleteFromList(int movieId) throws SQLException {
        return deleteFromList(list, userId, movieId);
    }

    @Override
    public ArrayList<Integer> getMovieList() throws SQLException {
        return getMovieList(list, userId);
    }

    @Override
    public Movies getMovies() throws NotFoundException, SQLException {
        return getMovies(list, userId);
    }

}
