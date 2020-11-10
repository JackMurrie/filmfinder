package com.filmfinder.resources;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.filmfinder.templates.MovieLimitTemplate;
import com.filmfinder.recommender.Recommender;
import com.filmfinder.movie.Movies;

@Path("popular/")
public class ResourceGenericRecommendation {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenericRecommendations(MovieLimitTemplate data) {
        
        int limit = data.getLimit();
        try {
            Movies movies = Recommender.getGenericRecommendations(limit);
            return Response.status(200).entity(movies.toJson()).build();
        } catch (Exception e) {
            return Response.status(400).entity("Error getting generic recommendations").build();
        }

    }
}
