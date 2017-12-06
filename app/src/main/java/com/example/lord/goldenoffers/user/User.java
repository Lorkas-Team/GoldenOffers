package com.example.lord.goldenoffers.user;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String email;
    private List<Desire> desires;

    public User() {
        username = "";
        email = "";
        desires = new ArrayList<>();
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.desires = new ArrayList<>();
    }

    public User(String username, String email, List<Desire> desires) {
        this.username = username;
        this.email = email;
        this.desires = desires;
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

    public List<Desire> getDesires() {
        return desires;
    }

    public void setDesires(List<Desire> desires) {
        this.desires = desires;
    }
}
