package com.filmfinder.movie;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.annotations.Expose;


public class Movies extends frontendObject {
    @Expose
    private ArrayList<Movie> movies;

    public Movies() {
        movies = new ArrayList<Movie>();
    }
    public Movies(ArrayList<Movie> movies) {
        setMovies(movies);
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
    
    public void add(Movie m) {
        movies.add(m);
    }
}
