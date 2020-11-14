package com.filmfinder.resources;

import javax.ws.rs.GET;
import java.sql.SQLException;
import javassist.NotFoundException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Cookie;
import javassist.NotFoundException;

import com.filmfinder.templates.AuthTemplate;
import com.filmfinder.templates.ForgotPasswordTemplate;
import com.filmfinder.templates.ResetPasswordTemplate;

import org.eclipse.jetty.util.security.Credential;

import javassist.NotFoundException;

import com.filmfinder.auth.CredentialHandler;
import com.filmfinder.db.AuthDB;
import com.filmfinder.db.UtilDB;

@Path("auth/")
public class ResourceLogin {

    @CookieParam("auth_token")
    private String token;

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(AuthTemplate data) {
        String first = data.getFirstName();
        String last = data.getLastName();
        String email = data.getEmail();
        String password = data.getPassword();
        String token = null;

        try {
            token = CredentialHandler.authenticate(first, last, email, password);
            return Response.status(200).entity("Register successful").cookie(new NewCookie("auth_token", token, "/rest/", null, null, 3600, false)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(400).entity("Register unsuccessful").build();
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(AuthTemplate data) {
        String email = data.getEmail();
        String password = data.getPassword();
        try {
            String token = CredentialHandler.authorise(email, password);
            return Response.status(200).entity("Login successful\n").cookie(new NewCookie("auth_token", token, "/rest/", null, null, 3600, false)).build();
        } catch (NotFoundException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (Exception e) {
            String message = "Login unsuccessful.";
            if (e.getClass() == SQLException.class) {
                message = "Database error. SQL Exception";
            } else if (e.getClass() == NotFoundException.class) {
                message = "Data not found.";
            }
            return Response.status(400).entity(message).build();
        }
    }

    @GET
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout(@CookieParam("auth_token") Cookie cookie) {

        if (cookie != null) {
            return Response.status(200).entity("Logout successful").cookie(new NewCookie("auth_token", "", "/rest/", null, null, 0, false)).build();
        }
        return Response.status(200).entity("OK - No session").build();
    }
    
    @POST
    @Path("request_reset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendForgotPasswordResetRequest(ForgotPasswordTemplate data) {
        String email = data.getEmail();

        int userId;
        try {
            if (email == null) {
                email = CredentialHandler.decodeToken(token);
            }
            userId = UtilDB.getUserId(email);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }

        try {
            if (!AuthDB.checkEmail(email)) {
                return Response.status(400).entity("email doesn't exist").build();
            }
            CredentialHandler.sendVerificationRequest(userId);
            return Response.status(200).entity("verification code sent to user email").build();
        } catch (Exception e) {
            return Response.status(400).entity("could not send reset request").build();
        }
    }

    @POST
    @Path("reset_password")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response resetUserPassword(ResetPasswordTemplate data) {
        String code = data.getCode();
        String newPassword = data.getPassword();
        String email = data.getEmail();
        int userId;

        try {
            if (email == null) {
                email = CredentialHandler.decodeToken(token);
            }
            userId = UtilDB.getUserId(email);
        } catch (Exception e) {
            return Response.status(400).entity("authorisation failure").build();
        }

        try {
            CredentialHandler.resetPassword(userId, code, newPassword);
            return Response.status(200).entity("password was reset").build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity("timeout - verification code has expired").build();
        } catch (Exception e) {
            return Response.status(400).entity("error resetting password: "+e.getMessage()).build();
        }
    }

    @GET
    @Path("deactivate")
    public Response deactivateUserAccount() {
        int userId;
        try {
            userId = UtilDB.getUserId(CredentialHandler.decodeToken(token));
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }
        try {
            AuthDB.deleteUser(userId);
            return Response.status(200).entity("user account deleted").cookie(new NewCookie("auth_token", "", "/rest/", null, null, 0, false)).build();
        } catch (Exception e) {
            return Response.status(400).entity("there was error while deleting user account").build();
        }
    }
}
