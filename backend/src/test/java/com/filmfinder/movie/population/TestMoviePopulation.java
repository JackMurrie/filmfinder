package com.filmfinder.movie.population;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestMoviePopulation {
    @Test
    public void testGetPopMovieData() {
        System.out.println("Testing get pop movie data");
        
        try {
            PopMovieData target = PopMovieData.getPopMovieData(105800);

            System.out.println(target.toString());
        } catch (Exception e) {
            assertTrue(false);
        }
        assertTrue(true);
    }
    // @Test
    public void populateDB() {
        Populate.populateGenreDirector();
        assertTrue(true);
    }

}
