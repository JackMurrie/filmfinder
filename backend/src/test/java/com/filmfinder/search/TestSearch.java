package com.filmfinder.search;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.Search.Search;
import com.filmfinder.db.SearchDB;
import com.filmfinder.movie.Movies;

import org.junit.Test;

import javassist.NotFoundException;

public class TestSearch {
    // @Test
    public void testSearch() {
        Movies ms;
        try {
            ms = Search.getMovieIdsBySearch("Daddy's home 2", 10);
            System.out.println(ms);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testGenreDirectors() {
        Movies ms;
        try {
            // ms = Search.searchDirectors("Quentin Tarantino", 10);
            // System.out.println(ms);
            ms = Search.searchGenre("Romance", 10);
            System.out.println();
            System.out.println(ms);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
