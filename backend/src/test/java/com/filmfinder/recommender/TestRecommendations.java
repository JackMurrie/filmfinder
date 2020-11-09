package com.filmfinder.recommender;

import java.sql.SQLException;

import com.filmfinder.movie.Movies;

import org.junit.Test;

import javassist.NotFoundException;

public class TestRecommendations {
    @Test
    public void testRec() {
        try {
            Movies m = Recommender.getRecommendedMovies(2, 10);
            System.out.println(m);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

    }
}
