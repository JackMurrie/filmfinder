package com.filmfinder.Search;

import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.movie.Movie;
import com.filmfinder.movie.Movies;
import com.filmfinder.util.UrlConnector;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javassist.NotFoundException;

public class Search {

    public static Movies getMovieIdsBySearch(String query, int limit) throws NotFoundException, SQLException {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        String data = UrlConnector.readUrl(
            "https://api.themoviedb.org/3/search/movie?"+
            "api_key=70ae629f88a806e8758ac3900483833e&language=en-US&page=1&include_adult=false&query="+query);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
        JsonArray results = jsonObject.get("results").getAsJsonArray();
        
        for (int i = 0; i < Math.min(results.size(), limit); i++) {
            try {
                int id = results.get(i).getAsJsonObject().get("id").getAsNumber().intValue();
                System.out.println(id);
                movies.add(Movie.getMovie(id));
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
            // System.out.println("id: " + ids.get(i));
        }

        return new Movies(movies);
    }
}
