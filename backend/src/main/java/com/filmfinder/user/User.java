package com.filmfinder.user;

import java.sql.SQLException;

import com.filmfinder.db.AuthDB;
import com.filmfinder.frontendObject.frontendObject;
import com.google.gson.annotations.Expose;

import javassist.NotFoundException;

public class User extends frontendObject {
    @Expose
    private int userId;
    @Expose
    private String first;
    @Expose
    private String last;
    @Expose
    private String email;
    @Expose
    private String profilePicUrl;

    public User(int userId, String first, String last, String email, String url) {
        this.userId = userId;
        this.first = first;
        this.last = last;
        this.email = email;
        this.profilePicUrl = url;
    }

    public static User getUser(int userId) throws NotFoundException, SQLException {
        return AuthDB.getUser(userId);
	}

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        User rev = (User) obj;

        return (this.userId==rev.getUserId());
    }

    @Override
    public int hashCode() {
        return userId;
    }
}
