package com.filmfinder.user;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UsersSerializer implements JsonSerializer<Users>{
    @Override
    public JsonElement serialize(Users src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonList = new JsonArray();

        for (User u: src.getUsers()) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            JsonObject jsonObj = new Gson().fromJson(gson.toJson(u), JsonObject.class);
            jsonList.add(jsonObj);
        }
        return jsonList;
    }
}
