package com.filmfinder.dashboard;

import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.movie.Movies;
import com.filmfinder.movieLists.MovieListSerializer;
import com.filmfinder.movieLists.Watchlist;
import com.filmfinder.movieLists.Wishlist;
import com.filmfinder.user.User;
import com.filmfinder.user.Users;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;

import javassist.NotFoundException;

public class Dashboard extends frontendObject {
    @Expose
    @JsonAdapter(MovieListSerializer.class)
    private Watchlist watchlist;
    @Expose
    @JsonAdapter(MovieListSerializer.class)
    private Wishlist wishlist;
    @Expose
    private Movies recommedations;
    @Expose
    private Users blacklisted;

    public Dashboard(int userId) throws NotFoundException, SQLException {
        watchlist = new Watchlist(userId);
        wishlist = new Wishlist(userId);
        //TODO implement recommendation and blacklisted functions
        recommedations = new Movies();
        blacklisted = new Users(new ArrayList<User>());
    }

    @Override
    public String toJson() {
        // TODO Auto-generated method stub
        return super.toJson();
    }

}
