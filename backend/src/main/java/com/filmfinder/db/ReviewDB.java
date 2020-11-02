package com.filmfinder.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.Review.ReviewPair;
import com.filmfinder.Review.Review;
import com.filmfinder.Review.Reviews;
import com.filmfinder.recommender.RatingSimilarityPair;

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

            int number = s.executeUpdate();
            MovieDb.updateRating(movieId);
            
            return number;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            return 1;
        } finally {
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

            try {
                MovieDb.updateRating(movieId);
            } catch (Exception e){}

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

    public static Reviews getReviews(int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<Review> list = new ArrayList<Review>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT rating, review comment, user_id uId, movie_id mId FROM review WHERE movie_id=?";
            s = c.prepareStatement(q);

            s.setInt(1, movieId);

            rs = s.executeQuery();
            
            while (rs.next()) {
                //TODO: implement Date
                list.add(new Review(rs.getInt("uId"), movieId, rs.getString("comment"), rs.getFloat("rating"), new Date(10000)));
            };

            return new Reviews(list);

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

    public static ArrayList<RatingSimilarityPair> getReviewedWithSimilarities(int movieId, int userId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<RatingSimilarityPair> ratings = new ArrayList<RatingSimilarityPair>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT rating rat, similarity sim FROM review r LEFT JOIN similarity s on r.user_id = s.user_2 WHERE movie_id=? and s.user_1=? ORDER BY s.similarity LIMIT 5";
            s = c.prepareStatement(q);

            s.setInt(1, movieId);
            s.setInt(2, userId);

            rs = s.executeQuery();
            
            while (rs.next()) {
                ratings.add(new RatingSimilarityPair(rs.getFloat("rat"), rs.getFloat("sim")));
            };
            return ratings;

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

    public static ArrayList<ReviewPair> getReviewPairs(int userId_1, int userId_2) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<ReviewPair> list = new ArrayList<ReviewPair>();
        try {
            c = DbDataSource.getConnection();
            String q = "Select distinct r1.movie_id mId, r1.rating rR1, r2.rating rR2 from review r1, review r2 where r1.user_id=? and r2.user_id=? and r1.movie_id = r2.movie_id";
            s = c.prepareStatement(q);

            s.setInt(1, userId_1);
            s.setInt(2, userId_2);

            rs = s.executeQuery();
            
            while (rs.next()) {
                list.add(new ReviewPair(rs.getFloat("rR1"), rs.getFloat("rR2"), rs.getInt("mId")));
            };

            return list;

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

    public static Review getReview(int movieId, int userId) throws SQLException, NotFoundException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT rating, review comment, user_id uId, movie_id mId FROM review WHERE movie_id=? and user_id=?";
            s = c.prepareStatement(q);

            s.setInt(1, movieId);
            s.setInt(2, userId);

            rs = s.executeQuery();
            
            if (rs.next()) {
                //TODO: implement Date
                return new Review(rs.getInt("uId"), movieId, rs.getString("comment"), rs.getFloat("rating"), new Date(10000));
            } else {
                throw new NotFoundException("review for user " + userId + " does not exist");
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
}
