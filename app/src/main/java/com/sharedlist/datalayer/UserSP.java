package com.sharedlist.datalayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.sharedlist.entities.User;

import org.jetbrains.annotations.NotNull;

public class UserSP {

    private static UserSP INSTANCE = null;

    volatile SharedPreferences _sp = null;

    Gson gson = new Gson();



    private UserSP() {};

    public static UserSP getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserSP();
        }
        return(INSTANCE);
    }


    // other instance methods can follow

    public void initSharedPreferences(Context context) {
        _sp = context.getSharedPreferences("sharedlist", context.MODE_PRIVATE);
    }

    // Save User to local device
    public void addUser(@NotNull User user) {
        if (!isInitSharedPreferences()) {
            Log.e("UserSP.addUser", "You need to call initSharedPreferences() before adding user");
            return;
        }
        SharedPreferences.Editor prefsEditor = _sp.edit();
        String json = gson.toJson(user);
        prefsEditor.putString("User", json);
        prefsEditor.commit();
    }

    public User retrieveUser() {
        if (!isInitSharedPreferences()) {
            Log.e("UserSP.retrieveUser", "You need to call initSharedPreferences() before retrieving user");
            return null;
        }
        String json = _sp.getString("User", null);
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public void removeUser() {
        if (!isInitSharedPreferences()) {
            Log.e("UserSP.removeUser", "You need to call initSharedPreferences() before removing user");
            return;
        }
        SharedPreferences.Editor prefsEditor = _sp.edit();
        prefsEditor.remove("User");
        prefsEditor.commit();
    }



    private boolean isInitSharedPreferences() {
        return _sp != null;
    }

}
