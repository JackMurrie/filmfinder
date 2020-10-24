package com.filmfinder.user;

import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User implements frontendObject {
    private int userId;
    private String first;
    private String last;
    private String email;

    public User(int userId, String first, String last, String email) {
        this.userId = userId;
        this.first = first;
        this.last = last;
        this.email = email;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
