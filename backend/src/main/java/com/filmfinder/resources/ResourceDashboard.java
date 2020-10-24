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
import com.filmfinder.db.UtilDB;

@Path("users/")
public class ResourceDashboard {
    
    @CookieParam("auth_token")
    private String token;

    @GET
    @Path("{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserDashboard(@PathParam("user_id") int id) {

        try {
            CredentialHandler.decodeToken(token);
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }    

        try {
            Dashboard d = new Dashboard(id);
            return Response.status(200).entity(d.toJson()).build();
        } catch (Exception e) {
            return Response.status(400).entity("Could not get dashboard data\n").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addWishlist() {

        String email = null;
        int userId;
        try {
            email = CredentialHandler.decodeToken(token);
            userId = UtilDB.getUserId(email);

        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }  
        
        Watchlist list = new Watchlist(userId)
        
    }


}
