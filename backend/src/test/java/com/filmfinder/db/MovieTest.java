package com.filmfinder.db;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MovieTest {
    @Test
    public void testGetMovies() {
        try {
            System.out.println(MovieDb.getMoviesNoDirector());

        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetGenres() {
        try {
            System.out.println(MovieDb.getGenres(2));

        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetDirectors() {
        try {
            System.out.println(MovieDb.getDirectors(2));

        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    @Test
    public void testCheckMovies() {
        try {
            assertTrue(MovieDb.checkMovie(2));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

}
