package com.filmfinder.review;

import java.sql.Date;

import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Review implements frontendObject {
    private int userId;
    private int movieId;
    private String comment;
    private float rating;
    private Date post_date;

    public Review(int userId, int movieId, String comment, float rating, Date date) {
            this.userId = userId;
            this.movieId = movieId;
            this.comment = comment;
            this.rating = rating;
            this.post_date = date;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
