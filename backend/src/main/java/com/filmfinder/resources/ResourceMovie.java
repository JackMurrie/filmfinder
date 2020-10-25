package com.filmfinder.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.filmfinder.auth.CredentialHandler;
import com.filmfinder.movie.movieCombo.MovieCombo;

@Path("movies/")
public class ResourceMovie {

    @CookieParam("auth_token")
    private String token;

    @GET
    @Path("{movie_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("movie_id") int movieId) {
        
        try {
            CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }
        try {
            MovieCombo mc = new MovieCombo(movieId);
            // resource table wants a "MovieData" type, what is this ? discuss with FE 
            return Response.status(200).entity(mc.toJson()).build();
        } catch (Exception e) {
            return Response.status(400).entity("Failed to get movie data").build();
        }
        
    }

    @GET
    public Response getGenericMovieList() {
        return Response.status(200).entity("todo").build();
    }
}


