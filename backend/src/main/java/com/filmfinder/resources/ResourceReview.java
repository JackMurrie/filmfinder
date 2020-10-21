package com.filmfinder.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

<<<<<<< HEAD
=======
import com.filmfinder.db.ReviewDB;
>>>>>>> 0a566aca018c9efc8a21c2c462cc07b9b1b394f7
import com.filmfinder.templates.ReviewTemplate;

import com.google.gson.Gson;


@Path("movies/{movieId}")
public class ResourceReview {

    @POST
    @Path("/review")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReview(@PathParam("movieId") int id, ReviewTemplate review) throws Exception {

        // String token = review.getToken();
        String comment = review.getComment();
        float star = review.getStar();

<<<<<<< HEAD
        // DB.putReview(userEmail, comment, star);
=======
        String userEmail;
        try {
            ReviewDB.putReview(userEmail, id, comment, star);
        } catch (Exception e) {

        }
>>>>>>> 0a566aca018c9efc8a21c2c462cc07b9b1b394f7
        return Response.status(200).entity("todo").build();        
    }

    @PUT
    @Path("/review")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editReview(@PathParam("movieId") int id,  ReviewTemplate review) throws Exception {
        // String token = review.getToken();
        String comment = review.getComment();
        float star = review.getStar();
<<<<<<< HEAD
        String reviewId = review.getReviewId();

        // DB.setReview(userEmail, comment, star);
=======
        // String reviewId = review.getReviewId();

        String userEmail;
        // String userEmail = CredentialHandler.decodeToken(token);
        
        try {
            ReviewDB.editReview(userEmail, id, comment, star);
        } catch (Exception e) {

        }
>>>>>>> 0a566aca018c9efc8a21c2c462cc07b9b1b394f7
        return Response.status(200).entity("todo").build();        
    }

    @DELETE
    @Path("/review")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeReview( @PathParam("movie_id") int id, ReviewTemplate review) throws Exception {
        
<<<<<<< HEAD
        String reviewId = review.getReviewId();
        // String userEmail = CredentialHandler.decodeToken(token);
        // DB.removeReview(userEmail, movieId);
=======
        int reviewId = review.getReviewId();
        String userEmail;
        // String userEmail = CredentialHandler.decodeToken(token);
        try {
            ReviewDB.removeReview(userEmail, id);
        } catch (Exception e) {

        }
>>>>>>> 0a566aca018c9efc8a21c2c462cc07b9b1b394f7
        return Response.status(200).entity("todo").build();        
    }

}