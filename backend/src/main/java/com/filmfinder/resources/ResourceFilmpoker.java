package com.filmfinder.resources;

import com.filmfinder.templates.JoinFilmpokerTemplate;

@Path("filmpoker/")
public class ResourceFilmPoker {
    
    @CookieParam("auth_token")
    private String token;

    @POST
    @Path("create_game/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGameId() {
        /* if (data.gameId exists)
            some error
            else 
            put gameId into thing
        */
    }

    @POST
    @Path("join_game/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response joinGameWithId(JoinFilmpokerTemplate data) {
        int gameId = data.getGameId();

        /* if (data.gameId exists)

        */
    }
}
