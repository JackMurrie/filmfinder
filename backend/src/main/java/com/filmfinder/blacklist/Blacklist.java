package com.filmfinder.blacklist;

import com.filmfinder.db.BlacklistDB;
import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.user.Users;
import com.google.gson.annotations.Expose;

public class Blacklist extends frontendObject {
    @Expose
    private Users users;
    private int userId;

    public Blacklist(int userId) {
        this.userId = userId;
    }

    public void add(int userId) {
        BlacklistDB.addBlacklist(this.userId, userId);
    }

    public void remove(int userId) {
        BlacklistDB.removeFromBlacklist(this.userId, userId);
    }
}
