package com.filmfinder.poker;

public class PlayerData {
    private int gameId;
    private String nickname;

    public int getGameId() {
        return gameId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public PlayerData(int gameId, String nickname) {
        this.gameId = gameId;
        this.setNickname(nickname);
    }
}
