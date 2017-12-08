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

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("username");
        String email = user.get("email");

        // Displaying the user details on the screen
        tvName.setText(name);
        tvEmail.setText(email);

        //Add New Offer on click event
        btnAddDesire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAddDesire = new Intent(UserLoggedInActivity.this,AddDesireActivity.class);
                startActivity(gotoAddDesire);
            }
        });




        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        db.deleteDesires();
        Intent intent = new Intent(UserLoggedInActivity.this, UserLoginActivity.class);
        startActivity(intent);
        finish();
    }


}
