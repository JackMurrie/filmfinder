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
import com.filmfinder.poker.PlayerData;
import com.filmfinder.poker.PokerManager;

@WebSocket
public class WebSocketFilmPoker {

    private Gson gson = new Gson();

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
        
        String response = null;
        PokerGame pg = null;
        int command = -1;
        try {
            command = data.get("command").getAsInt();
        } catch (Exception e) {
            response = getBoolResponse(-1, false);
        }

        if (command == Data.JOINGAME) {
            try {
                int gameId = data.get("gameId").getAsInt();
                pg = PokerManager.getGame(gameId);
                pg.addPlayer(data.get("nickname").getAsString(), session);
                response = getBoolResponse(command, true);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                response = getBoolResponse(command, false);
            }
            
            session.getRemote().sendString(response);
            return;
        }

        PlayerData pd = null;
        int gameId = -1;

        try {
            pd = PokerManager.getPlayerData(session);
            gameId = pd.getGameId();
            pg = PokerManager.getGame(gameId);
        } catch (NotFoundException e) {
            response = getBoolResponse(command, false);
            session.getRemote().sendString(response);
            return;
        }

        boolean sendAll = false;
        boolean closeSession = false;
        switch (command) {
            case Data.GETNICK:
                response = pg.getSelectionProgress().toJson();
                break;
            case Data.ADDSELECT:
                try {
                    pg.addSelect(pd.getNickname(), data.get("selectedMovie").getAsInt());
                    response = getBoolResponse(command, true);
                } catch (Exception e) {
                    response = getBoolResponse(command, false);
                }
                break;
            case Data.REMOVESELECT:
                pg.removeSelect(pd.getNickname(), data.get("selectedMovie").getAsInt());
                response = getBoolResponse(command, true);
                break;
            case Data.GETSELECTED:
                response = pg.getSelectedMovies().toJson();
                break;
            case Data.VOTE:
                try {
                    JsonArray jarray = data.get("votes").getAsJsonArray();
                    ArrayList<Integer> votes = new ArrayList<Integer>();
                    for (JsonElement je: jarray) {
                        JsonObject jo = (JsonObject) je;
                        votes.add(jo.get("movieId").getAsInt());
                    }
                    if (!pg.addVote(pd.getNickname(), votes)) {
                        response = getBoolResponse(command, true);
                    } else {
                        response = "{command:"+command+", isSuccess:"+true+", isLast: " +true+"}";
                        sendAll = true;
                    }
                } catch (Exception e) {
                    response = getBoolResponse(command, false);
                }
                break;
            case Data.RESULTS:
                try {
                    response = pg.getResults().toJson();
                    sendAll = true;
                    closeSession = true;
                } catch (Exception e) {
                    response = getBoolResponse(command, false);
                    System.out.println(e.getMessage());
                }
                break;
            case Data.DONESELECT:
                pg.finishSelect(pd.getNickname());
                response = "{command:"+command+", "+"nickname: " +pd.getNickname()+"}";

                sendAll = true;
                break;
        }
        if (sendAll) {
            ArrayList<Session> sessions = pg.getSessions();
            sendAll(sessions, response);
        } else {
            if (session.isOpen()) {
                session.getRemote().sendString(response);
            }
        }
        if (closeSession) {
            session.close(101, "Filmpoker ended successfully");
        }
    }   

    private String getBoolResponse(int commandId, boolean isSuccess) {
        return "{command:"+commandId+", isSuccess:"+isSuccess+"}";
    }

    private void sendAll(ArrayList<Session> sessions, String response) throws IOException {
        for (Session s: sessions) {
            if (s.isOpen()) {
                s.getRemote().sendString(response);
            }
        }
    }
}
