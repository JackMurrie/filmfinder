package com.filmfinder.movie;

import java.util.Map;
import com.filmfinder.util.UrlConnector;

public class MovieApiParser {
    // DO NOT COMMIT 
    String ApiKey = "70ae629f88a806e8758ac3900483833e";

    UrlConnector connector = new UrlConnector();
    
    // gets a movie by id from the site
    public MovieData getMovie(int id) throws Exception {
    
        try {
            Map data = connector.getJsonEntries("https://api.themoviedb.org/3/movie/" + id + " ?api_key=" + ApiKey + "&language=en-US");
            // System.out.println(data.toString());
            System.out.println(data.get("genres"));
            return parseMovie( data );
        
        }
        finally {

        }
        
    }

    public MovieData parseMovie(Map entries) {
        
        // 
        return new MovieData((String)entries.get("title"), (String)entries.get("overview"), "", (String)entries.get("poster_path"));

    }

}
