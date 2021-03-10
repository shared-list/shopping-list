package com.sharedlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sharedlist.datalayer.UserDAL;
import com.sharedlist.datalayer.UserSP;
import com.sharedlist.entities.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        UserSP.getInstance().initSharedPreferences(this);

        User user = UserSP.getInstance().retrieveUser();
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else {


            final UserDAL userDAL = UserDAL.getInstance();
            userDAL.retrieveUser(user.getMail(), user.getPassword());

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            if ("1".equals(userDAL.get_message())) {
                                //onLoginSuccess();
                                initMainActivity();  // TODO refresh Fragment?
                            }
                            else {
                                //onLoginFailed(userDAL.get_message());
                            }
                            //progressDialog.dismiss();
                        }
                    }, 3000);
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }


    private void initMainActivity() {
        User user = UserDAL.getInstance().get_user();
        if (user != null) {

//            CardsDAL.getInstance().retrieveCardsByCardsName(user.getCardsList());

        }
    }


}