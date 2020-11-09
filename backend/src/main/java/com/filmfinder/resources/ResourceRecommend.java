package com.filmfinder.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;

import javax.ws.rs.CookieParam;
import com.filmfinder.db.UtilDB;
import com.filmfinder.movie.Movies;
import com.filmfinder.recommender.Recommender;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.filmfinder.auth.CredentialHandler;

@Path("recommend/")
public class ResourceRecommend {

    @CookieParam("auth_token")
    private String token;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response recommend() throws Exception {
        int userId = 1;
        try {
            //userId = UtilDB.getUserId(CredentialHandler.decodeToken(token));
            Movies movies = Recommender.getRecommendedMovies(userId, 10);
            return Response.status(200).entity(movies.toJson()).build();
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }

        // ArrayList<Integer> movieIds = Recommender.getRecommendedMovies(userId, 5);
        // JsonObject returnData = new JsonObject();
        // Gson gson = new Gson();
        // JsonArray jsonMatches = gson.toJsonTree(movieIds).getAsJsonArray();
        // returnData.add("recommended", jsonMatches);
        // return Response.status(200).entity(movies.toJson()).build();
    }
}
