package com.filmfinder.recommender;

public class RatingSimilarityPair {
    private float rating, similarity;

    public RatingSimilarityPair(float _rating, float _similarity) {
        rating = _rating;
        similarity = _similarity;
    }

    public float getRating() {
        return rating;
    }

    public float getSimilarity() {
        return similarity;
    }

}
