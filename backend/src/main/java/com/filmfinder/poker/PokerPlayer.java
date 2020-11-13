package com.filmfinder.poker;

import java.sql.SQLException;
import java.util.HashMap;

import com.filmfinder.movie.Movie;

import javassist.NotFoundException;

public class PokerPlayer {
    private int userId;
    private String nickname;
    private HashMap<Integer, Movie> proposed = new HashMap<Integer, Movie>();
    private boolean submittedProposal = false;
    private boolean submittedRanking = false;

    public PokerPlayer(int userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }

    public boolean isSubmittedRanking() {
        return submittedRanking;
    }

    public void setSubmittedRanking(boolean submittedRanking) {
        this.submittedRanking = submittedRanking;
    }

    public boolean isSubmittedProposal() {
        return submittedProposal;
    }

    public void setSubmittedProposal(boolean submittedProposal) {
        this.submittedProposal = submittedProposal;
    }

    public int getUserId() {
        return userId;
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

        return this.userId==nn.getUserId();
    }

    @Override
    public int hashCode() {
        return userId;
    }
}
