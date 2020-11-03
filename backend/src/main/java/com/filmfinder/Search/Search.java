package com.filmfinder.Search;

import java.util.ArrayList;

import com.filmfinder.util.UrlConnector;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javassist.NotFoundException;

public class Search {

    public static ArrayList<Integer> getMovieIdsBySearch(String query, int limit) throws NotFoundException {
        ArrayList<Integer> ids = new ArrayList<Integer>();

        String data = UrlConnector.readUrl(
            "https://api.themoviedb.org/3/search/movie?"+
            "api_key=70ae629f88a806e8758ac3900483833e&language=en-US&page=1&include_adult=false&query=help");

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
        JsonArray results = jsonObject.get("results").getAsJsonArray();
        
        for (int i = 0; i < results.size(); i++) {
            ids.add(results.get(i).getAsJsonObject().get("id").getAsNumber().intValue());
            System.out.println("id: " + ids.get(i));
        }

        return ids;
    }
}
