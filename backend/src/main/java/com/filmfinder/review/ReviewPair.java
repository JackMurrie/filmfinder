package com.filmfinder.review;

public class ReviewPair {
    private float ratingA, ratingB;
    private int movieId;

    public ReviewPair(float a, float b, int _movieId) {
        ratingA = a;
        ratingB = b;
        movieId = _movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public float getFirstRating() {
        return ratingA;
    }

    public float getSecondRating() {
        return ratingB;
    }
}
