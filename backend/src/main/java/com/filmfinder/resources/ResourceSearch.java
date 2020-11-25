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
        int limit = data.getLimit();
        try {
            Movies movies = null;
            if (data.getTitle() != null) {
                movies = Search.getMovieIdsBySearch(data.getTitle(), limit);
            } else if (data.getDirector() != null) {
                movies = Search.searchDirectors(data.getDirector(), limit);
            } else if (data.getGenre() != null) {
                movies = Search.searchGenre(data.getGenre(), limit);
            }   
            return Response.status(200).entity(movies.toJson()).build();
        } catch (Exception e) { 
            // e.printStackTrace();
            return Response.status(400).entity("bad search query").build();
        }
    }

}