package com.filmfinder.movie;

public class MovieData {
    String name;
    String description;

    String director;
    String imageUrl;

    // Todo: get different available sizes and post to frontend
    String baseImageUrl = "http://image.tmdb.org/t/p/w185/";

    public MovieData(String _name, String _description, String _director, String _imageUrl) {

        name = _name;
        description = _description;
        director = _director;
        imageUrl = baseImageUrl + _imageUrl;
    }

    public String toString() {
        return "Movie: " + this.name + "\n Description: " + this.description + "\n Director: " + this.director + "\n Image URL: " + this.imageUrl;
    }
}
