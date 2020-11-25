package com.filmfinder.db;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import com.filmfinder.movie.Movie;
import com.filmfinder.movie.Movies;

import org.junit.Test;

import javassist.NotFoundException;

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

    @Test
    public void getMovie() {
        try {
            Movie m = Movie.getMovie(2);
            System.out.println(m);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testMovies() {
        Movies movies = new Movies();
        try {
            movies.add(Movie.getMovie(2));
            System.out.println(movies);
        } catch (NotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Other exception");
            assertTrue(false);
        }
    }
}
