package com.filmfinder.poker;

import java.util.ArrayList;
import java.util.HashMap;

import com.filmfinder.movie.Movie;
import com.filmfinder.movie.Movies;

public class PokerData {
    private HashMap<ReviewKey, Movie> proposed;
    private HashMap<Integer, String> nicknames;
    private HashMap<Integer, Movie> votes;

    public PokerData(){}

    public void addPlayer(int userId, String nickname) {
        nicknames.put(userId, nickname);
    }

    public void removePlayer(int userId) {
        nicknames.remove(userId);
    }

    public String getPickedData(){
        return "hi";
    }

    public void addPick(int userId, int movieId) {

    }

    public void r
}
