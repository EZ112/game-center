package com.example.gamecenterasg.Model;

import java.io.Serializable;

public class Users {

    private String userID;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userBirthday;
    private String userPhone;
    private String userGender;
    private int userBalance;
    private String userStatus;

    public Users(String userID, String userName, String userEmail, String userPassword, String userBirthday, String userPhone, String userGender, int userBalance, String userStatus) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userBirthday = userBirthday;
        this.userPhone = userPhone;
        this.userGender = userGender;
        this.userBalance = userBalance;
        this.userStatus = userStatus;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserGender() {
        return userGender;
    }

    public int getUserBalance() {
        return userBalance;
    }

    public String getUserStatus() {
        return userStatus;
    }
}
