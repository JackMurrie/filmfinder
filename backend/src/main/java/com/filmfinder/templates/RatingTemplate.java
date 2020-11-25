package com.filmfinder.templates;

import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class RatingTemplate {
    
    private float rating;

    public float getRating() {
        return this.rating;
    }

    public void setRating(float rating) {
        if (rating > 5) {
            this.rating = 5;
        } else if (rating < 0) {
            this.rating = 0;
        } else {
            this.rating = rating;
        }
    }
}