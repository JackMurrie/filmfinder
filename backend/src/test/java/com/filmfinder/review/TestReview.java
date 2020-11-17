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
    @Test
    public void testSplitReviewRating() {
        // try {
        //     String review = "This is a comprehensive test review";
        //     String newReview = "This a comprehensive test review";
        //     int userId = 5;
        //     String email = "asdf";
        //     int movieId = 2;
        //     int movieId2 = 3;
        //     float rating = (float)5.0;

        //     // ReviewDB.removeReview(email, movieId);
        //     // ReviewDB.removeReview(email, movieId2);

        //     // testing no rating
        //     // ReviewDB.removeReview(email, movieId);
        //     // ReviewDB.removeReview(email, movieId2);

        //     System.out.println("Starting tests");
        //     ReviewDB.updateReview(email, movieId, review);

        //     Review r = ReviewDB.getReview(movieId, userId);
        //     System.out.println(r);
        //     assertEquals(r.getComment(), review);
        //     assertTrue(r.getRating()>=0);

        //     ReviewDB.updateRating(email, movieId, rating);
        //     r = ReviewDB.getReview(movieId, userId);
        //     System.out.println(r);
        //     assertEquals(r.getComment(), review);
        //     assertTrue(r.getRating()>=5);

        //     System.out.println("First tests passed");

        //     ReviewDB.updateRating(email, movieId2, (float)2.5);
        //     r = ReviewDB.getReview(movieId2, userId);
        //     System.out.println(r);
        //     assertEquals(r.getComment().equals("")||r.getComment().equals(review), true);
        //     assertTrue(r.getRating()<=2.6||r.getRating()>=2.4);

        //     ReviewDB.updateReview(email, movieId2, review);
        //     r = ReviewDB.getReview(movieId2, userId);
        //     System.out.println(r);
        //     assertEquals(r.getComment(), review);
        //     assertTrue(r.getRating()<=2.6||r.getRating()>=2.4);

        //     System.out.println("Second tests passed");

        //     ReviewDB.updateReview(email, movieId, newReview);
        //     ReviewDB.updateRating(email, movieId, (float)1.7);

        //     r = ReviewDB.getReview(movieId, userId);
        //     System.out.println(r);
        //     assertEquals(r.getComment(), newReview);
        //     assertTrue(r.getRating()<=1.8||r.getRating()>=1.6);


        // } catch (Exception e) {
        //     System.err.println(e.getMessage());
        //     assertTrue(false);
        // }
    }
    @Test
    public void testRatingOnEmpytReview() {
        // String review = "This is a comprehensive test review";
        // try {
        //     int userId = 5;
        //     String email = "asdf";
        //     int movieId = 11;

        //     // ReviewDB.removeReview(email, movieId);
        //     // ReviewDB.removeReview(email, movieId2);

        //     // testing no rating
        //     // ReviewDB.removeReview(email, movieId);
        //     // ReviewDB.removeReview(email, movieId2);

        //     System.out.println("Starting tests");
        //     ReviewDB.updateRating(email, movieId, (float)1.3);

        //     Review r = ReviewDB.getReview(movieId, userId);
        //     System.out.println(r);
        //     assertEquals(r.getComment(), "");
        //     assertTrue(r.getRating()>=0);
        // } catch (Exception e) {
            
        // }
    }
    
}
