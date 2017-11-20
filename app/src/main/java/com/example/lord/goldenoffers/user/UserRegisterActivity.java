package com.example.lord.goldenoffers.user;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.app.AppConfig;
import com.example.lord.goldenoffers.app.AppController;
import com.example.lord.goldenoffers.helper.SQLiteHandler;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegisterActivity extends AppCompatActivity {

    private static final String TAG = UserRegisterActivity.class.getSimpleName();
    private Button RegisterBtn;
    private EditText inputUserName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputRepeatPass;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandlerForUsers db;


    public final static boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);








        inputUserName = (EditText) findViewById(R.id.etUserName);
        inputEmail = (EditText) findViewById(R.id.etEmail);
        inputPassword = (EditText) findViewById(R.id.etPassword);
        inputRepeatPass = (EditText) findViewById(R.id.etRepeatPass);
        RegisterBtn = (Button) findViewById(R.id.RegisterBtn);



        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandlerForUsers(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to logged in activity
            Intent intent = new Intent(UserRegisterActivity.this,
                    UserLoggedInActivity.class);
            startActivity(intent);
            finish();
        }


        // Register Button Click event
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String username = inputUserName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String repeatpass = inputRepeatPass.getText().toString().trim();



                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !repeatpass.isEmpty()   ) {
                    if(isEmailValid(email)==true) {

                        if (!password.equals(repeatpass)) {
                            Toast.makeText(getApplicationContext(),
                                    "Password doesn't match!", Toast.LENGTH_LONG)
                                    .show();
                        } else {

                                registerUser(username, email, password);


                        }

                    }else{
                        Toast.makeText(getApplicationContext(),
                                "This is not a valid Email address!", Toast.LENGTH_LONG)
                                .show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(),
                            "You must fill in all fields!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String username, final String email,final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.USER_URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite


                        JSONObject user = jObj.getJSONObject("user");
                        String username = user.getString("username");
                        String email = user.getString("email");


                        // Inserting row in users table
                        db.addUser(username, email);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                UserRegisterActivity.this,
                                UserLoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        //errorMsg=errorMsg+"paok";
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}


