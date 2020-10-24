package com.filmfinder.movie.population;

import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.db.MovieDb;

public class Populate {
    public static void populateGenreDirector() {
        try {
            ArrayList<Integer> mIds = MovieDb.getMoviesNoDirector();
            int j = 0;
            for (Integer i: mIds) {
                j++;
                try {
                    PopMovieData popMovie = PopMovieData.getPopMovieData(i);
                    System.out.printf("Inserting Movie %d of %d: %s\n", j, mIds.size(), popMovie.getTitle());
                    popMovie.updateDBDirector();
                    popMovie.updateDBGenres();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean populateDBMovieData(int movieId) {
        try {
            PopMovieData popMovie = PopMovieData.getPopMovieData(movieId);
            popMovie.updateDBDirector();
            popMovie.updateDBGenres();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
