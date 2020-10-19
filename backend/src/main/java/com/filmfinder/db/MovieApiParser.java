package com.filmfinder.db;

import com.filmfinder.app.MovieData;
import com.filmfinder.db.UrlConnector;
import java.util.Map;

public class MovieApiParser {
    // DO NOT COMMIT 
    String ApiKey = "70ae629f88a806e8758ac3900483833e";

    UrlConnector connector = new UrlConnector();
    
    // gets a movie by id from the site
    public MovieData getMovie(int id) throws Exception {
    
        try {
            return parseMovie( connector.getJsonEntries("https://api.themoviedb.org/3/movie/" + id + " ?api_key=" + ApiKey + "&language=en-US") );
        
        }
        finally {

        }
        
    }

    MovieData parseMovie(Map entries) {
        
        // 
        return new MovieData((String)entries.get("title"), (String)entries.get("overview"), "", (String)entries.get("poster_path"));

    }

}
