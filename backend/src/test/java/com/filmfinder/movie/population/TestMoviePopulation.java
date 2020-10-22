package com.filmfinder.movie.population;

import static org.junit.Assert.assertTrue;

import com.filmfinder.util.UrlConnector;
import com.google.gson.Gson;

import org.junit.Test;

public class TestMoviePopulation {
    @Test
    public void testGetPopMovieData() {
        System.out.println("Testing get pop movie data");
        
        try {
            String json = UrlConnector.readUrl("https://api.themoviedb.org/3/movie/3?api_key=70ae629f88a806e8758ac3900483833e&append_to_response=credits");
            PopMovieData target = new Gson().fromJson(json, PopMovieData.class);

            System.out.println(target.toString());
        } catch (Exception e) {
            assertTrue(false);
        }
        assertTrue(true);
    }
    
}
