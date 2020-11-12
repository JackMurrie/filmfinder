package com.filmfinder.websocket;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;

@WebSocket
public class WebSocketFilmPoker {
    @OnWebSocketMessage
    public void onText(Session session, String message) throws IOException {
        System.out.println("Message received:" + message);
        if (session.isOpen()) {
            String response = message.toUpperCase();
            session.getRemote().sendString(response);
        }
    }
 
    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println(session.getRemoteAddress().getHostString() + " connected!");
    }
 
    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        System.out.println(session.getRemoteAddress().getHostString() + " closed!");
    }
}
