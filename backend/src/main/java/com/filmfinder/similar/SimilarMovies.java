package com.filmfinder.similar;

import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.movie.Movie;
import com.filmfinder.movie.Movies;
import com.filmfinder.util.UrlConnector;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javassist.NotFoundException;

public class SimilarMovies {

    public static Movies getSimilarMovies(int movieId, int limit) throws NotFoundException, SQLException {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/similar?" +
            "api_key=70ae629f88a806e8758ac3900483833e&language=en-US&page=1&include_adult=false";
        String data = UrlConnector.readUrl(url);
        
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
        JsonArray results = jsonObject.get("results").getAsJsonArray();
        
        for (int i = 0; i < Math.min(results.size(), limit); i++) {
            try {
                int id = results.get(i).getAsJsonObject().get("id").getAsNumber().intValue();
                movies.add(Movie.getMovie(id));
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        return new Movies(movies);
    }
}
