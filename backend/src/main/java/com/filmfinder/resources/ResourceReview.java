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

import java.sql.SQLException;
import javassist.NotFoundException;

import com.filmfinder.auth.CredentialHandler;
import com.filmfinder.db.ReviewDB;
import com.filmfinder.templates.ReviewTemplate;

@Path("review/{movie_id}")
public class ResourceReview {

    @CookieParam("auth_token")
    private String token;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReview(@PathParam("movie_id") int movieId, ReviewTemplate review) {

        String email = null;
        try {
            email = CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token.").build();
        }
        
        String comment = review.getComment();
        try {
            if (!ReviewDB.exists(email, movieId)) {
                ReviewDB.postReview(email, movieId, comment);
                return Response.status(200).entity("Review added").build();     
            } else {
                return Response.status(400).entity("Review already exists").build();     
            }
        } catch (Exception e) {
            String msg = "Failed to add review";
            if (e.getClass() == SQLException.class) {
                msg = "SQL error";
            } else if (e.getClass() == NotFoundException.class) {
                msg = "Not found error";
            }
            return Response.status(400).entity(msg).build();        
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editReview(@PathParam("movie_id") int movieId,  ReviewTemplate review) {

        String email = null;
        try {
            email = CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token.").build();
        }

        String comment = review.getComment();
        
        try {
            if (ReviewDB.exists(email, movieId)) {
                ReviewDB.updateReview(email, movieId, comment);
                return Response.status(200).entity("Review edited").build();     
            } else {
                return Response.status(400).entity("Review does not exist").build();     
            }
        } catch (Exception e) {
            String msg = "Failed to edit review";
            if (e.getClass() == SQLException.class) {
                msg = "SQL error";
            } else if (e.getClass() == NotFoundException.class) {
                msg = "Not found error";
            }
            return Response.status(400).entity(msg).build();       
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeReview( @PathParam("movie_id") int movieId, ReviewTemplate review) {
        String email = null;
        try {
            email = CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token.").build();
        }
        try {
            if (ReviewDB.exists(email, movieId)) {
                ReviewDB.removeReview(email, movieId);
                return Response.status(200).entity("Review removed.").build();
            } else {
                return Response.status(400).entity("Review does not exist").build();     
            }
        } catch (Exception e) {
            String msg = "Failed to remove review";
            if (e.getClass() == SQLException.class) {
                msg = "SQL error";
            } else if (e.getClass() == NotFoundException.class) {
                msg = "Not found error";
            }
            return Response.status(400).entity(msg).build();         
        }        
    }

}