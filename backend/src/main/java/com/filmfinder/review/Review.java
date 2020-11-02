package com.filmfinder.Review;

import java.sql.Date;

import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.annotations.Expose;

public class Review extends frontendObject {
    @Expose
    private int userId;
    @Expose
    private int movieId;
    @Expose
    private String comment;
    @Expose
    private float rating;
    @Expose
    private Date post_date;

    public Review(int userId, int movieId, String comment, float rating, Date date) {
            this.userId = userId;
            this.movieId = movieId;
            this.comment = comment;
            this.rating = rating;
            this.post_date = date;
    }

    public int getMovieId() {
        return movieId;
    }

    public float getRating() {
        return rating;
    }
}
