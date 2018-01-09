package com.example.lord.goldenoffers.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManagerForUsers;

/**
 * Users Home Page Activity<br />
 * Contains buttons to navigate the user to :<br />
 * ADD new desired product for search<br />
 * VIEW list of desired products<br />
 * VIEW list of offered products based on desires and users location<br />
 * LOGOUT user from system<br />
 * Checks if users session is logged in, initializes components, gets users info,<br />
 * displays the info on screen and sets onClick listeners to Button components<br />
 * If users session is NOT logged in, then logs out user, cleans SQLite users table<br />
 * and launches UserLoginActivity.<br />
 * Fields (All private) :<br />
 * db - SQLiteHandlerForUsers Object. Used to get OR delete on logout users info.<br />
 * session - SessionManagerForUsers Object. Session of current user.<br />
 */
public class UserLoggedInActivity extends AppCompatActivity {

    private SQLiteHandlerForUsers db;
    private SessionManagerForUsers session;

    /**
     * Constructor<br />
     * Checks if session is logged in, Gets users info from SQLite and displays it to screen,<br />
     * initializes components and sets onLick listeners to Buttons.<br />
     * Otherwise, logs out user, cleans SQLite users table and launches the UserLoginActivity<br />
     * @param savedInstanceState Bundle Object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged_in);

        //initializing session field
        session = new SessionManagerForUsers(getApplicationContext());
        //initializing db field
        db = new SQLiteHandlerForUsers(getApplicationContext());
        //checking if users session is logged in
        if (!session.isLoggedIn()) {
            //users session is NOT logged in
            //logout user, launching UserLoggedInActivity
            logoutUser();
        } else {
            //getting users info from SQLite.
            User user = db.getUserDetails();

            //getting TextView components
            TextView tvName = findViewById(R.id.tv_name);
            TextView tvEmail = findViewById(R.id.tv_email);
            //displaying users info on screen.
            tvName.setText(user.getUsername());
            tvEmail.setText(user.getEmail());

            //getting Button components
            Button btnLogout = findViewById(R.id.btn_logout);
            Button btnAddDesire = findViewById(R.id.btn_add_desire);
            Button btnDesiresList = findViewById(R.id.btn_desires_list);
            Button btnCheckOffers = findViewById(R.id.btn_map);

            //TODO start for result ???
            //btnAddDesire click listener - launch addDesireActivity
            btnAddDesire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //launching AddDesireActivity
                    Intent intent = new Intent(
                            UserLoggedInActivity.this,
                            AddDesireActivity.class
                    );
                    startActivity(intent);
                }
            });

            //btnDesireList click listener - launch MyDesiresActivity
            btnDesiresList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //launching MyDesiresActivity
                    Intent intent = new Intent(
                            UserLoggedInActivity.this,
                            MyDesiresActivity.class
                    );
                    startActivity(intent);
                }
            });

            //btnCheckOffers click listener - launch OffersMapsActivity
            btnCheckOffers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(
                            UserLoggedInActivity.this,
                            OffersBasedOnMyDesiresActivity.class
                    );
                    startActivity(intent);
                }
            });

            //btnLogout click listener
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //logging out user
                    //launching UserLoginActivity
                    logoutUser();
                }
            });
        }
    }

    /**
     * Logging out the user.<br />
     * Will set isLoggedIn flag to false in shared preferences.<br />
     * Clears the user data from SQLite users table
     * Launches the UserLoginActivity
     **/
    private void logoutUser() {
        //setting session login to false
        session.setLogin(false);
        //cleaning users from SQLite database
        db.deleteUsers();
        //launching the UserLoginActivity.
        Intent intent = new Intent(
                UserLoggedInActivity.this,
                UserLoginActivity.class
        );
        startActivity(intent);
        //finishing this activity
        finish();
    }
}
