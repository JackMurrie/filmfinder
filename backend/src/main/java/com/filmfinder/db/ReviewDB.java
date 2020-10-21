package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javassist.NotFoundException;


public class ReviewDB {

    /**
     * 
     * @param email the users email
     * @param movieId the movie id
     * @param comment empty string if no comment to insert i.e. ""
     * @param star negative value if no start given
     * @throws SQLException if error occurs in database
     */
	public static void editReview(String email, int movieId, String comment, float star) throws SQLException {
        putReview(email, movieId, comment, star);
	}

    /**
     * 
     * @param email
     * @param movieId
     * @return the number of entries deleted, 1 if comment existed, 0 otherwise
     * @throws SQLException if an error occured
     */
	public static int removeReview(String email, int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = DbDataSource.getConnection();
            int userId = UtilDB.getUserId(email);
            String q = "DELETE FROM review WHERE user_id=? and movie_id=?";
            s = c.prepareStatement(q);
            s.setInt(1, userId);
            s.setInt(2, movieId);

            return s.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            return 1;
        } finally{
            try {
                if (c != null) c.close();
                if (s != null) s.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
	}

    /**
     * 
     * @param email the users email
     * @param movieId the movie id
     * @param comment empty string if no comment to insert i.e. ""
     * @param star negative value if no start given
     * @throws SQLException if error occurs in database
     */
	public static void putReview(String email, int movieId, String comment, float star) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            int userId = UtilDB.getUserId(email);
            c = DbDataSource.getConnection();
            String q = "REPLACE INTO review(movie_id, user_id, review, rating) values (?, ?, ?, ?);";
            s = c.prepareStatement(q);
            s.setInt(1, movieId);
            s.setInt(2, userId);
            s.setString(3, comment);
            s.setFloat(4, star);

            s.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
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

    /**
     * Check if the review exists for email movieId combo
     * @param email
     * @param movieId
     * @return true if exists, false otherwise
     * @throws SQLException thrown on error
     */
	public static boolean exists(String email, int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            c = DbDataSource.getConnection();
            int userId = UtilDB.getUserId(email);
            String q = "SELECT COUNT(*) AS num FROM review WHERE user_id=? and movie_id=?";
            s = c.prepareStatement(q);
            s.setInt(1, userId);
            s.setInt(2, movieId);

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
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            return false;
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
