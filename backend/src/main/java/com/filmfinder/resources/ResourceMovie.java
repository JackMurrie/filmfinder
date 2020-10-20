package com.filmfinder.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.filmfinder.templates.AuthTemplate;

@Path("movies/{movie_id}/")
public class ResourceMovie {

    @POST
    @Path("review")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(AuthTemplate data) throws Exception {
        String first = data.getFirstName();
        String last = data.getLastName();
        String email = data.getEmail();
        String password = data.getPassword();
        System.out.println("first="+first+", last="+last+", email="+email+", password="+ password);

        String token = CredentialHandler.authenticate(first, last, email, password);
        if (token == null) {
            // throw exception.
            return Response.status(400).entity("Register unsuccessful\n").build();
        } else {
            return Response.status(200).entity("Register successful\n").build();
        }
    }

    private Movie findSafely(long movieId) {
        // return movieDAO.findById(personId).orElseThrow(() -> new NotFoundException("No such user."));
        return new NotFoundException("No such movie."));
    }

}