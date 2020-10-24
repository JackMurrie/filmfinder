package com.filmfinder.frontendObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class frontendObject {
    public String toJson() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    };

    @Override
    public String toString() {
        return toJson();
    }
}
