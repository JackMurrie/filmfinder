package com.filmfinder.poker.frontendObjects;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.movie.Movie;
import com.google.gson.annotations.Expose;

public class Results extends frontendObject {
    @Expose
    private Integer command = 6;
    @Expose
    private ArrayList<Movie> orderedMovies = new ArrayList<Movie>();
    @Expose
    private ArrayList<Integer> votes = new ArrayList<Integer>();
}
