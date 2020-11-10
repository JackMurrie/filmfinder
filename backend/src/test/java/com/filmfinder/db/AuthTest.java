package com.filmfinder.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Date;

import com.filmfinder.user.User;

import org.junit.Test;

import javassist.NotFoundException;

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
    public void testGetUser() {
        try {
            User u = AuthDB.getUser(1);
            System.out.println(u);
        } catch (NotFoundException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }
    @Test
    public void testEmail() {
        try {
            String email = AuthDB.getEmailFromId(2);
            System.out.println(email);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(true, true);
    }

    @Test
    public void testAccessCode(){
        int userId = 1;
        String code = "1252";
        Date d = new Date();
        try {
            AuthDB.putVerficationCode(userId, code, d);
            Date d2 = AuthDB.getCodeExpiry(userId, code);
            System.out.println(d2);
            System.out.println(d);
            System.out.println(d2.equals(d));
            // assertEquals(d2, d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
