package com.filmfinder.resources;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

import java.sql.SQLException;

import com.filmfinder.movie.Movies;

import javassist.NotFoundException;

import com.filmfinder.Search.Search;

@Path("search/")
public class ResourceSearch {

    @POST
    @Path("search")
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    public Response search(String searchString) throws NotFoundException, SQLException {
        try {
            Movies movies = Search.getMovieIdsBySearch(searchString, 10);

            return Response.status(200).entity(movies.toJson()).build();

        } catch (Exception e) { 
            return Response.status(400).entity("bad search query").build();
        }
    }

}