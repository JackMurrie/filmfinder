package com.filmfinder.review;

import static org.junit.Assert.assertTrue;

import com.filmfinder.db.ReviewDB;

import org.junit.Test;

public class TestReview {
    @Test
    public void testReview() {
        try {
            Reviews reviews = ReviewDB.getReviewsByMovieId(2);
            System.out.println(reviews);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testInvalidMovieId() {
        try {
            Reviews reviews = ReviewDB.getReviewsByMovieId(1);
            System.out.println(reviews);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
}
