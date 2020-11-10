package com.filmfinder.auth;

import java.util.Date;
import java.util.Random;
import java.time.Duration;
import java.sql.SQLException;
import javassist.NotFoundException;

import io.jsonwebtoken.Jwts;  
import io.jsonwebtoken.SignatureAlgorithm;  
import io.jsonwebtoken.SignatureException;

import org.apache.commons.validator.routines.EmailValidator;

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
    static final int VALID_CODE_DURATION;
    
    static {
        key = "SECRETSECRETSECRETSECRETSECRETSECRET".getBytes();
        VALID_LOGIN_DURATION = 60;
        VALID_CODE_DURATION = 5;
    }

    static public String authenticate(String firstName, String lastName, String email, String password) throws SQLException, NotFoundException, IllegalArgumentException {
        EmailValidator validator = EmailValidator.getInstance();

        if (!validator.isValid(email)) {
            throw new IllegalArgumentException("must enter valid email");
        } 

        if (AuthDB.checkEmail(email))
            throw new IllegalArgumentException("email already exists");
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

    public static void sendVerificationRequest(int userId) throws Exception {

        Random rand = new Random();
        String code = String.format("%04d", rand.nextInt(10000));
        System.out.println(code);
        // send email here.

        Date currentTime = new Date();
        Date expireDate = Date.from(currentTime.toInstant().plus(Duration.ofMinutes(VALID_CODE_DURATION)));
        AuthDB.putVerficationCode(userId, code, expireDate);
    }

    public static void resetPassword(int userId, String code, String password) throws IllegalArgumentException, Exception {
        
        Date expireDate = AuthDB.getCodeExpiry(userId, code);

        if (!expireDate.before(new Date())) {
            throw new IllegalArgumentException();
        }
        AuthDB.setPassword(userId, password);

    }
}