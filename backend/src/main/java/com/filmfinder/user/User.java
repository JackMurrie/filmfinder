package com.filmfinder.user;

import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class User extends frontendObject {
    @Expose
    private int userId;
    @Expose
    private String first;
    private String last;
    private String email;

    public User(int userId, String first, String last, String email) {
        this.userId = userId;
        this.first = first;
        this.last = last;
        this.email = email;
    }

}
