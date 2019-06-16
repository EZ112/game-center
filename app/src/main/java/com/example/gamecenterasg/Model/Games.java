package com.example.gamecenterasg.Model;

import java.io.Serializable;
import java.util.Vector;

public class Games {
    private String gameID;
    private String gameName;
    private long gamePrice;
    private int gameStock;
    private double gameRating;
    private String gameGenre;
    private String gameDesc;
    private int gameImages;

    public Games(String gameID, String gameName, long gamePrice, int gameStock, double gameRating, String gameGenre, String gameDesc, int gameImages) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.gamePrice = gamePrice;
        this.gameStock = gameStock;
        this.gameRating = gameRating;
        this.gameGenre = gameGenre;
        this.gameDesc = gameDesc;
        this.gameImages = gameImages;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public long getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(long gamePrice) {
        this.gamePrice = gamePrice;
    }

    public int getGameStock() {
        return gameStock;
    }

    public void setGameStock(int gameStock) {
        this.gameStock = gameStock;
    }

    public double getGameRating() {
        return gameRating;
    }

    public void setGameRating(double gameRating) {
        this.gameRating = gameRating;
    }

    public String getGameGenre() {
        return gameGenre;
    }

    public void setGameGenre(String gameGenre) {
        this.gameGenre = gameGenre;
    }

    public String getGameDesc() {
        return gameDesc;
    }

    public void setGameDesc(String gameDesc) {
        this.gameDesc = gameDesc;
    }

    public int getGameImages() {
        return gameImages;
    }

    public void setGameImages(int gameImages) {
        this.gameImages = gameImages;
    }
}
