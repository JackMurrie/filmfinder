package com.filmfinder.user;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.annotations.Expose;

public class Users extends frontendObject {
    @Expose
    private ArrayList<User> users;

    public Users(ArrayList<User> users) {
        this.users = users;
    }
}
