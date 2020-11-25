package com.filmfinder.templates;
import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class MovieIdTemplate {
    private int movieId;

    public int getMovieId() {
        return this.movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
