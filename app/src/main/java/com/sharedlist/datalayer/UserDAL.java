package com.sharedlist.datalayer;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sharedlist.entities.User;

import org.jetbrains.annotations.NotNull;

public class UserDAL {

    private static UserDAL INSTANCE = null;

    // Firebase DB reference
    FirebaseDatabase _database = FirebaseDatabase.getInstance();
    DatabaseReference _usersRef = _database.getReference("users"); // Reference to users

    volatile User _user;
    volatile String _message;

    public String get_message() {
        return _message;
    }
    public User get_user() {
        return _user;
    }

    private UserDAL() {};

    public static UserDAL getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDAL();
        }
        return(INSTANCE);
    }


    // other instance methods can follow

    public void addUser(@NotNull User user) {
        _message = "";
        _user = user;
        String mailToKey = user.getMail().replace(".", ",");

        _usersRef.child(mailToKey.toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    _message = "User: " + _user.getMail() + " already exist.";
                }else{
                    DatabaseReference userRef = _usersRef.child(snapshot.getKey());
                    userRef.setValue(_user);
                    _message = "1";
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void retrieveUser(@NotNull String email, @NotNull final String pass) {
        _message = "";
        String mailToKey = email.replace(".", ",");

        _usersRef.child(mailToKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    _user = snapshot.getValue(User.class);
                    if (pass.equals(_user.getPassword())) {
                        _message = "1";
                    } else {
                        _user = null;
                        _message = "Wrong user or password details";
                    }
                } else {
                    _message = "Wrong user or password details";
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

}
