package com.sharedlist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sharedlist.datalayer.UserDAL;
import com.sharedlist.datalayer.UserSP;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _usernameText; // eMail
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;  // link to new account

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _usernameText = findViewById(R.id.user_name_editText);
        _passwordText = findViewById(R.id.password_editText);
        _loginButton = findViewById(R.id.submit_user_button);
        _signupLink = findViewById(R.id.link_signup);
    }


    // Login button
    public void login(View view) {

        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed("Your input is not valid");
            return;
        }
        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        // authentication logic.
        final UserDAL userDAL = UserDAL.getInstance();
        userDAL.retrieveUser(email, password);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if ("1".equals(userDAL.get_message())) {
                            onLoginSuccess();
                        }
                        else {
                            onLoginFailed(userDAL.get_message());
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {  // Result from signupActivity
                onLoginSuccess();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }


    private boolean validate() {
        boolean valid = true;

        String email = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _usernameText.setError("enter a valid email address");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        // update the user on SharedPreferences
        UserSP.getInstance().addUser(UserDAL.getInstance().get_user());

        this.finish();
    }
    public void onLoginFailed(String msg) {
        Toast.makeText(getBaseContext(), "Login failed: " + msg, Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }


    public void signUp(View view) {
        // Start the Signup activity
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }
}