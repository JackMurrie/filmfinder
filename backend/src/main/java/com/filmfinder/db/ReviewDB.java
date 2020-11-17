package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.review.ReviewPair;
import com.filmfinder.review.Review;
import com.filmfinder.review.Reviews;
import com.filmfinder.cache.Cache;
import com.filmfinder.recommender.RatingSimilarityPair;

import javassist.NotFoundException;


public class ReviewDB {


    /**
     * 
     * @param email
     * @param movieId
     * @return the number of entries deleted, 1 if comment existed, 0 otherwise
     * @throws SQLException if an error occured
     */
	public static int removeReview(int userId, int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = DbDataSource.getConnection();
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
	private static void putReviewMaster(String email, int movieId, String comment, float star) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        String reviewString = comment;
        float insertStar;
        if (star < 0) {
            insertStar = 0;
        } else {
            insertStar = star;
        }
        try {
            int userId = UtilDB.getUserId(email);
            c = DbDataSource.getConnection();
            try {
                Review r = getReview(movieId, userId);
                String oldReview = r.getComment();
                if (!oldReview.equals("") && comment.equals("")) {
                    reviewString = oldReview;
                }
                if (star < 0) {
                    insertStar = r.getRating();
                } 
            } catch (Exception e) {}
            String q = "REPLACE INTO review(movie_id, user_id, review, rating) values (?, ?, ?, ?);";
            s = c.prepareStatement(q);
            s.setInt(1, movieId);
            s.setInt(2, userId);
            s.setString(3, reviewString);
            s.setFloat(4, insertStar);

            s.executeUpdate();

            try {
                MovieDb.updateRating(movieId);
                Cache.refreshRating(movieId);
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
     * 
     * @param email the users email
     * @param movieId the movie id
     * @param comment empty string if no comment to insert i.e. ""
     * @param star negative value if no start given
     * @throws SQLException if error occurs in database
     */
	public static void updateRating(String email, int movieId, float star) throws SQLException {
        putReviewMaster(email, movieId, "", star);
	}

    /**
     * 
     * @param email the users email
     * @param movieId the movie id
     * @param comment empty string if no comment to insert i.e. ""
     * @throws SQLException if error occurs in database
     */
	public static void updateReview(String email, int movieId, String comment) throws SQLException {
        putReviewMaster(email, movieId, comment, -1);
    }

    public static void postRating(String email, int movieId, float star) throws SQLException {
        putReviewMaster(email, movieId, "", star);
	}

    public static void postReview(String email, int movieId, String comment) throws SQLException {
        putReviewMaster(email, movieId, comment, -1);
    }

    public static void postReview(int userId, int movieId, String comment) throws SQLException, NotFoundException {
        String email = AuthDB.getEmailFromId(userId);
        putReviewMaster(email, movieId, comment, -1);
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

    public static Reviews getReviewsByMovieId(int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<Review> list = new ArrayList<Review>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT review.rating, review comment, user_id uId, movie_id mId, name, date FROM review INNER JOIN movie ON id=movie_id WHERE movie_id=? ORDER BY date DESC";
            s = c.prepareStatement(q);

            s.setInt(1, movieId);

            rs = s.executeQuery();
            
            while (rs.next()) {
                list.add(new Review(rs.getInt("uId"), movieId, rs.getString("comment"), rs.getFloat("rating"), rs.getDate("date"), rs.getString("name")));
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

    public static Reviews getReviewsByUserId(int userId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<Review> list = new ArrayList<Review>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT review.rating, review comment, user_id uId, movie_id mId, name, date FROM review INNER JOIN movie ON id=movie_id WHERE user_id=? ORDER BY date DESC";
            s = c.prepareStatement(q);

            s.setInt(1, userId);

            rs = s.executeQuery();
            
            while (rs.next()) {
                list.add(new Review(userId, rs.getInt("mId"), rs.getString("comment"), rs.getFloat("rating"), rs.getDate("date"), rs.getString("name")));
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
            String q = "SELECT review.rating, review comment, user_id uId, movie_id mId, name, date FROM review INNER JOIN movie ON id=movie_id WHERE movie_id=? and user_id=?";
            s = c.prepareStatement(q);

            s.setInt(1, movieId);
            s.setInt(2, userId);

            rs = s.executeQuery();
            
            if (rs.next()) {
                return new Review(rs.getInt("uId"), movieId, rs.getString("comment"), rs.getFloat("rating"), rs.getDate("date"), rs.getString("name"));
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
