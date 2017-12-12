package com.example.lord.goldenoffers.user;

public class User {

    private int dbID; //id from servers database, NOT from sqlite
    private String username;
    private String email;

    public User() {
        dbID = -1;
        username = "";
        email = "";
    }

    public User(int id, String username, String email) {
        this.dbID = id;
        this.username = username;
        this.email = email;
    }

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int id) {
        this.dbID = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return dbID + " : " + username + " (" + email + ")";
    }
}
