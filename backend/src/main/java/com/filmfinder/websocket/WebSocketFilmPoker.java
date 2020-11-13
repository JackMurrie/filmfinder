package com.filmfinder.websocket;

import java.util.ArrayList;
import java.io.IOException;
import javassist.NotFoundException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;

import com.filmfinder.poker.PokerGame;
import com.filmfinder.poker.PokerManager;

@WebSocket
public class WebSocketFilmPoker {

    private Gson gson = new Gson();
    private int gameId = -1;

    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println(session.getRemoteAddress().getHostString() + " connected!");
        // sessions.put(gameId, session);
    }
 
    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        System.out.println(session.getRemoteAddress().getHostString() + " closed!");
    }

    @OnWebSocketMessage
    public void onText(Session session, String message) throws IOException {
        System.out.println("Message received:" + message);
        
        JsonObject data = gson.fromJson(message, JsonObject.class);
        
        if (data.get("command").getAsInt() != Data.JOINGAME && gameId == -1) {

        }
        String response = null;
        PokerGame pg = null;

        if (data.get("command").getAsInt() == Data.JOINGAME) {
            int gameId = data.get("gameId").getAsInt();
            pg = PokerManager.getGame(gameId);
            if (pg != null) {
                try {
                    pg.addPlayer(data.get("nickname").getAsString(), session);
                    this.gameId = gameId;
                    response = getBoolResponse(data.get("command").getAsInt(), true);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    response = getBoolResponse(data.get("command").getAsInt(), false);
                }
            } else {
                // game doesn't exist.
            }
            // send message and return.
            session.getRemote().sendString(response);
            return;
        }

        pg = PokerManager.getGame(this.gameId);
        switch (data.get("command").getAsInt()) {
            case Data.GETNICK:
                response = pg.getPlayers().toJson();
                break;
            case Data.ADDSELECT:
                try {
                    pg.addSelect(data.get("nickname").getAsString(), data.get("selectedMovie").getAsInt());
                    response = getBoolResponse(data.get("command").getAsInt(), true);
                } catch (Exception e) {
                    response = getBoolResponse(data.get("command").getAsInt(), false);
                }
                break;
            case Data.REMOVESELECT:
                pg.removeSelect(data.get("nickname").getAsString(), data.get("selectedMovie").getAsInt());
                response = getBoolResponse(data.get("command").getAsInt(), true);
                break;
            case Data.GETSELECTED:
                response = pg.getSelectionProgress().toJson();
                break;
            case Data.VOTE:
                JsonArray jarray = data.get("votes").getAsJsonArray();
                ArrayList<Integer> votes = new ArrayList<Integer>();
                for (JsonElement je: jarray) {
                    JsonObject jo = (JsonObject) je;
                    votes.add(jo.get("movieId").getAsInt());
                }
                try { 
                    if (!pg.addVote(data.get("nickname").getAsString(), votes)) {
                        response = getBoolResponse(data.get("command").getAsInt(), true);
                    } else {
                        response = "{command:"+Data.VOTE+", isSuccess:"+true+", isLast: " +true+"}";
                    }
                } catch (Exception e) {
                    response = getBoolResponse(data.get("command").getAsInt(), false);
                }
                break;
            case Data.RESULTS:
                try {
                    response = pg.getResults().toJson();
                } catch (Exception e) {
                    response = getBoolResponse(data.get("command").getAsInt(), false);
                }
                break;
            case Data.DONESELECT:
                pg.finishSelect(data.get("nickname").getAsString());
                response = getBoolResponse(data.get("command").getAsInt(), true);
                break;
        }

        if (session.isOpen()) {
            session.getRemote().sendString(response);
        }
    }   

    private String getBoolResponse(int commandId, boolean isSuccess) {
        return "{command:"+commandId+", isSuccess:"+isSuccess+"}";
    }

}
