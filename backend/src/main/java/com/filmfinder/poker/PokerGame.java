package com.filmfinder.poker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.filmfinder.movie.Movie;

import org.eclipse.jetty.websocket.api.Session;

import javassist.NotFoundException;

public class PokerGame {
    private HashMap<Integer, Movie> proposed;
    private HashMap<Integer, PokerPlayer> players;
    private HashMap<Integer, Movie> votes;

    protected PokerGame() {
    }

    public ArrayList<Session> getSessions() {
        return new ArrayList<Session>();
    }

    public void addPlayer(int userId, String nickname, Session connection) {
        players.put(userId, new PokerPlayer(userId, nickname, connection));
    }

    public String getPlayersJson() {
        return "All players";
    }

    public void removePlayer(int userId) {
        players.remove(userId);
    }

    public String getNickJson() {
        for (PokerPlayer p: players.values()) {
            //TODO: 
        }
        return "hi";
    }

    public void addSelect(int userId, int movieId) throws NotFoundException, SQLException {

        // proposed.put();
    }

    public void removeSelect(int userId, int movieId) {
        // proposed.remove(rk);
    }

    /**
     * 
     * @return list of selections in json format
     */
    public String getSelectionsJson() {
        return "selectionJson";
    }

    public void addVotes(int userId, ArrayList<Integer> movieIds) {

    }

    public String resultJson() {
        return "Result json";
    }
}
