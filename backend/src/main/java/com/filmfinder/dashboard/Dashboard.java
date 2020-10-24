package com.filmfinder.dashboard;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.movie.Movie;
import com.filmfinder.user.User;
import com.google.gson.annotations.Expose;

public class Dashboard extends frontendObject {
    @Expose
    private ArrayList<Movie> watchlist;
    @Expose
    private ArrayList<Movie> wishlist;
    @Expose
    private ArrayList<Movie> recommedations;
    @Expose
    private ArrayList<User> blacklisted;

    public Dashboard(int userId) {
        //TODO implement functions
        watchlist = new ArrayList<Movie>();
        wishlist = new ArrayList<Movie>();
        recommedations = new ArrayList<Movie>();
        blacklisted = new ArrayList<User>();
        try {
            ArrayList<Integer> wList = new ArrayList<Integer>();
            wList.add(2);
            wList.add(5);
            for (Integer i: wList) {
                watchlist.add(Movie.getMovie(i));
            }
        } catch (Exception e) {

        }
    }

}
