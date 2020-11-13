package com.filmfinder.websocket;

public class Votes {
    
    private int movieId;
    private int preference;

    public int getMovieId() {
        return this.movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getPreference() {
        return this.preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

}
