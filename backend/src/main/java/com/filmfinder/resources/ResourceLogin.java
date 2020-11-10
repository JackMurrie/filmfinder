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

import io.jsonwebtoken.SignatureAlgorithm;

import com.filmfinder.templates.AuthTemplate;
import com.filmfinder.auth.CredentialHandler;

@Path("auth/")
public class ResourceLogin {

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
            return Response.status(200).entity("Register successful.\n").cookie(new NewCookie("auth_token", token, "/rest/", null, null, 3600, false)).build();
        } catch (Exception e) {
            String message = "Register unsuccessful.";
            if (e.getClass() == SQLException.class) {
                message = "Database error. SQL Exception";
            } else if (e.getClass() == NotFoundException.class) {
                message = "Data not found.";
            }
            return Response.status(400).entity(message).build();
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
}
