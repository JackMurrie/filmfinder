package com.filmfinder.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReviewTest {
    @Test
    public void testStandard() {
        System.out.println("Review DB test");
        String email = "testing@somthingrandom.test";
        String first = "Dave";
        String last = "Test";
        int hash = 3;

        // Following movie is hardcoded
        int movieID = 2;

        try {
            // Setting up DB with user
            if (AuthDB.checkEmail(email)) {
                // assertEquals(AuthDB.deleteUser(email), 1);
            } else {
                AuthDB.putCredentials(first, last, email, hash);
            }

            ReviewDB.putReview(email, movieID, "Test review", (float)-1);

            assertEquals(ReviewDB.exists(email, movieID), true);

            assertEquals(ReviewDB.removeReview(email, movieID), 1);

            assertEquals(ReviewDB.removeReview(email, movieID), 0);
            
            assertEquals(ReviewDB.exists(email, movieID), false);

            ReviewDB.putReview(email, movieID, "Test review 2", (float)-1);
            ReviewDB.putReview(email, movieID, "Test review 3", (float)-1);

            // Resetting DB
            // assertEquals(AuthDB.deleteUser(email), 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}
