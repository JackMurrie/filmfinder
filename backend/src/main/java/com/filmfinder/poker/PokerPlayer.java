package com.filmfinder.poker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.filmfinder.movie.Movie;

import org.eclipse.jetty.websocket.api.Session;

import javassist.NotFoundException;

public class PokerPlayer {
    private String nickname;
    private HashMap<Integer, Movie> proposed = new HashMap<Integer, Movie>();
    private boolean finishedSelection = false;
    private boolean submittedRanking = false;
    private Session websocketConnection;
    private ArrayList<Integer> votes = new ArrayList<Integer>();

    public PokerPlayer(String nickname, Session connection) {
        this.nickname = nickname;
        this.setWebsocketConnection(connection);
    }

    public ArrayList<Integer> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Integer> votes) throws NotFoundException, SQLException {
        this.votes = votes;
    }

    public boolean isFinishedSelection() {
        return finishedSelection;
    }

    public void setFinishedSelection(boolean finishedSelection) {
        this.finishedSelection = finishedSelection;
    }

    public Session getWebsocketConnection() {
        return websocketConnection;
    }

    public void setWebsocketConnection(Session websocketConnection) {
        this.websocketConnection = websocketConnection;
    }

    public boolean isSubmittedRanking() {
        return submittedRanking;
    }

    public void setSubmittedRanking(boolean submittedRanking) {
        this.submittedRanking = submittedRanking;
    }

    public String getNickname() {
        return nickname;
    }

    public void addProposed(int movieId) throws NotFoundException, SQLException {
        proposed.put(movieId, Movie.getMovie(movieId));
    }

    public void removeProposed(int movieId) {
        proposed.remove(movieId);
    }

    public HashMap<Integer, Movie> getProposed() {
        return proposed;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        PokerPlayer nn = (PokerPlayer) obj;

        return this.nickname==nn.getNickname();
    }

    @Override
    public int hashCode() {
        return nickname.hashCode();
    }

}
