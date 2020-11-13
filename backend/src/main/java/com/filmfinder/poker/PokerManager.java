package com.filmfinder.poker;

import java.util.HashMap;

public class PokerManager {
    private static HashMap<Integer, PokerGame> games = new HashMap<Integer, PokerGame>();
    private static int currentGameId;

    public static int instantiate() {
        PokerGame pd = new PokerGame();
        games.put(currentGameId, pd);
        return currentGameId++;
    }

    public static PokerGame getGame(int gameId) {
        return games.get(gameId);
    }

    public static void endGame(int gameId) {
        games.remove(gameId);
    }
}
