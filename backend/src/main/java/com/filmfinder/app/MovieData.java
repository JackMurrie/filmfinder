package com.filmfinder.app;

public class MovieData {
    String name;
    String description;

    String director;
    String imageUrl;

    public MovieData(String _name, String _description, String _director, String _imageUrl) {

        name = _name;
        description = _description;
        director = _director;
        imageUrl = _imageUrl;
    }

    public String toString() {
        return "Movie: " + this.name + "\n Description: " + this.description + "\n Director: " + this.director + "\n Image URL: " + this.imageUrl;
    }
}
