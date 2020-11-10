package com.filmfinder.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.filmfinder.auth.CredentialHandler;
import com.filmfinder.db.ReviewDB;
import com.filmfinder.templates.RatingTemplate;

@Path("rating/{movie_id}")
public class ResourceRating {

    @CookieParam("auth_token")
    private String token;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReview(@PathParam("movie_id") int movieId, RatingTemplate rating) {

        String email = null;
        try {
            email = CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token.").build();
        }
        
        float star = rating.getRating();
        try {
            if (!ReviewDB.exists(email, movieId)) {
                ReviewDB.postRating(email, movieId, star);
                return Response.status(200).entity("Star rating added").build();     
            } else {
                return Response.status(400).entity("Star rating already exists").build();     
            }
        } catch (Exception e) {
            return Response.status(400).entity("Failed to add star rating").build();        
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editReview(@PathParam("movie_id") int movieId,  RatingTemplate rating) {

        String email = null;
        try {
            email = CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token.").build();
        }

        float star = rating.getRating();
        
        try {
            if (ReviewDB.exists(email, movieId)) {
                ReviewDB.updateRating(email, movieId, star);
                return Response.status(200).entity("Star rating edited").build();     
            } else {
                return Response.status(400).entity("Star rating does not exist").build();     
            }
        } catch (Exception e) {
            return Response.status(400).entity("Failed to edit star rating").build();     
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeReview( @PathParam("movie_id") int movieId, RatingTemplate rating) {
        String email = null;
        try {
            email = CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token.").build();
        }

        try {
            if (ReviewDB.exists(email, movieId)) {
                ReviewDB.removeReview(email, movieId);
                return Response.status(200).entity("Star rating removed.").build();
            } else {
                return Response.status(400).entity("Star rating does not exist").build();     
            }
        } catch (Exception e) {
            return Response.status(400).entity("Failed to remove star rating").build();     
        }        
    }

}