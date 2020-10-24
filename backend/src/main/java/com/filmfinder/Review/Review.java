package com.filmfinder.Review;

import java.sql.Date;

public class Review {
    private int userId;
    private int movieId;
    private String comment;
    private float rating;
    private Date post_date;

    public Review(int userId, int movieId, String comment, int rating, Date date) {
            this.userId = userId;
            this.movieId = movieId;
            this.comment = comment;
            this.rating = rating;
            this.post_date = date;
    }
}
