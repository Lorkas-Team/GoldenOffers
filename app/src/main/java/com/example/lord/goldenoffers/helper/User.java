package com.example.lord.goldenoffers.helper;

public class User {

    private int dbID;
    //id from servers database, NOT from sqlite
    private String name;
    private String email;

    public User() {
        dbID = -1;
        name = "";
        email = "";
    }

    public User(int id, String username, String email) {
        this.dbID = id;
        this.name = username;
        this.email = email;
    }

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int id) {
        this.dbID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return dbID + " : " + name + " (" + email + ")";
    }
}
