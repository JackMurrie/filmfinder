package com.filmfinder.poker;

import java.util.HashMap;

import org.eclipse.jetty.websocket.api.Session;

import javassist.NotFoundException;

public class PokerManager {
    private static HashMap<Integer, PokerGame> games = new HashMap<Integer, PokerGame>();
    private static int currentGameId;

    public static int instantiate() {
        PokerGame pd = new PokerGame(currentGameId);
        games.put(currentGameId, pd);
        return currentGameId++;
    }

    public static PokerGame getGame(int gameId) throws NotFoundException {
        PokerGame game = games.get(gameId);
        if (game == null) {
            throw new NotFoundException("Game id doesn't exist");
        }
        return game;
    }

    public static void endGame(int gameId) {
        games.remove(gameId);
    }

    public static PlayerData getPlayerData(Session session) throws NotFoundException {
        for (PokerGame game: games.values()) {
            try {
                return game.getPlayerData(session);
            } catch (NotFoundException e) {

            }
        }
        throw new NotFoundException("Session cannot be found");
    }
}
