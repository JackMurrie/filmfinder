package com.filmfinder.servlet;

import javax.servlet.annotation.WebServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import com.filmfinder.websocket.WebSocketFilmPoker;

public class FilmPokerWebSocketServlet extends WebSocketServlet {

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(WebSocketFilmPoker.class);
    }  
}
