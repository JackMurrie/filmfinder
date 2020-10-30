package com.filmfinder.db;

import java.util.ArrayList;

import com.filmfinder.user.User;
import com.filmfinder.user.Users;

public class BlacklistDB {
    public static Users getBlacklist(int userId) {
        ArrayList<User> blacklist = new ArrayList<User>();
        return new Users(blacklist);
    }

    public static void addBlacklist(int userId, int blacklistedUserId) {

    }

    public static int removeFromBlacklist(int userId, int unBlacklistId) {
        return 0;
    }

}
