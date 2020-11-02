package com.filmfinder.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.movie.population.DBMovieData;

import javassist.NotFoundException;

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

    public static boolean checkMovie(int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT name FROM movie WHERE id=?";
            s = c.prepareStatement(q);

            s.setInt(1, movieId);

            rs = s.executeQuery();
            
            if (rs.next()) {
                return true;
            };

            return false;

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

    public static ArrayList<String> getGenres(int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT genre_name name FROM movie_genre WHERE movie_id=?";
            s = c.prepareStatement(q);

            s.setInt(1, movieId);

            rs = s.executeQuery();
            
            while (rs.next()) {
                list.add(rs.getString("name"));
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

    public static ArrayList<String> getDirectors(int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT name FROM director WHERE movie_id=?";
            s = c.prepareStatement(q);

            s.setInt(1, movieId);

            rs = s.executeQuery();
            
            while (rs.next()) {
                list.add(rs.getString("name"));
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

    public static DBMovieData getDBMovie(int movieID) throws NotFoundException, SQLException {
        DBMovieData movie = new DBMovieData();
        if (!checkMovie(movieID)) throw new NotFoundException("Movie not found");
        //TODO implement this function
        movie.setAverageRating(0);
        movie.setGenres(getGenres(movieID));
        movie.setDirectors(getDirectors(movieID));
        return movie;
        
    }

    public static float getRating(int movieId) throws NotFoundException, SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT rating FROM movie WHERE id=?";
            s = c.prepareStatement(q);
            s.setInt(1, movieId);

            rs = s.executeQuery();
            
            if (!rs.next()) {
                throw new NotFoundException("Email doesn't exist in database");
            };
            return rs.getFloat("rating");

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

    public static void updateRating(int movieId) throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            c = DbDataSource.getConnection();
            String q = "UPDATE movie " +
                       "SET rating=(SELECT avg(rating) from review where movie_id=?) " +
                       "WHERE id=?;";
            s = c.prepareStatement(q);
            s.setInt(1, movieId);
            s.setInt(2, movieId);

            rs = s.executeQuery();

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

    // hey, sorry if i missed if there is something functionally identical to this !
    //
    public static ArrayList<Integer> getMovies() throws SQLException {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            c = DbDataSource.getConnection();
            String q = "SELECT id mId FROM movie";
            s = c.prepareStatement(q);

            rs = s.executeQuery();
            
            while (rs.next()) {
                list.add(rs.getInt("mId"));
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
