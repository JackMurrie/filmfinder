package com.filmfinder.movie;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;


public class Movies extends frontendObject {
    private ArrayList<Movie> movies;

    public Movies(ArrayList<Movie> movies) {
        setMovies(movies);
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
