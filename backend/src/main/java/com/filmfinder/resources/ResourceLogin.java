package com.filmfinder.resources;

import javax.ws.rs.GET;
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
            return Response.status(400).entity("Login unsuccessful\n").build();
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

    @GET
    @Path("reset_password")
    public Response sendResetRequest() {
        int userId;
        try {
            userId = UtilDB.getUserId(CredentialHandler.decodeToken(token));
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }
        try {
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
        int userId;
        try {
            userId = UtilDB.getUserId(CredentialHandler.decodeToken(token));
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }

        try {
            CredentialHandler.resetPassword(userId, code, newPassword);
            return Response.status(200).entity("password was reset").build();
        } catch (Exception e) {
            return Response.status(400).entity("error resetting password").build();
        }
    }
}
