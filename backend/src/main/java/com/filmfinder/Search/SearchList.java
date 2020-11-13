package com.filmfinder.Search;

import java.sql.SQLException;

import com.filmfinder.movie.Movies;

import javassist.NotFoundException;

public class SearchList {
    String query;
    Movies results;

    public SearchList(String query) throws NotFoundException, SQLException {
        this.query = query;
        this.results = Search.getMovieIdsBySearch(query, 10);
    }
    public SearchList(String query, int limit) throws NotFoundException, SQLException {
        this.query = query;
        this.results = Search.getMovieIdsBySearch(query, limit);
    }
}
