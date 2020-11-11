package com.filmfinder.auth;

import java.util.Date;
import java.util.Random;
import java.time.Duration;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.Transport;

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
    static final String SYSTEM_EMAIL;
    static final String SYSTEM_PASSWORD;
    static {
        key = "SECRETSECRETSECRETSECRETSECRETSECRET".getBytes();
        VALID_LOGIN_DURATION = 60;
        VALID_CODE_DURATION = 5;
        SYSTEM_EMAIL = "noreply.filmfinder@gmail.com";
        SYSTEM_PASSWORD = "lol_i_dunno@admin";
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

        String to = AuthDB.getEmailFromId(userId);

        try {
            sendCodeToEmail(to, code);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Date currentTime = new Date();
        Date expireDate = Date.from(currentTime.toInstant().plus(Duration.ofMinutes(VALID_CODE_DURATION)));
        AuthDB.putVerficationCode(userId, code, expireDate);
    }

    public static void resetPassword(int userId, String code, String password) throws IllegalArgumentException, Exception {
        
        Date expireDate = AuthDB.getCodeExpiry(userId, code);

        if (!expireDate.before(new Date())) {
            throw new IllegalArgumentException();
        }
        AuthDB.setPassword(userId, password.hashCode());
    }

    private static void sendCodeToEmail(String to, String code) throws Exception {
        Properties props = new Properties();    
        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class",    
                    "javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");  

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SYSTEM_EMAIL, SYSTEM_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SYSTEM_EMAIL));
        message.setRecipients(
        Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Password reset verification code");
        
        String msg = "Forgot your password? We got your back! Your verification code is "+code+".";
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");
        
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
    }

}