package com.filmfinder.auth;

import java.util.Date;
import java.time.Duration;
import java.sql.SQLException;
import javassist.NotFoundException;

import io.jsonwebtoken.Jwts;  
import io.jsonwebtoken.SignatureAlgorithm;  

import com.filmfinder.db.AuthDB;
// Note need to handle sql exceptions,
// Need to check if email already exists
// Need to check if email is valid
// Need to return/handle any of the above errors and pass correctly to front end

public class CredentialHandler {

    static final byte[] key;
    static final int VALID_LOGIN_DURATION;
    
    static {
        key = "SECRET".getBytes();
        VALID_LOGIN_DURATION = 30;
    }

    static public String authenticate(String firstName, String lastName, String email, String password) {
        try {
            AuthDB.putCredentials(firstName, lastName, email, password.hashCode());
        } catch (SQLException e) {
            return null;
        } 
        return authorise(email, password);
    }

    static public String authorise(String email, String password) {

        int hashed = password.hashCode();

        int hashedDb = 0;
        try {
            hashedDb = AuthDB.getHashedPassword(email);
        } catch (SQLException e) {
            return null;
        } catch (NotFoundException e) {
            return null;
        }
        String token = null;
        if (hashed == hashedDb) {
            token = generateToken(email);
        }
        return token;
    }

    private static String generateToken(String email) {
        // byte[] key = "SECRET".getBytes();
        // int VALID_LOGIN_DURATION = 30;

        Date currentTime = new Date();
        Date expireDate = Date.from(currentTime.toInstant().plus(Duration.ofMinutes(VALID_LOGIN_DURATION)));
        String jwt = Jwts.builder().setIssuer("http://filmfinder.com.au/").setSubject(email).setExpiration(expireDate).claim("scope", "user").signWith(SignatureAlgorithm.HS256, key).compact();
        return jwt;
    }

    private String decodeToken(String token) {
        
        // try {
        //     Jws claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        //     String userName = claims.getBody().getSubject();
        //     /* Here we need to check if userName matches with the userName extracted from token */
        //     // userNameDB = extractedUserName(token);
        //     // if (userName.equals(userNameDB))
        //     //     return
        // }
        // catch (SignatureException e) {
        //     return null;
        // }
        return token.substring(0, token.length() - 5);
    }
}