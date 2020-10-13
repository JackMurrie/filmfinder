package com.filmfinder.auth;

import com.filmfinder.db.AuthDB;

public class CredentialHandler {

    static public String authenticate(String firstName, String lastName, String email, String password) {
        AuthDB.putCredentials(firstName, lastName, email, password.hashCode());
        return authorise(email, password);
    }

    static public String authorise(String email, String password) {
        String handle = "";
        int hashed = password.hashCode();
        String email_ = AuthDB.getEmail(handle);
        int hashed_ = AuthDB.getHashedPassword(handle);
    
        String token = null;
        if (email.equals(email_) && hashed == hashed_)
            token = generateToken("token");
        return token;
    }

    private static String generateToken(String key) {
        return key + "token";
    }

    private String decodeToken(String token) {
        return token.substring(0, token.length() - 5);
    }
}