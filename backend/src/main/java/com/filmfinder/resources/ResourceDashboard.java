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
import javax.ws.rs.DELETE;

import com.filmfinder.auth.CredentialHandler;
import com.filmfinder.blacklist.Blacklist;
import com.filmfinder.dashboard.Dashboard;
import com.filmfinder.movieLists.Wishlist;
import com.filmfinder.templates.MovieIdTemplate;
import com.filmfinder.templates.UserIdTemplate;
import com.filmfinder.movieLists.Watchlist;
import com.filmfinder.db.UtilDB;

@Path("user/")
public class ResourceDashboard {
    
    @CookieParam("auth_token")
    private String token;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserDashboard() {
        int userId;
        try {
            userId = UtilDB.getUserId(CredentialHandler.decodeToken(token));
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
    @Path("wishlist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToWishlist(MovieIdTemplate data) {

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

    @DELETE
    @Path("wishlist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeFromWishList(MovieIdTemplate data) {
        
        int movieId = data.getMovieId();
        int userId;
        String email = null;
        try {
            email = CredentialHandler.decodeToken(token);
            userId = UtilDB.getUserId(email);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }  

        try {
            Wishlist list = new Wishlist(userId);
            list.deleteMovie(movieId);
            return Response.status(200).entity("deleted from user wishlist.").build();
        } catch (Exception e) {
            return Response.status(400).entity("failed to delete from user wishlist").build();
        }
    }

    @POST
    @Path("watchedlist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToWatchedlist(MovieIdTemplate data) {

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
    @DELETE
    @Path("watchedlist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeFromWatchedList(MovieIdTemplate data) {
        
        int movieId = data.getMovieId();
        int userId;
        String email = null;
        try {
            email = CredentialHandler.decodeToken(token);
            userId = UtilDB.getUserId(email);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }  

        try {
            Watchlist list = new Watchlist(userId);
            list.deleteMovie(movieId);
            return Response.status(200).entity("deleted from user watched list.").build();
        } catch (Exception e) {
            return Response.status(400).entity("failed to delete from user watched list").build();
        }
    }

    @POST
    @Path("blacklist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToBlacklist(UserIdTemplate data) {

        int userId = data.getUserId();
        try {
            Blacklist bl = new Blacklist(userId);
            bl.add(userId);
            bl.refresh();
            return Response.status(200).entity("user blacklisted").build();
        } catch (Exception e) {
            return Response.status(400).entity("failed to blacklist user").build();
        }
    }

    @DELETE
    @Path("blacklist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeFromBlacklist(UserIdTemplate data) {

        int userId = data.getUserId();
        try {
            Blacklist bl = new Blacklist(userId);
            bl.remove(userId);
            bl.refresh();
            return Response.status(200).entity("user unblacklisted").build();
        } catch (Exception e) {
            return Response.status(400).entity("failed to unblacklist user").build();
        }
    }


}
