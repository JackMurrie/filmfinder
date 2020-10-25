package com.filmfinder.resources;


import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import com.filmfinder.auth.CredentialHandler;
import com.filmfinder.dashboard.Dashboard;
import com.filmfinder.movieLists.Wishlist;
import com.filmfinder.templates.MovieIdTemplate;
import com.filmfinder.movieLists.Watchlist;
import com.filmfinder.db.UtilDB;

@Path("users/")
public class ResourceDashboard {
    
    @CookieParam("auth_token")
    private String token;

    @GET
    @Path("{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserDashboard(@PathParam("user_id") int userId) {

        try {
            CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }
        
        try {
            Dashboard d = new Dashboard(userId);
            return Response.status(200).entity(d.toJson()).build();
        } catch (Exception e) {
            return Response.status(400).entity("Could not get dashboard data\n").build();
        }
    }

    @POST
    @Path("add_wishlist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addWishlist(MovieIdTemplate data) {

        String email = null;
        int movieId = data.getMovieId();
        int userId;
        try {
            email = CredentialHandler.decodeToken(token);
            userId = UtilDB.getUserId(email);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }  

        try {
            Wishlist list = new Wishlist(userId);
            list.addMovie(movieId);
            return Response.status(200).entity("added to wishlist.").build();
        } catch (Exception e) {
            return Response.status(400).entity("failed to add to wishlist").build();
        }
    }

    @POST
    @Path("add_watchedlist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addWatchedlist(MovieIdTemplate data) {

        String email = null;
        int movieId = data.getMovieId();
        int userId;
        try {
            email = CredentialHandler.decodeToken(token);
            userId = UtilDB.getUserId(email);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }  

        try {
            Watchlist list = new Watchlist(userId);
            list.addMovie(movieId);
            return Response.status(200).entity("added to watch history.").build();
        } catch (Exception e) {
            return Response.status(400).entity("failed to add to watch history").build();
        }
    }
}
