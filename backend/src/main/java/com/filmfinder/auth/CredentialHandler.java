package com.filmfinder.auth;

import java.util.Date;
import java.time.Duration;
import java.sql.SQLException;
import javassist.NotFoundException;

import io.jsonwebtoken.Jwts;  
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;  
import io.jsonwebtoken.SignatureException;

import com.filmfinder.db.AuthDB;
// Note need to handle sql exceptions,
// Need to check if email already exists
// Need to check if email is valid
// Need to return/handle any of the above errors and pass correctly to front end
/**
 * CredentialHandler class that autheticate and authorise users. 
 */
public class CredentialHandler {

    static final byte[] key;
    static final int VALID_LOGIN_DURATION;
    
    static {
        key = "SECRETSECRETSECRETSECRETSECRETSECRET".getBytes();
        VALID_LOGIN_DURATION = 30;
    }

    static public String authenticate(String firstName, String lastName, String email, String password) throws SQLException, NotFoundException, Exception {

        if (AuthDB.checkEmail(email))
            throw new Exception();
        try {
            AuthDB.putCredentials(firstName, lastName, email, password.hashCode());
            return authorise(email, password);
        } catch (SQLException e) {
            throw e;
        } catch (NotFoundException e) {
            throw e;
        }
    }

    static public String authorise(String email, String password) throws SQLException, NotFoundException {

        int hashed = password.hashCode();

        int hashedDb = 0;
        try {
            hashedDb = AuthDB.getHashedPassword(email);
            if (hashedDb != hashed) 
                throw new NotFoundException("Wrong email or password");
            return generateToken(email);
        } catch (SQLException e) {
            throw e;
        } catch (NotFoundException e) {
            throw e;
        }
    }

    private static String generateToken(String email) {
 
        Date currentTime = new Date();
        Date expireDate = Date.from(currentTime.toInstant().plus(Duration.ofMinutes(VALID_LOGIN_DURATION)));
        String jws = Jwts.builder().setIssuer("http://filmfinder.com.au/").setSubject(email).setExpiration(expireDate).claim("scope", "user").signWith(SignatureAlgorithm.HS256, key).compact();
        return jws;
    }

    public static String decodeToken(String token) throws Exception {
        String userEmail = null;
        try {
            userEmail = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
            if (!AuthDB.checkEmail(userEmail)) throw new Exception();
        }
        catch (SignatureException e) {
            throw e;
        }
        return userEmail;
    }
}