package com.filmfinder.review;

import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.db.ReviewDB;
import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(ReviewListSerializer.class)
public class Reviews extends frontendObject {
    @Expose
    private ArrayList<Review> reviews;

    public Reviews(ArrayList<Review> reviews) {
        setReviews(reviews);
    }

    public static Reviews getReviewsByMovieId(int movieId) throws SQLException {
        return ReviewDB.getReviewsByMovieId(movieId);
    }

    public static Reviews getReviewsByUserId(int userId) throws SQLException {
        return ReviewDB.getReviewsByUserId(userId);
    }
    
	public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
