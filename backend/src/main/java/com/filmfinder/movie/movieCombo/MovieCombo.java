package com.filmfinder.movie.movieCombo;

import java.sql.SQLException;

import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.movie.Movie;
import com.filmfinder.review.Reviews;
import com.google.gson.annotations.Expose;

import javassist.NotFoundException;

public class MovieCombo extends frontendObject {
    @Expose
    private Movie movie;
    @Expose
    private Reviews reviews;
    @Expose
    private float rating = 0;

    public MovieCombo(int movieId) throws NotFoundException, SQLException {
        movie = Movie.getMovie(movieId);
        reviews = Reviews.getReviewsByMovieId(movieId);
        rating = movie.getAverageRating();
    }
}
