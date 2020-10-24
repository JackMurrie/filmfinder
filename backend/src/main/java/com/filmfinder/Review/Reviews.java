package com.filmfinder.review;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Reviews implements frontendObject {
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

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
