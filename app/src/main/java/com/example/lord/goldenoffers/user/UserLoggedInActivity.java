package com.example.lord.goldenoffers.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManager;

import java.util.HashMap;

public class UserLoggedInActivity extends AppCompatActivity {

    private SQLiteHandlerForUsers db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged_in);

        TextView NameTv = (TextView) findViewById(R.id.NameTv);
        TextView EmailTv = (TextView) findViewById(R.id.EmailTv);
        Button btnLogout = (Button) findViewById(R.id.LogoutBtn);
        Button btnAddDesire = (Button) findViewById(R.id.btn_add_desire);
        Button btnDesiresList = (Button) findViewById(R.id.btn_desires_list);

        // SqLite database handler
        db = new SQLiteHandlerForUsers(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("username");
        String email = user.get("email");

        // Displaying the user details on the screen
        NameTv.setText(name);
        EmailTv.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        // Add Desire button click event
        btnAddDesire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAddDesireActivity();
            }
        });

        btnDesiresList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDesiresListActivity();
            }
        });
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        // Launching the login activity
        Intent intent = new Intent(UserLoggedInActivity.this, UserLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void launchAddDesireActivity() {
        Intent intent = new Intent(UserLoggedInActivity.this, AddDesireActivity.class);
        startActivity(intent);
        finish();
    }

    private void launchDesiresListActivity() {
        Intent intent = new Intent(UserLoggedInActivity.this, DesireListActivity.class);
        startActivity(intent);
        finish();
    }
}
