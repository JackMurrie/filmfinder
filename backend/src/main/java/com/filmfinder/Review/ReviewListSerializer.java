package com.filmfinder.review;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ReviewListSerializer implements JsonSerializer<Reviews> {

    @Override
    public JsonElement serialize(Reviews src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonList = new JsonArray();

        for (Review r: src.getReviews()) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            JsonObject jsonObj = new Gson().fromJson(gson.toJson(r), JsonObject.class);
            jsonList.add(jsonObj);
        }
        return jsonList;
    }
    
}
