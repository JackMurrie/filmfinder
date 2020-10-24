package com.filmfinder.dashboard;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.movie.Movie;
import com.filmfinder.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Dashboard implements frontendObject {
    @Expose
    private ArrayList<Movie> watchlist;
    private ArrayList<Movie> wishlist;
    private ArrayList<Movie> recommedations;
    private ArrayList<User> blacklisted;

    public Dashboard(int userId) {
        //TODO implement functions
        try {
            ArrayList<Integer> wList = new ArrayList<Integer>();
            wList.add(2);
            wList.add(5);
            watchlist = new ArrayList<Movie>();
            for (Integer i: wList) {
                watchlist.add(Movie.getMovie(i));
            }
        } catch (Exception e) {

        }
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }

}
