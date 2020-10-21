package com.filmfinder.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

public class AuthTest {
    // @Test
    public void testStandard() {
        System.out.println("Running Auth Standard test");
        String email = "testing@somthingrandom.test";
        String first = "Dave";
        String last = "Test";
        int hash = 3;
        try {
            if (AuthDB.checkEmail(email)) {
                assertEquals(AuthDB.deleteUser(email), 1);
            }
            AuthDB.putCredentials(first, last, email, hash);
            assertTrue(AuthDB.checkEmail(email));
            assertEquals(AuthDB.getHashedPassword(email), 3);
            assertEquals(AuthDB.deleteUser(email), 1);
            assertEquals(AuthDB.checkEmail(email), false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
    
    // @Test
    public void testDoubleInsert() {
        System.out.println("Running Auth double test");
        String email = "testing@somthingrandom.test";
        String first = "Dave";
        String last = "Test";
        int hash = 3;

        try {
            if (AuthDB.checkEmail(email)) {
                assertEquals(AuthDB.deleteUser(email), 1);
            }
            AuthDB.putCredentials(first, last, email, hash);
            try {
                AuthDB.putCredentials(first, last, email, hash);
                assertTrue(false);
            } catch (SQLException e) {
            }
            assertEquals(AuthDB.deleteUser(email), 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    // @Test
    public void testDelete() {
        System.out.println("Running Auth Delete test");
        String email = "testing@somthingrandom.test";
        String email2 = "testing2@somthingrandom.test";
        String first = "Dave";
        String last = "Test";
        int hash = 3;

        try {
            if (AuthDB.checkEmail(email)) {
                assertEquals(AuthDB.deleteUser(email), 1);
            }
            if (AuthDB.checkEmail(email2)) {
                assertEquals(AuthDB.deleteUser(email2), 1);
            }
            AuthDB.putCredentials(first, last, email, hash);
            AuthDB.putCredentials(first, last, email2, hash);

            assertEquals(AuthDB.deleteUser(email), 1);
            assertEquals(AuthDB.deleteUser(email), 0);

            assertEquals(AuthDB.checkEmail(email), false);
            assertTrue(AuthDB.checkEmail(email2));

            assertEquals(AuthDB.deleteUser(email2), 1);
            assertEquals(AuthDB.checkEmail(email2), false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void testAuth() {
        assertEquals(true, true);
    }
}
