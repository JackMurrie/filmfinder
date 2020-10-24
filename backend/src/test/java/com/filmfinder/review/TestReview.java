package com.filmfinder.review;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import com.filmfinder.db.ReviewDB;

import org.junit.Test;

public class TestReview {
    @Test
    public void testReview() {
        try {
            ArrayList<Review> reviews = ReviewDB.getReviews(2);
            System.out.println(reviews);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
}
