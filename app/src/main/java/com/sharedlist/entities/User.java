package com.sharedlist.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    String mail; // id (, instead of .)
    String password;
    String displyName;
    Boolean stayConnected;
    String joinDate;
    Map<String, ItemsList> sharedList = new HashMap<>();  // list name (or category) and the list

    public User() {
    }

    public User(String _mail, String _password, String _displyName, Boolean _stayConnected, String _joinDate) {
        this.mail = _mail;
        this.password = _password;
        this.displyName = _displyName;
        this.stayConnected = _stayConnected;
        this.joinDate = _joinDate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplyName() {
        return displyName;
    }

    public void setDisplyName(String displyName) {
        this.displyName = displyName;
    }

    public Boolean getStayConnected() {
        return stayConnected;
    }

    public void setStayConnected(Boolean stayConnected) {
        this.stayConnected = stayConnected;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public Map<String, ItemsList> getSharedList() {
        return sharedList;
    }

    public void setSharedList(Map<String, ItemsList> sharedList) {
        this.sharedList = sharedList;
    }
}
