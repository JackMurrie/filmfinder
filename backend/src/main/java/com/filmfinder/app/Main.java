package com.filmfinder.app;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;

import com.filmfinder.servlet.FilmPokerWebSocketServlet;

public class Main {

    public static void main(String[] args) {

        Server server = new Server(8080);
        ServletContextHandler srv = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        
        srv.setContextPath("/");
        ServletHolder serHolRest = srv.addServlet(ServletContainer.class, "/rest/*");

        serHolRest.setInitOrder(1);
        serHolRest.setInitParameter("jersey.config.server.provider.packages", "com.filmfinder.resources");
        srv.addServlet(FilmPokerWebSocketServlet.class, "/filmpoker");

        server.setHandler(srv);
        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            server.destroy();
        }
    }

    
}

