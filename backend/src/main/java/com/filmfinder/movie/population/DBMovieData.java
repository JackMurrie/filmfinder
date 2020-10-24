package com.filmfinder.movie.population;

import java.util.ArrayList;

public class DBMovieData {
    private ArrayList<String> genres;
    private ArrayList<String> directors;
    private float averageRating;

    public DBMovieData() {}

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void setDirectors(ArrayList<String> directors) {
        this.directors = directors;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getDirectors() {
        return directors;
    }

    public float getAverageRating() {
        return averageRating;
    }
}
