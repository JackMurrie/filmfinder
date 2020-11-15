package com.filmfinder.templates;
import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class EndFilmpokerTemplate {
    
    private int gameId;

    public int getGameId() {
        return this.gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
