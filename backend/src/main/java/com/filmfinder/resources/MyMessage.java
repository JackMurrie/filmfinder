package com.filmfinder.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.filmfinder.db.MysqlConnector;

@Path("msg")
public class MyMessage {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {

        MysqlConnector c = new MysqlConnector();

        try {
            c.readDb();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return "This is good\n";
    }
}