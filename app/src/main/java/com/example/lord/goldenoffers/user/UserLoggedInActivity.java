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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserLoggedInActivity extends AppCompatActivity {

    private SQLiteHandlerForUsers db;
    private SessionManager session;

    protected static User USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged_in);

        TextView tvName = (TextView) findViewById(R.id.NameTv);
        TextView tvEmail = (TextView) findViewById(R.id.EmailTv);
        Button btnLogout = (Button) findViewById(R.id.LogoutBtn);
        Button btnAddDesire = (Button) findViewById(R.id.btn_add_desire);
        Button btnDesiresList = (Button) findViewById(R.id.btn_desires_list);

        db = new SQLiteHandlerForUsers(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        HashMap<String, String> listUserInfo = db.getUserDetails();
        List<Desire> listDesires = new ArrayList<>();
        listDesires = db.getDesires();
        USER = new User(
                listUserInfo.get("username"),
                listUserInfo.get("email"),
                listDesires
        );

        tvName.setText(USER.getUsername());
        tvEmail.setText(USER.getEmail());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

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
        db.deleteDesires();
        launchLoginInActivity();
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

    private void launchLoginInActivity() {
        Intent intent = new Intent(UserLoggedInActivity.this, UserLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
