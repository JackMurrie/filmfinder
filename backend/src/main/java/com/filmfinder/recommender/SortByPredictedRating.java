package com.filmfinder.recommender;

import java.util.Comparator;

public class SortByPredictedRating implements Comparator<PredictedMoviePair> {
    public int compare(PredictedMoviePair a, PredictedMoviePair b) {
        return Float.compare(a.getPredictedRating(), b.getPredictedRating());
    }
}