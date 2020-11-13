package com.filmfinder.poker.frontendObjects;

import java.util.ArrayList;
import java.util.HashMap;

import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.movie.Movie;
import com.google.gson.annotations.Expose;

public class SelectedMovies extends frontendObject {
    @Expose
    private Integer command = 5;
    @Expose
    private ArrayList<Movie> selectedMovies = new ArrayList<Movie>();

    public void addMovie(Movie movie) {
        if (!selectedMovies.contains(movie)) {
            selectedMovies.add(movie);
        }
    }

	public ArrayList<Movie> getMovies() {
		return selectedMovies;
	}

}
