package com.filmfinder.resources;

import com.filmfinder.templates.EndFilmpokerTemplate;
import com.filmfinder.poker.PokerManager;

@Path("filmpoker/")
public class ResourceFilmPoker {
    
    @CookieParam("auth_token")
    private String token;

    @POST
    @Path("create_game/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGameId() {

        int userId;
        try {
            userId = UtilDB.getUserId(CredentialHandler.decodeToken(token));
        } catch (Exception e) {
            return Response.status(400).entity("invalid token").build();
        }

        try {
            int gameId = PokerManager.instantiate();
            String message = "{gameId: "+gameId+"}";
            return Response.status(200).entity(gameId).build();
        } catch (Exception e) {
            return Response.status(400).entity("unknown error").build();
        }
    }

    @POST
    @Path("end_game/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response joinGameWithId(EndFilmpokerTemplate data) {
        int gameId = data.getGameId();
        try {
            PokerManager.endGame(gameId);
            return Response.status(200).entity("Ended successfully").build();
        } catch (Exception e) {
            return Response.status(400).entity("unknown error").build();
        }
    }
}
