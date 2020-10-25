package com.filmfinder.review;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;

//TODO implement database connection within object
@JsonAdapter(ReviewListSerializer.class)
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
