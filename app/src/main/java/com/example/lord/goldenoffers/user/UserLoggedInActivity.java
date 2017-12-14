package com.example.lord.goldenoffers.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.app.AppConfig;
import com.example.lord.goldenoffers.app.AppController;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManagerForUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserLoggedInActivity extends AppCompatActivity {

    private static final String TAG = UserLoggedInActivity.class.getSimpleName();
    protected static User USER;

    private SQLiteHandlerForUsers db;
    private SessionManagerForUsers session;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged_in);

        TextView tvName = findViewById(R.id.tv_name);
        TextView tvEmail = findViewById(R.id.tv_email);
        Button btnLogout = findViewById(R.id.btn_logout);
        Button btnAddDesire = findViewById(R.id.btn_add_desire);
        Button btnDesiresList = findViewById(R.id.btn_desires_list);

        session = new SessionManagerForUsers(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        db = new SQLiteHandlerForUsers(getApplicationContext());
        USER = db.getUserDetails();

        String name = USER.getUsername();
        String email = USER.getEmail();

        tvName.setText(name);
        tvEmail.setText(email);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        setDesiresToSQLite(String.valueOf(USER.getDbID()));

        btnAddDesire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        UserLoggedInActivity.this,
                        AddDesireActivity.class
                );
                startActivity(intent);
            }
        });

        btnDesiresList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        UserLoggedInActivity.this,
                        MyDesiresActivity.class
                );
                startActivity(intent);
            }
        });

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
        Intent intent = new Intent(
                UserLoggedInActivity.this,
                UserLoginActivity.class
        );
        startActivity(intent);
        finish();
    }

    private void setDesiresToSQLite(final String strUsersDbID) {

        String tag_string_req = "req_get_desires";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.USER_URL_GET_DESIRES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Getting Desires Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        JSONArray jArrDesires = jObj.getJSONArray("desires");
                        for(int pos = 0; pos < jArrDesires.length(); pos++){

                            JSONArray desire = jArrDesires.getJSONArray(pos);

                            String strPriceLow = desire.getString(1);
                            String strPriceHigh = desire.getString(2);
                            int desireDbID = desire.getInt(3);
                            String desireName = desire.getString(4);

                            db.addDesire(desireDbID, desireName, strPriceLow, strPriceHigh);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Getting Desires Error: " + error.getMessage());
                makeToast(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("users_id", strUsersDbID);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void makeToast(String message) {
        Toast.makeText(
                getApplicationContext(),
                message, Toast.LENGTH_LONG
        ).show();
    }

    private void showDialog() {
        if (!pDialog.isShowing()) pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing()) pDialog.dismiss();
    }
}
