package com.filmfinder.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.filmfinder.movie.Movie;
import com.filmfinder.templates.SearchTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Path("search/")
public class ResourceSearch {

    @POST
    @Path("search")
    //@Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response search(String searchString) throws Exception {

        // get distinct words from search query
        String rawQuery = searchString;
        HashSet<String> words = new HashSet<String>();

        Pattern pattern_allWords = Pattern.compile("\\w+");

        Matcher matcher_allWords = pattern_allWords.matcher(rawQuery);

        int count = 0;
        while(matcher_allWords.find()) {
            words.add(rawQuery.substring(matcher_allWords.start(), matcher_allWords.end()));
            count++;
        }

        Pattern pattern_asOneWord = Pattern.compile("[\\\"\'].*[\\\"\']");

        Matcher matcher_asOneWord = pattern_asOneWord.matcher(rawQuery);

        count = 0;
        if (matcher_asOneWord.find()) {
            words.add(rawQuery.substring(matcher_asOneWord.start(), matcher_asOneWord.end()));
            count++;
        }

        Iterator<String> it = words.iterator();
        String[] results = new String[words.size()];

        count = 0;
        while(it.hasNext()) {
            results[count] = it.next();
            count++;
        }

        // get some movies here
        ArrayList<Movie> searchList = new ArrayList<Movie>();
        ArrayList<Integer> searchListId = new ArrayList<Integer>();

        for(int i = 0; i < 100; i++) {
            try {
                searchList.add(Movie.getMovie(i));
                searchListId.add(i);
            } catch (Exception e) {
                //..
            }
            
        }


        // look for matches here
        Iterator<Movie> movie_it = searchList.iterator();
        Iterator<Integer> id_it = searchListId.iterator();
        ArrayList<Integer> movieMatches = new ArrayList<Integer>();

        while(movie_it.hasNext()) {

            Movie movie = movie_it.next();
            int id = id_it.next();

            String title = movie.getName();
            String description = movie.getDescription();

            for (int i = 0; i < results.length; i++) {
                int titleHits = stringSearch(movie.getName(), results[i]);
                int descriptionHits = stringSearch(movie.getDescription(), results[i]);


                int genreHits = 0;
                ArrayList<String> genres = movie.getGenres();
                for(int j = 0; j < genres.size(); j++) {
                    genreHits += stringSearch(genres.get(j), results[i]);
                }

                if (titleHits > 0 || descriptionHits > 0 || genreHits > 0) {
                    movieMatches.add(id);
                    break;
                }
            }
        }
        JsonObject returnData = new JsonObject();
        Gson gson = new Gson();
        JsonArray jsonMatches = gson.toJsonTree(movieMatches).getAsJsonArray();
        returnData.add("matchingList", jsonMatches);
        
        return Response.status(200).entity(gson.toJson(returnData)).build();        
    }


    // bad character herustic boyes moore algorithm
    public int stringSearch(String target, String pattern) {

        int[] badChar = new int[16384];

        int m = pattern.length();
        int n = target.length();

        for (int i = 0; i < 16384; i++) {
            badChar[i] = -1;
        }

        for (int i = 0; i < pattern.length(); i++) {
            badChar[ (int) pattern.charAt(i) ] = i;
        }

        int hits = 0;
        int offset = 0;

        while (offset <= (n - m)) {
            int i = m - 1;
            
            while (i >= 0 && pattern.charAt((i)) == target.charAt(offset+i)) {
                
                i--;
            }

            if (i < 0) {
                hits++;

                if (offset + m < n) {
                    offset += m - badChar[target.charAt(offset + m)];
                } else {
                    offset++;
                }
            } else {
                if (badChar[target.charAt(offset + i)] <= 0) {
                    offset ++;
                    
                } else {
                    offset += badChar[target.charAt(offset + i)];

                }
            }
        }
        return hits;
    }

}