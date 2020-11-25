package com.filmfinder.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.filmfinder.auth.CredentialHandler;
import com.filmfinder.movie.Movies;
import com.filmfinder.movie.movieCombo.MovieCombo;
import com.filmfinder.similar.SimilarMovies;
// import com.filmfinder.template.MovieLimitTemplate;

@Path("movies/{movie_id}")
public class ResourceMovie {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("movie_id") int movieId) {
        
        try {
            MovieCombo mc = new MovieCombo(movieId);
            return Response.status(200).entity(mc.toJson()).build();
        } catch (Exception e) {
            return Response.status(400).entity("Failed to get movie data").build();
        }
        
    }

    @GET
    @Path("/similar")
    @Produces(MediaType.APPLICATION_JSON)
    // public Response getSimilarMovie(@PathParam("movie_id") int movieId, MovieLimitTemplate data) {
    public Response getSimilarMovie(@PathParam("movie_id") int movieId) {
    
        // int limit = data.getLimit();
        int limit = 10;
        try {
            Movies movies = SimilarMovies.getSimilarMovies(movieId, limit);
            return Response.status(200).entity(movies.toJson()).build();
        } catch (Exception e) {
            return Response.status(400).entity("Failed to get movie data").build();
        }
        
    }
}


