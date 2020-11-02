package com.filmfinder.recommender;

public class PredictedMoviePair {
    private float predictedRating;
    private int movieId;

    public PredictedMoviePair(int id, float rating) {
        movieId = id;
        predictedRating = rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public float getPredictedRating() {
        return predictedRating;
    }

}
