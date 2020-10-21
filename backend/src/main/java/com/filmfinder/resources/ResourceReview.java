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
import com.filmfinder.templates.ReviewTemplate;

@Path("movies/{movieId}")
public class ResourceReview {

    @CookieParam("auth_token")
    private String token;

    @POST
    @Path("/review")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReview(@PathParam("movieId") int id, ReviewTemplate review) throws Exception {
        String userEmail = null;
        try {
            userEmail = CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token.").build();
        }

        String comment = review.getComment();
        float star = review.getStar();
        try {
            ReviewDB.putReview(userEmail, id, comment, star);
            return Response.status(200).entity("Review successfully written.").build();  
        } catch (Exception e) {
            return Response.status(400).entity("Review was not written.").build();  
        }
              
    }

    @PUT
    @Path("/review")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editReview(@PathParam("movieId") int id,  ReviewTemplate review) throws Exception {

        String userEmail = null;
        try {
            userEmail = CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token.").build();
        }

        String comment = review.getComment();
        float star = review.getStar();
        try {
            ReviewDB.editReview(userEmail, id, comment, star);
            return Response.status(200).entity("Review edited.").build();        
        } catch (Exception e) {
            return Response.status(400).entity("Review was not edited.").build();
        }
    }

    @DELETE
    @Path("/review")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeReview( @PathParam("movie_id") int id, ReviewTemplate review) throws Exception {
        
        String userEmail = null;
        try {
            userEmail = CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token.").build();
        }

        // int reviewId = review.getReviewId();
        try {
            ReviewDB.removeReview(userEmail, id);
            return Response.status(200).entity("Review was removed.").build();        
        } catch (Exception e) {
            return Response.status(400).entity("Review was not removed.").build();        
        }        
    }

}