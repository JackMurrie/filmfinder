package com.filmfinder.dashboard;

import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.blacklist.Blacklist;
import com.filmfinder.db.ReviewDB;
import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.movie.Movies;
import com.filmfinder.movieLists.Watchlist;
import com.filmfinder.movieLists.Wishlist;
import com.filmfinder.review.Reviews;
import com.filmfinder.user.User;
import com.filmfinder.user.Users;
import com.google.gson.annotations.Expose;

import javassist.NotFoundException;

public class Dashboard extends frontendObject {
    @Expose
    private Watchlist watchlist;
    @Expose
    private Wishlist wishlist;
    @Expose
    private Movies recommendations;
    @Expose
    private Reviews reviews;
    @Expose
    private Blacklist blacklisted;

    public Dashboard(int userId) throws NotFoundException, SQLException {
        watchlist = new Watchlist(userId);
        wishlist = new Wishlist(userId);
        recommedations = new Movies();
        reviews = Reviews.getReviews(userId);
        blacklisted = new Blacklist(userId);
        recommendations = new Movies();
    }

}
