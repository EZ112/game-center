package com.example.gamecenterasg.Model;

import java.io.Serializable;
import java.util.Vector;

public class Games {
    private int gameID;
    private String gameName;
    private String gameDesc;
    private String gameGenre;
    private float gameRating;
    private int gameStock;
    private long gamePrice;
    private int gameImages;
    private Vector<String> MyGamesID;

    public Games(int gameID, String gameName, String gameDesc, String gameGenre, float gameRating, int gameStock, long gamePrice, int gameImages, Vector<String> myGamesID) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.gameDesc = gameDesc;
        this.gameGenre = gameGenre;
        this.gameRating = gameRating;
        this.gameStock = gameStock;
        this.gamePrice = gamePrice;
        this.gameImages = gameImages;
        MyGamesID = myGamesID;
    }

    public int getGameID() {
        return gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameDesc() {
        return gameDesc;
    }

    public String getGameGenre() {
        return gameGenre;
    }

    public float getGameRating() {
        return gameRating;
    }

    public int getGameStock() {
        return gameStock;
    }

    public long getGamePrice() {
        return gamePrice;
    }

    public int getGameImages() {
        return gameImages;
    }

    public Vector<String> getMyGamesID() {
        return MyGamesID;
    }
}
