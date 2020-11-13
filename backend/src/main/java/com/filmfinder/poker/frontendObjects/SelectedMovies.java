package com.filmfinder.poker.frontendObjects;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.movie.Movie;
import com.google.gson.annotations.Expose;

public class SelectedMovies extends frontendObject {
    @Expose
    //Todo: Check command number
    private Integer command = 3;
    @Expose
    private ArrayList<Movie> selectedMovies = new ArrayList<Movie>();

    public void addMovie(Movie movie) {
        if (!selectedMovies.contains(movie)) {
            selectedMovies.add(movie);
        }
    }

}
