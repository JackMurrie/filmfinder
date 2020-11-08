package com.filmfinder.resources;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;

import com.filmfinder.movie.Movies;
import com.filmfinder.templates.SearchTemplate;

import javassist.NotFoundException;

import com.filmfinder.Search.Search;

@Path("search/")
public class ResourceSearch {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(SearchTemplate data) throws NotFoundException, SQLException {
        String searchString = data.getSearchString();
        try {
            Movies movies = Search.getMovieIdsBySearch(searchString, 10);

            return Response.status(200).entity(movies.toJson()).build();
        } catch (Exception e) { 
            return Response.status(400).entity("bad search query").build();
        }
    }

}