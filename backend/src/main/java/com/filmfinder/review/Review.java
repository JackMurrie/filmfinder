package com.filmfinder.review;

import java.sql.Date;
import java.sql.SQLException;

import com.filmfinder.db.AuthDB;
import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.user.User;
import com.google.gson.annotations.Expose;

import javassist.NotFoundException;

public class Review extends frontendObject {
    @Expose
    private User user;
    @Expose
    private int movieId;
    @Expose
    private String movieName;
    @Expose
    private String comment;
    @Expose
    private float rating;
    @Expose
    private Date post_date;

    public Review(int userId, int movieId, String comment, float rating, Date date, String movieName) {
        try {
            this.user = AuthDB.getUser(userId);
        } catch (NotFoundException | SQLException e) {
            e.printStackTrace();
            this.user = null;
        }
        this.movieId = movieId;
        this.comment = comment;
        this.rating = rating;
        this.post_date = date;
        this.movieName=movieName;
    }

    public int getMovieId() {
        return movieId;
    }

    public float getRating() {
        return rating;
    }

	public String getComment() {
		return comment;
	}
}
