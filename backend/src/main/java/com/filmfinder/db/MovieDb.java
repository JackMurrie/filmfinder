package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovieDb {
    public static ArrayList<Integer> getMoviesNoDirector() throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT m.id id FROM movie m " +
                       "LEFT JOIN director d on m.id=d.movie_id " +
                       "WHERE d.name is NULL";
            s = c.prepareStatement(q);

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

    public static void putGenre(String name, int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = DbDataSource.getConnection();
            String q = "INSERT INTO movie_genre (genre_name, movie_id)" +
                       "VALUES" +
                       "(?, ?);";
            s = c.prepareStatement(q);
            s.setString(1, name);
            s.setInt(2, movieId);

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

    public static void putDirector(String name, int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = DbDataSource.getConnection();
            String q = "INSERT INTO director (name, movie_id)" +
                       "VALUES" +
                       "(?, ?);";
            s = c.prepareStatement(q);
            s.setString(1, name);
            s.setInt(2, movieId);

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
}
