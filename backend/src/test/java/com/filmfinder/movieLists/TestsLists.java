package com.filmfinder.movieLists;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import javassist.NotFoundException;

public class TestsLists {
    @Test
    public void testWishlist() {
        try {
            Wishlist w = new Wishlist(1);

            System.out.println(w);
        } catch (SQLException e) {
            System.out.println("SQL Exception:");
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (NotFoundException e) {
            System.out.println("Not found exception:");
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}
