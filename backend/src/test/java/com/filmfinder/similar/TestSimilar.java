package com.filmfinder.similar;

import java.sql.SQLException;

import com.filmfinder.movie.Movies;

import org.junit.Test;

import javassist.NotFoundException;

public class TestSimilar {
    @Test
    public void testSimilar() {
        try {
            Movies m = SimilarMovies.getSimilarMovies(3, 10);
            System.out.println(m);
        } catch (NotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
