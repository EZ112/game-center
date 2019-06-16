package com.example.gamecenterasg.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class MyGames {
    private int myGameID;
    private String playingHour;
    private String gameID;
    private String gameName;
    private String gameDesc;
    private String gameGenre;
    private float gameRating;
    private int gameStock;
    private long gamePrice;
    private int gameImages;
    private String userID;

    public MyGames(int myGameID, String playingHour, String gameID, String gameName, String gameDesc, String gameGenre, float gameRating, int gameStock, long gamePrice, int gameImages, String userID) {
        this.myGameID = myGameID;
        this.playingHour = playingHour;
        this.gameID = gameID;
        this.gameName = gameName;
        this.gameDesc = gameDesc;
        this.gameGenre = gameGenre;
        this.gameRating = gameRating;
        this.gameStock = gameStock;
        this.gamePrice = gamePrice;
        this.gameImages = gameImages;
        this.userID = userID;
    }

    public int getMyGameID() {
        return myGameID;
    }

    public void setMyGameID(int myGameID) {
        this.myGameID = myGameID;
    }

    public String getPlayingHour() {
        return playingHour;
    }

    public void setPlayingHour(String playingHour) {
        this.playingHour = playingHour;
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

    public String getGameDesc() {
        return gameDesc;
    }

    public void setGameDesc(String gameDesc) {
        this.gameDesc = gameDesc;
    }

    public String getGameGenre() {
        return gameGenre;
    }

    public void setGameGenre(String gameGenre) {
        this.gameGenre = gameGenre;
    }

    public float getGameRating() {
        return gameRating;
    }

    public void setGameRating(float gameRating) {
        this.gameRating = gameRating;
    }

    public int getGameStock() {
        return gameStock;
    }

    public void setGameStock(int gameStock) {
        this.gameStock = gameStock;
    }

    public long getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(long gamePrice) {
        this.gamePrice = gamePrice;
    }

    public int getGameImages() {
        return gameImages;
    }

    public void setGameImages(int gameImages) {
        this.gameImages = gameImages;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
