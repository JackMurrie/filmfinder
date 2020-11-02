package com.filmfinder.Review;

import java.util.Comparator;

public class SortByRating implements Comparator<Review> {
    public int compare(Review a, Review b) {
        return Float.compare(a.getRating(), b.getRating());
    }
}