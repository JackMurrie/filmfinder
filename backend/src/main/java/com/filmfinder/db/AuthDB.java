package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.filmfinder.user.User;

import javassist.NotFoundException;


public class AuthDB {
    /**
     * 
     * @param firstName
     * @param lastName
     * @param email Assumes this email is not already in the database call checkEmail to see
     * @param hashedPassword
     * @throws SQLException if anything goes wrong in insertion
     */
    public static void putCredentials(String firstName, String lastName, String email, int hashedPassword) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        String profile_url = "https://i.pinimg.com/236x/d6/7f/cb/d67fcb293e7ab5d6fdd92cb9bc639b3b.jpg";
        try {
            c = DbDataSource.getConnection();
            String q = "INSERT INTO film_finder.user (first_name, last_name, email, hash, profile_image_url) VALUES (?, ?, ?, ?, ?);";
            s = c.prepareStatement(q);
            s.setString(1, firstName);
            s.setString(2, lastName);
            s.setString(3, email);
            s.setInt(4, hashedPassword);
            s.setString(5, profile_url);

            s.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            try {
                if (c != null) c.close();
                if (s != null) s.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return;
    }

    // //Not sure what this if for
    // public static String getEmail(String handle) {
    //     return "user@gmail.com";
    // }

    /**
     * 
     * @param email of user
     * @return the password hash identified by email
     * @throws NotFoundException if the requested password is not available in the database,
     * @throws SQLException if there's an error with the database
     */
    public static int getHashedPassword(String email) throws NotFoundException, SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT hash FROM user WHERE email=?";
            s = c.prepareStatement(q);
            s.setString(1, email);

            rs = s.executeQuery();
            
            if (!rs.next()) {
                throw new NotFoundException("Email doesn't exist in database");
            };
            return rs.getInt("hash");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            try {
                if (c != null) c.close();
                if (s != null) s.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }
    }

    /**
     * 
     * @param email
     * @return true if email already in DB, false otherwise
     * @throws SQLException if anything goes wrong on DB insertion
     */
    public static boolean checkEmail(String email) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT COUNT(*) AS num FROM user WHERE email=?";
            s = c.prepareStatement(q);
            s.setString(1, email);

            rs = s.executeQuery();
            
            rs.next();
            int num = rs.getInt("num");
            if (num > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            try {
                if (c != null) c.close();
                if (s != null) s.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }
    }

    /**
     * 
     * @param email the email of user to remove
     * @return the number of entries deleted
     * @throws SQLException if an SQL error occurs
     */
    public static int deleteUser(String email) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = DbDataSource.getConnection();
            String q = "DELETE FROM user WHERE email=?";
            s = c.prepareStatement(q);
            s.setString(1, email);

            return s.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally{
            try {
                if (c != null) c.close();
                if (s != null) s.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static User getUser(int userId) throws NotFoundException, SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT first_name first, last_name last, email, profile_image_url url FROM user WHERE id=?";
            s = c.prepareStatement(q);
            s.setInt(1, userId);

            rs = s.executeQuery();
            
            if (!rs.next()) {
                throw new NotFoundException("Email doesn't exist in database");
            };
            return new User(userId, rs.getString("first"), rs.getString("last"), rs.getString("email"), rs.getString("url"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            try {
                if (c != null) c.close();
                if (s != null) s.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }
    }
}
