package com.filmfinder.review;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.annotations.Expose;

//TODO implement database connection within object
public class Reviews extends frontendObject {
    @Expose
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
