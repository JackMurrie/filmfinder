package com.filmfinder.user;

import java.util.ArrayList;

import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(UsersSerializer.class)
public class Users extends frontendObject {
    @Expose
    private ArrayList<User> users;

    public Users(ArrayList<User> users) {
        this.users = users;
    }

	public ArrayList<User> getUsers() {
		return users;
	}
}
