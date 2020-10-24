package com.filmfinder.review;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;

public class Reviews extends frontendObject {
    private ArrayList<Review> reviews;

    public Reviews(ArrayList<Review> reviews) {
        setReviews(reviews);
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

}
