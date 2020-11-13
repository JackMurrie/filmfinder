package com.filmfinder.poker;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.jetty.websocket.api.Session;
import org.junit.Test;

public class TestPoker {
    @Test
    public void testPoker() {
        Session session = null;
        ArrayList<Integer> votes1 = new ArrayList<Integer>();
        ArrayList<Integer> votes2 = new ArrayList<Integer>();
        ArrayList<Integer> votes3 = new ArrayList<Integer>();
        votes1.add(2);
        votes1.add(5);
        votes1.add(6);
        votes2.add(5);
        votes2.add(6);
        votes2.add(2);
        votes3.add(5);

        int gameId = PokerManager.instantiate();
        try {
            PokerGame game = PokerManager.getGame(gameId);

            game.addPlayer("Fred", session);
            game.addPlayer("John", session);
            game.addPlayer("George", session);

            System.out.println(game.getPlayers());

            game.removePlayer("George");
            System.out.println(game.getPlayers());
            game.addPlayer("George", session);
            System.out.println(game.getPlayers());

            System.out.println(game.getSelectionProgress());

            game.addSelect("Fred", 2);
            game.addSelect("Fred", 5);
            game.addSelect("John", 6);
            game.addSelect("John", 2);
            game.finishSelect("Fred");

            System.out.println(game.getSelectionProgress());

            game.finishSelect("John");
            System.out.println(game.getSelectionProgress());

            System.out.println(game.getSelectedMovies());

            game.addVote("Fred", votes1);

            game.addVote("John", votes2);
            game.addVote("George", votes3);

            System.out.println(game.getResults().debugString());
            
            
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }


    }
}
