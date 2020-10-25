package com.filmfinder.movie;

import static org.junit.Assert.assertTrue;

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
