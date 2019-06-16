package com.example.gamecenterasg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gamecenterasg.Model.Games;
import com.example.gamecenterasg.Model.MyGames;
import com.example.gamecenterasg.Model.Users;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "game_center.db";
    private static final String USER_TABLE = "user_tbl";
    public static final String GAME_TABLE = "game_tbl";
    public static final String MY_GAME_TABLE = "my_game_tbl";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + USER_TABLE +
                "(userID TEXT PRIMARY KEY, userName TEXT, userEmail TEXT, userPass TEXT, userBirthday TEXT, userPhone TEXT, userGender TEXT, userBalance INTEGER, userStatus TEXT)";
        db.execSQL(query);
        query = "CREATE TABLE " + GAME_TABLE +
                "(gameID TEXT PRIMARY KEY, gameName TEXT, gamePrice INTEGER, gameStock INTEGER, gameRating REAL, gameGenre TEXT, gameDesc TEXT, gameImage INTEGER)";
        db.execSQL(query);
        query = "CREATE TABLE " + MY_GAME_TABLE +
                "(myGameId INTEGER PRIMARY KEY AUTOINCREMENT, playingHour TEXT, gameID TEXT, userID TEXT, FOREIGN KEY (gameID) REFERENCES "+MY_GAME_TABLE+" (gameID), FOREIGN KEY (userID) REFERENCES "+USER_TABLE+" (userID))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + USER_TABLE;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS " + GAME_TABLE;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS " + MY_GAME_TABLE;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean addUser(String userID, String userName, String userEmail, String userPass, String userBirthday, String userPhone, String userGender, int userBalance, String userStatus){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", userID);
        contentValues.put("userName", userName);
        contentValues.put("userEmail", userEmail);
        contentValues.put("userPass", userPass);
        contentValues.put("userBirthday", userBirthday);
        contentValues.put("userPhone", userPhone);
        contentValues.put("userGender", userGender);
        contentValues.put("userBalance", userBalance);
        contentValues.put("userStatus", userStatus);
        long result = db.insert(USER_TABLE,null,contentValues);
        db.close();
        return result == -1 ? false : true;
    }

    public boolean addGame(String gameID, String gameName, long gamePrice, int gameStock, double gameRating, String gameGenre, String gameDesc, int gameImages){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("gameID", gameID);
        contentValues.put("gameName", gameName);
        contentValues.put("gamePrice", gamePrice);
        contentValues.put("gameStock", gameStock);
        contentValues.put("gameRating", gameRating);
        contentValues.put("gameGenre", gameGenre);
        contentValues.put("gameDesc", gameDesc);
        contentValues.put("gameImage", gameImages);
        long result = db.insert(GAME_TABLE, null, contentValues);
        db.close();
        return result == -1 ? false : true;
    }

    public boolean addMyGame(String playingHour, String gameID, String userID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("playingHour", playingHour);
        contentValues.put("gameID", gameID);
        contentValues.put("userID", userID);
        long result = db.insert(MY_GAME_TABLE, null, contentValues);
        db.close();
        return result == -1 ? false : true;
    }

    public Users getUser(String userID){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM "+ USER_TABLE + " WHERE userID = '"+ userID +"'", null);
        Users user = null;
        if(cursor.moveToNext())
            user = new Users(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getString(8));
        cursor.close();
        return user;
    }

    public ArrayList<Games> viewGame(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ GAME_TABLE, null);
        ArrayList<Games> gamesList = new ArrayList<>();
        while (cursor.moveToNext())
            gamesList.add(new Games(cursor.getString(0), cursor.getString(1), cursor.getLong(2), cursor.getInt(3), cursor.getDouble(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7)));
        return gamesList;
    }

    public ArrayList<MyGames> viewMyGame(String userID){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT a.myGameId, a.playingHour, a.gameID, b.gameName, b.gameDesc, b.gameGenre, b.gameRating, b.gameStock, b.gamePrice, b.gameImage, a.userID FROM "+ MY_GAME_TABLE + " a JOIN "+ GAME_TABLE +" b ON a.gameID = b.gameID WHERE a.userID = '"+ userID +"'", null);
        ArrayList<MyGames> myGamesList = new ArrayList<>();
        while (cursor.moveToNext())
            myGamesList.add(new MyGames(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getFloat(6), cursor.getInt(7), cursor.getLong(8), cursor.getInt(9), cursor.getString(10)));
        return myGamesList;
    }

    public boolean updatePlayingHour(int myGameId, String playingHour){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("playingHour", playingHour);
        long result = db.update(MY_GAME_TABLE, contentValues, "myGameId="+myGameId, null);
        return  result == -1 ? false : true;
    }

    public String loginUser(String userEmail, String userPass){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ USER_TABLE +" WHERE userEmail = '"+ userEmail +"' AND userPass = '"+ userPass +"'", null);
        Users user = null;
        if(cursor.moveToNext())
            user = new Users(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getString(8));
        cursor.close();
        return user != null ? user.getUserID() : "";
    }
}
