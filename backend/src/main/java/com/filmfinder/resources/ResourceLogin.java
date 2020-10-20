package com.filmfinder.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
    public Response registerUser(AuthTemplate data) throws Exception {
        String first = data.getFirstName();
        String last = data.getLastName();
        String email = data.getEmail();
        String password = data.getPassword();
        System.out.println("first="+first+", last="+last+", email="+email+", password="+ password);

        String token = CredentialHandler.authenticate(first, last, email, password);
        if (token == null) {
            // throw exception.
            return Response.status(400).entity("Register unsuccessful\n").build();
        } else {
            return Response.status(200).entity("Register successful\n").build();
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(AuthTemplate data) throws Exception {
        String email = data.getEmail();
        String password = data.getPassword();
        System.out.println("email="+email+", password="+ password);
        String token = CredentialHandler.authorise(email, password);
        if (token == null) {
            // throw exception.
            return Response.status(400).entity("Login unsuccessful\n").build();
        } else {
            return Response.status(200)
                    .entity("Login successful\n")
                    .cookie(new NewCookie("auth_token", token))
                    .build();
        }
    }
}
