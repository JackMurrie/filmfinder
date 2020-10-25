package com.filmfinder.movie;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;

import com.filmfinder.db.MovieDb;
import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.movie.population.DBMovieData;
import com.filmfinder.movie.population.Populate;
import com.filmfinder.util.UrlConnector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;

import javassist.NotFoundException;

public class Movie extends frontendObject {
    @Expose
    String name;

    @Expose
    ArrayList<String> genres;

    @Expose
    ArrayList<String> directors;

    @Expose
    String imageUrl;

    @Expose
    float averageRating;

    @Expose
    String description;

    @Expose
    String year;

    // Todo: get different available sizes and post to frontend
    String baseImageUrl = "http://image.tmdb.org/t/p/w185/";

    private Movie() {}

    public static Movie getMovie(int id) throws NotFoundException, SQLException {
        Movie movie = getTMDBData(id);
        movie.checkGetLocalData(id);
        return movie;
    }

    private void checkGetLocalData(int id) throws NotFoundException, SQLException {
        if (!getDBData(id)) {
            System.out.println("Movie not in DB");
            if (!Populate.populateDBMovieData(id)) {
                throw new NotFoundException("Movie doesn't exist");
            }
            getDBData(id);
        }
    }

    private boolean getDBData(int id) throws SQLException {
        try {
            DBMovieData data = MovieDb.getDBMovie(id);
            this.genres = data.getGenres();
            this.directors = data.getDirectors();
            this.averageRating = data.getAverageRating();
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    private static Movie getTMDBData(int id) throws NotFoundException {
        String data = UrlConnector.readUrl("https://api.themoviedb.org/3/movie/" + id
                + "?api_key=70ae629f88a806e8758ac3900483833e&append_to_response=credits");

        GsonBuilder builder = new GsonBuilder();

        JsonDeserializer<Movie> deserializer = new JsonDeserializer<Movie>() {
            @Override
            public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();

                Movie movie = new Movie();
                movie.name = jsonObject.get("original_title").getAsString();
                movie.description = jsonObject.get("overview").getAsString();
                movie.imageUrl = movie.baseImageUrl+jsonObject.get("backdrop_path").getAsString();
                movie.year = jsonObject.get("release_date").getAsString();

                return movie;
            }

        };

        builder.registerTypeAdapter(Movie.class, deserializer);

        Gson gson = builder.create();

        Movie movie = gson.fromJson(data, Movie.class);
        return movie;
    }

    @Override
    public String toString() {
        return toJson();
        // return "Movie: " + this.name + "\n Description: " + this.description + "\n Director: " + this.director + "\n Image URL: " + this.imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }
}
