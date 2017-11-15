package com.example.lord.goldenoffers.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.content.Intent;
import android.annotation.SuppressLint;


import com.example.lord.goldenoffers.R;


public class LoginActivity extends AppCompatActivity {

    //Layout button and editText variables
    Button Loginbutton;
    EditText editTextUsername;
    EditText editTextPassword;

    //Database connection variables
    Connection con;
    String un, pass, db, ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        //Get values from button and editTexts
        Loginbutton = (Button) findViewById(R.id.loginButton);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        //Database configuration
        ip = "";     //server IP goes here
        db = "";     //database name goes here
        un = "";     //database username goes here
        pass = "";   //database password goes here

        //What happens when you press the Login button
        Loginbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CheckLogin checkLogin = new CheckLogin();          //Async task for reduced load on app process

                checkLogin.execute("");
            }
        });
    }


    public class CheckLogin extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        /*
           @Override
           protected void onPreExecute() {


           }
        */

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(LoginActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {

                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                startActivity(intent);

            }
        }

        @Override
        protected String doInBackground(String... params) {

            String usernam = editTextUsername.getText().toString();
            String passwordd = editTextPassword.getText().toString();
            if (usernam.trim().equals("") || passwordd.trim().equals(""))
                z = "Please enter Username and Password";
            else {

                try {
                    con = connectionclass(un, pass, db, ip); //Connecting to database
                    if (con == null) {
                        z = "Check your internetz!";
                    } else {
                        String query = "SELECT * FROM users WHERE username= '" + usernam.toString() + "' AND password ='" + passwordd.toString() + "' ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {

                            z = "Login successful";
                            isSuccess = true;
                            con.close();
                        } else {

                            z = "Invalid Credentials!";
                            isSuccess = false;
                        }
                    }
                } catch (Exception ex) {

                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try {

            Class.forName("");
            ConnectionURL = "" + server + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se) {

            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e) {

            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e) {

            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}

