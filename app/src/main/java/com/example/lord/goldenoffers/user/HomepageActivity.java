package com.example.lord.goldenoffers.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManager;

public class HomepageActivity extends AppCompatActivity {

    private SQLiteHandlerForUsers db;
    private SessionManager session;

    public Button AddWishButton;
    public Button ViewWishButton;
    public Button SearchButton;
    public Button logoutButton;

    public void nav() {

        //Starts the AddWishActivity when the ADD NEW WISH button is pressed
        AddWishButton = (Button) findViewById(R.id.AddWishButton);
        AddWishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomepageActivity.this, AddWishActivity.class);
                startActivity(intent);
            }
        });

        //Starts the ViewWishActivity when  VIEW YOUR WISHES button is pressed
        ViewWishButton = (Button) findViewById(R.id.ViewWishButton);
        ViewWishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomepageActivity.this, ViewWishActivity.class);
                startActivity(intent);
            }
        });

        //Starts the SearchActivity when the SEARCH button is pressed
        SearchButton = (Button) findViewById(R.id.SearchButton);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomepageActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);


        // SqLite database handler
        db = new SQLiteHandlerForUsers(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Logout button click event
        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }


    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(HomepageActivity.this, UserLoginActivity.class);
        startActivity(intent);
        finish();
    }
}