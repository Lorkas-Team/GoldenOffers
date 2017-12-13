package com.example.lord.goldenoffers.business;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
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
import com.example.lord.goldenoffers.helper.InputChecker;
import com.example.lord.goldenoffers.helper.SQLiteHandler;
import com.example.lord.goldenoffers.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button RegisterBtn;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputRepeatPass;
    private EditText inputOwner;
    private EditText inputAfm;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private TextView locationText;
    private static final int REQUEST_LOCATION = 1;
    private Button locationButton;
    private LocationManager locationManager;

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    private String latitude,longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_register);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        locationText = findViewById(R.id.text_location);
        locationButton = findViewById(R.id.button_location);

        locationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (locationManager != null) {
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        buildAlertMessageNoGps();

                    } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        getLocation();
                    }
                }

            }

        });






        inputFullName = findViewById(R.id.etUserName);
        inputEmail = findViewById(R.id.etEmail);
        inputPassword = findViewById(R.id.etPassword);
        inputRepeatPass = findViewById(R.id.etRepeatPass);
        inputOwner = findViewById(R.id.etOwner);
        inputAfm = findViewById(R.id.etAfm);
        RegisterBtn = findViewById(R.id.RegisterBtn);



        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to logged in activity
            Intent intent = new Intent(RegisterActivity.this,
                    LoggedInActivity.class);
            startActivity(intent);
            finish();
        }


        // Register Button Click event
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String repeatpass = inputRepeatPass.getText().toString().trim();
                String owner = inputOwner.getText().toString().trim();
                String afm = inputAfm.getText().toString().trim();

                if(isInputValid(name, email, password, repeatpass, owner, afm)) {
                    registerUser(name, email, password, owner, afm, latitude, longitude);
                }
            }
        });

    }

    private boolean isInputValid(String name, String email, String strPassword, String strPasswordRepeat, String owner, String strAFM) {

        List<Object> response = InputChecker.isBusinessRegisterInputValid(name, email, strPassword, strPasswordRepeat, owner, strAFM);
        boolean error = (boolean) response.get(0);
        if(error) {
            String msgError = (String) response.get(1);
            String unvalidInput = (String) response.get(2);
            clearUnvalidInput(unvalidInput);
            Toast.makeText(
                    getApplicationContext(),
                    msgError, Toast.LENGTH_LONG
            ).show();
            return false;
        } else return true;
    }

    private void clearUnvalidInput(String unvalidInput) {

        switch(unvalidInput) {
            case "name" :
                inputFullName.setText("");
                inputFullName.requestFocus();
                break;
            case "email" :
                inputEmail.setText("");
                inputEmail.requestFocus();
                break;
            case "password" :
                inputPassword.setText("");
                inputRepeatPass.setText("");
                inputPassword.requestFocus();
                break;
            case "owner" :
                inputOwner.setText("");
                inputOwner.requestFocus();
                break;
            default :
                inputAfm.setText("");
                inputAfm.requestFocus();
        }
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password, final String owner, final String afm, final String latitude, final String longitude) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String owner = user.getString("owner");
                        String afm = user.getString("afm");
                        String latitude = user.getString( "latitude");
                        String longitude = user.getString("longitude");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, owner, afm, latitude, longitude, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                BusinessLoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        if(errorMsg.contains(email)) clearUnvalidInput("email");
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
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("owner", owner);
                params.put("afm", afm);
                params.put("latitude", latitude);
                params.put("longitude", longitude);

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
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (RegisterActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                latitude = String.valueOf(latti);
                setLatitude(latitude);
                longitude = String.valueOf(longi);
                setLongitude(longitude);

                locationText.setText("Your current location is"+ "\n" + "Lattitude = " + latitude+ "\n" + "Longitude = " + longitude);
                RegisterBtn.setEnabled(true);
            }else{
                Toast.makeText(this,"Unable to Trace your location please try again soon",Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}

