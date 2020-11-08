package com.filmfinder.recommender;

import java.util.Comparator;

public class SortBySimilarity implements Comparator<SimilarityPair> {
    public int compare(SimilarityPair a, SimilarityPair b) {
        return Float.compare(a.getSimilarity(), b.getSimilarity());
    }
}