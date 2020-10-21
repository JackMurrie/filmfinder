package com.filmfinder.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.filmfinder.auth.CredentialHandler;

import com.filmfinder.db.MovieApiParser;
import com.google.gson.Gson;


@Path("movie/")
public class ResourceMovie {

    @CookieParam("auth_token")
    private String token;

    @GET
    @Path("{movie_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("movie_id") int id ) throws Exception {
        
        try {
            CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }

        MovieApiParser parser = new MovieApiParser();
        
        Gson gson = new Gson();

        // resource table wants a "MovieData" type, what is this ? discuss with FE 
        return Response.status(200).entity(gson.toJson(parser.getMovie(id))).build();
        
    }

    @GET
    public Response getGenericMovieList() {
        return Response.status(200).entity("todo").build();
    }
}


