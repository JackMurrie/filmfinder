package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.movie.Movie;
import com.filmfinder.movie.Movies;
import com.filmfinder.recommender.SimilarityPair;

import javassist.NotFoundException;

public class RecommenderDB {
    private String list = "similarityList";

    public static void putSimilarity(int userId_1, int userId_2, float similarity) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = DbDataSource.getConnection();
            String q = "REPLACE INTO similarity(user_1, user_2, similarity) values (?, ?, ?);";
            s = c.prepareStatement(q);
            s.setInt(1, userId_1);
            s.setInt(2, userId_2);
            s.setFloat(3, similarity);

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

    public static ArrayList<SimilarityPair> getUserSimilaritiesTo(int userId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<SimilarityPair> list = new ArrayList<SimilarityPair>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT user_2 uId, similarity sim FROM similarity WHERE user_1 =?";
            s = c.prepareStatement(q);

            s.setInt(1, userId);

            rs = s.executeQuery();
            
            while (rs.next()) {
                list.add(new SimilarityPair(rs.getInt("uId"), rs.getFloat("sim")));
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

    public static Movies getRecommendedMovies2(int userId, int limit) throws SQLException, NotFoundException {
        // Based on this Shrinkage Estimator algorithm
        // https://stats.stackexchange.com/questions/6418/rating-system-taking-account-of-number-of-votes
        Movies movies = new Movies();
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT movie_id, (n/(n+m)*R+m/(n+m)*C) ranking "
                        + "FROM "
                        + "(SELECT AVG(rd1.avg_rating) C, AVG(n)+1 m FROM ( "
                        + "    SELECT AVG(r.rating) avg_rating, COUNT(r.rating) n, r.movie_id FROM review r WHERE r.user_id NOT IN ( "
                        + "        SELECT u.id FROM user u WHERE u.id IN (SELECT b.blacklisted_id FROM blacklist b WHERE b.owner_id=?) "
                        + "   ) AND r.movie_id NOT IN (SELECT w.movie_id FROM watched w WHERE w.user_id=?) "
                        + "    GROUP BY r.movie_id "
                        + ") rd1) a_t "
                        + "JOIN ( "
                        + "    SELECT AVG(r.rating) R, COUNT(r.rating) n, r.movie_id FROM review r WHERE r.user_id NOT IN ( "
                        + "        SELECT u.id FROM user u WHERE u.id IN (SELECT b.blacklisted_id FROM blacklist b WHERE b.owner_id=?) "
                        + "    ) AND r.movie_id NOT IN (SELECT w.movie_id FROM watched w WHERE w.user_id=?) "
                        + "    GROUP BY r.movie_id "
                        + ") rd2 "
                        + "ORDER BY ranking DESC LIMIT ?;";
            s = c.prepareStatement(q);

            s.setInt(1, userId);
            s.setInt(2, userId);
            s.setInt(3, userId);
            s.setInt(4, userId);
            s.setInt(5, limit);

            rs = s.executeQuery();
            
            while (rs.next()) {
                movies.add(Movie.getMovie(rs.getInt("movie_id")));
            };

            return movies;

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
