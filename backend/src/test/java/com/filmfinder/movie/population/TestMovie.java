package com.filmfinder.movie.population;

import static org.junit.Assert.assertTrue;

import com.filmfinder.movie.Movie;

import org.junit.Test;

public class TestMovie {
    @Test
    public void testGetMovie() {
        try {
            Movie m = Movie.getMovie(2);
            System.out.println(m);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
