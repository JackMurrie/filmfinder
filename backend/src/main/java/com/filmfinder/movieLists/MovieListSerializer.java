package com.filmfinder.movieLists;

import java.lang.reflect.Type;

import com.filmfinder.movie.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MovieListSerializer implements JsonSerializer<MovieList> {

    @Override
    public JsonElement serialize(MovieList src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonList = new JsonArray();

        for (Movie m: src.getMovies().arrayList()) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            JsonObject jsonObj = new Gson().fromJson(gson.toJson(m), JsonObject.class);
            jsonList.add(jsonObj);
        }
        JsonObject obj = new JsonObject();
        obj.add("movies", jsonList);
        return obj;
    }
    
}
