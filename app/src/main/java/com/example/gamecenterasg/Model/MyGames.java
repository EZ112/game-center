package com.example.gamecenterasg.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class MyGames {
    private int myGameID;
    private String playingHour;
    private Games games;
    private Users users;

    public MyGames(int myGameID, String playingHour, Games games, Users users) {
        this.myGameID = myGameID;
        this.playingHour = playingHour;
        this.games = games;
        this.users = users;
    }

    public void setPlayingHour(String playingHour) {
        this.playingHour = playingHour;
    }

    public int getMyGameID() {
        return myGameID;
    }

    public String getPlayingHour() {
        return playingHour;
    }

    public Games getGames() {
        return games;
    }

    public Users getUsers() {
        return users;
    }
}
