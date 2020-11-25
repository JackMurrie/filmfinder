package com.filmfinder.blacklist;


import java.sql.SQLException;

import com.filmfinder.db.BlacklistDB;
import com.filmfinder.frontendObject.frontendObject;
import com.filmfinder.user.Users;
import com.google.gson.annotations.Expose;

public class Blacklist extends frontendObject {
    @Expose
    private Users users;
    private int userId;

    public Blacklist(int userId) throws SQLException {
        this.userId = userId;
        this.users = BlacklistDB.getBlacklist(userId);
    }

    public void add(int userId) throws SQLException {
        BlacklistDB.addToBlacklist(this.userId, userId);
    }

    public int remove(int userId) throws SQLException {
        return BlacklistDB.removeFromBlacklist(this.userId, userId);
    }

	public void refresh() throws SQLException {
        this.users = BlacklistDB.getBlacklist(userId);
	}
}
