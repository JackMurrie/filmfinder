package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.movie.Movie;
import com.filmfinder.movie.Movies;

import javassist.NotFoundException;

public abstract class MovieListDB {
    public void addToList(String list, int userId, int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = DbDataSource.getConnection();
            String q = "REPLACE INTO " + list + "(user_id, movie_id) values (?, ?);";
            s = c.prepareStatement(q);
            s.setInt(1, userId);
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

    public int deleteFromList(String list, int userId, int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = DbDataSource.getConnection();
            String q = "DELETE FROM "+ list +" WHERE user_id=? and movie_id=?";
            s = c.prepareStatement(q);
            s.setInt(1, userId);
            s.setInt(2, movieId);

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

    public ArrayList<Integer> getMovieList(String list, int userId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<Integer> returnList = new ArrayList<Integer>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT movie_id id FROM " + list + " WHERE user_id=?";
            s = c.prepareStatement(q);

            s.setInt(1, userId);

            rs = s.executeQuery();
            
            while (rs.next()) {
                returnList.add(rs.getInt("id"));
            };

            return returnList;

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

    public Movies getMovies(String list, int userId) throws NotFoundException, SQLException {
        ArrayList<Integer> movieIds = getMovieList(list, userId);
        Movies movies = new Movies();
        for (Integer id: movieIds) {
            movies.add(Movie.getMovie(id));
        }
        return movies;
    }

    public abstract void addToList(int movieId) throws SQLException;
    public abstract int deleteFromList(int movieId) throws SQLException;
    public abstract ArrayList<Integer> getMovieList() throws SQLException;
    public abstract Movies getMovies() throws NotFoundException, SQLException;
}
