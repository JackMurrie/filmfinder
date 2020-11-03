package com.filmfinder.recommender;

public class SimilarityPair {
    private float similarity;
    private int otherUser;

    public SimilarityPair(int id, float sim) {
        otherUser = id;
        similarity = sim;
    }

    public int getOtherUser() {
        return otherUser;
    }

    public float getSimilarity() {
        return similarity;
    }

}
