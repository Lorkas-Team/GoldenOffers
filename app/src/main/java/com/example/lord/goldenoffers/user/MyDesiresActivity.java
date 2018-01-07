package com.example.lord.goldenoffers.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDesiresActivity extends AppCompatActivity {

    private List<Desire> listDesires;
    private SessionManagerForUsers session;
    private SQLiteHandlerForUsers db;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desire_list);
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //initializing session manager
        session = new SessionManagerForUsers(getApplicationContext());
        //initializing SQLite handler
        db = new SQLiteHandlerForUsers(getApplicationContext());
        //checking if users session is logged in
        if (!session.isLoggedIn()) {
            //users session is NOT logged in
            //logout user, launching UserLoggedInActivity
            logoutUser();
        } else {
            //TODO get users id as extra from homepage
            //getting users info from SQLite
            User user = db.getUser();
            final String strUsersDbID = String.valueOf(user.getDbID());
            //initializing the desires list
            listDesires = new ArrayList<>();
            //getting users desired products from database based on users id
            final String tag = MyDesiresActivity.class.getSimpleName();
            String tag_string_req = "req_get_desires";

            //making the request
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.USER_URL_GET_DESIRES, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    //outputs the response to logcat
                    Log.d(tag, "Getting Desires Response: " + response);
                    try {
                        //receiving the response
                        JSONObject jObj = new JSONObject(response);
                        //checking if response has an error
                        boolean error = jObj.getBoolean("error");
                        if (!error) {
                            //getting the desires of response as JSon array
                            JSONArray jArrDesires = jObj.getJSONArray("desires");
                            //for each of desires
                            for (int pos = 0; pos < jArrDesires.length(); pos++) {

                                //getting one desire from desires JSon array
                                JSONArray desire = jArrDesires.getJSONArray(pos);
                                //getting desires info
                                float priceLow = Float.parseFloat(desire.getString(1));
                                float priceHigh = Float.parseFloat(desire.getString(2));
                                int desireDbID = desire.getInt(3);
                                String desireName = desire.getString(4);
                                //creating a new desire object and adding it to desires list
                                //Desire newDes = new Desire(desireDbID, desireName, priceLow, priceHigh);
                                //makeToast(newDes.toString());
                                listDesires.add(new Desire(desireDbID, desireName, priceLow, priceHigh));
                            }

                            if (listDesires.size() > 0) {
                                //creating adapter object and setting it to recyclerView
                                DesireAdapter adapter = new DesireAdapter(MyDesiresActivity.this, listDesires);
                                recyclerView.setAdapter(adapter);
                            } else {
                                //user has NOT desired products
                                //displaying a toast
                                makeToast("Nothing to show.");
                                //launching UserLoggedInActivity
                                Intent intent = new Intent(
                                        MyDesiresActivity.this,
                                        UserLoggedInActivity.class
                                );
                                startActivity(intent);
                                //finishing this activity
                                finish();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    //output to logcat the response error
                    Log.e(tag, "Getting Desires Error: " + error.getMessage());
                    //displaying a toast with the error message on the screen
                    makeToast(error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    //setting the value of the methods parameter to request parameter
                    Map<String, String> params = new HashMap<>();
                    params.put("users_id", strUsersDbID);
                    return params;
                }
            };
            //adding the request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
            //checking if desires list is NOT empty
        }
    }

    private void getUsersDesires(final String strUsersDbID) {


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
                MyDesiresActivity.this,
                UserLoginActivity.class
        );
        startActivity(intent);
        //finishing this activity
        finish();
    }

    /**
     * Makes a toast to display a message on the screen.<br />
     * @param message (String) Text to display
     */
    private void makeToast(String message) {
        Toast.makeText(
                getApplicationContext(),
                message, Toast.LENGTH_LONG
        ).show();
    }
}