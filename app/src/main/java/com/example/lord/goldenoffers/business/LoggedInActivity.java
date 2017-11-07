package com.example.lord.goldenoffers.business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.helper.SQLiteHandler;
import com.example.lord.goldenoffers.helper.SessionManager;

import java.util.HashMap;

public class LoggedInActivity extends Activity {

    private TextView NameTv;
    private TextView EmailTv;
    private Button LogoutBtn;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        NameTv = (TextView) findViewById(R.id.NameTv);
        EmailTv = (TextView) findViewById(R.id.EmailTv);
        LogoutBtn = (Button) findViewById(R.id.LogoutBtn);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        NameTv.setText(name);
        EmailTv.setText(email);

        // Logout button click event
        LogoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(LoggedInActivity.this, BusinessLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
