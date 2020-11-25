package com.filmfinder.db;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import javassist.NotFoundException;

public class ListsTest {
    @Test
    public void testGetMovieList() {
        try {
            WishlistDB w = new WishlistDB(1);

            System.out.println(w.getMovieList());
        } catch (SQLException e) {
            System.out.println("SQL Exception:");
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
    @Test
    public void testGetMovies() {
        try {
            WishlistDB w = new WishlistDB(1);
            System.out.println(w.getMovies());

        } catch (SQLException e) {
            System.out.println("SQL Exception:");
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (NotFoundException e) {
            System.out.println("Not found exception:");
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (Exception e) {
            System.out.println("Other exception");
            assertTrue(false);
        }
    }
}
