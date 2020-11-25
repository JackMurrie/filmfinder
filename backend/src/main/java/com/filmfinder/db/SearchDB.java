package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchDB {
    public static ArrayList<Integer> searchDirector(String director, int limit) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT id FROM movie JOIN " +
                        "(SELECT movie_id FROM director WHERE name=?) AS movies " +
                        "ON movie.id=movies.movie_id " +
                        "ORDER BY movie.rating DESC LIMIT ?;";
            s = c.prepareStatement(q);
            s.setString(1, director);
            s.setInt(2, limit);

            rs = s.executeQuery();
            
            while (rs.next()) {
                list.add(rs.getInt("id"));
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

    public static ArrayList<Integer> searchGenre(String genre, int limit) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT id FROM movie JOIN " +
                        "(SELECT movie_id FROM movie_genre WHERE genre_name=?) AS movies " +
                        "ON movie.id=movies.movie_id " +
                        "ORDER BY movie.rating DESC LIMIT ?;";
            s = c.prepareStatement(q);
            s.setString(1, genre);
            s.setInt(2, limit);

            rs = s.executeQuery();
            
            while (rs.next()) {
                list.add(rs.getInt("id"));
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
}
