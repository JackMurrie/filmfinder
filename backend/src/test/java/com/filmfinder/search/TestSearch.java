package com.filmfinder.search;

import java.sql.SQLException;

import com.filmfinder.Search.Search;
import com.filmfinder.movie.Movies;

import org.junit.Test;

import javassist.NotFoundException;

public class TestSearch {
    @Test
    public void testSearch() {
        Movies ms;
        try {
            ms = Search.getMovieIdsBySearch("Ariel", 3);
            System.out.println(ms);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
