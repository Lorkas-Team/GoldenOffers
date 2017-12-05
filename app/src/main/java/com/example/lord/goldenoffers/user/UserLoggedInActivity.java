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
    private TextView NameTv;
    private TextView EmailTv;
    private Button LogoutBtn;

    private SQLiteHandlerForUsers db;
    private SessionManager session;

    public Button ContButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_loged_in);

        NameTv = (TextView) findViewById(R.id.NameTv);
        EmailTv = (TextView) findViewById(R.id.EmailTv);
        LogoutBtn = (Button) findViewById(R.id.LogoutBtn);

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
        LogoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    public void init(){

        ContButton=(Button)findViewById(R.id.ContButton);
        ContButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent toy = new Intent(UserLoggedInActivity.this,HomepageActivity.class);
                startActivity(toy);

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
        Intent intent = new Intent(UserLoggedInActivity.this, UserLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
