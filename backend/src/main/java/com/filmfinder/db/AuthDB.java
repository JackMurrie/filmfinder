package com.filmfinder.db;

public class AuthDB {

    static public void putCredentials(String firstName, String lastName, String email, int hashedPassword) {
        return;
    }

    static public String getEmail(String handle) {
        return "user@gmail.com";
    }

    static public int getHashedPassword(String handle) {
        return "password".hashCode();
    }
    
}
