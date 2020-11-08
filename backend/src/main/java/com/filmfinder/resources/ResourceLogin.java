package com.filmfinder.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
            return Response.status(400).entity("Register unsuccessful.\n").build();
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(AuthTemplate data) {
        String email = data.getEmail();
        String password = data.getPassword();
        // --> here get userID from email.
        try {
            String token = CredentialHandler.authorise(email, password);
            return Response.status(200).entity("Login successful\n").cookie(new NewCookie("auth_token", token, "/rest/", null, null, 3600, false)).build();
        } catch (Exception e) {
            return Response.status(400).entity("Login unsuccessful\n").build();
        }
    }
}
