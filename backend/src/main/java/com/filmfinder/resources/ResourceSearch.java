package com.filmfinder.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import java.util.HashSet;
import java.util.Iterator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.filmfinder.movie.Movie;
import com.filmfinder.movie.Movies;
import com.filmfinder.templates.SearchTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javassist.NotFoundException;

import com.filmfinder.Search.Search;
import com.filmfinder.movie.Movie;
import com.filmfinder.templates.SearchTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Path("search/")
public class ResourceSearch {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
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