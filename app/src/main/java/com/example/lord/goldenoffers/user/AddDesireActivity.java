package com.example.lord.goldenoffers.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.app.AppConfig;
import com.example.lord.goldenoffers.app.AppController;
import com.example.lord.goldenoffers.helper.InputChecker;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManagerForUsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gets and uploads to servers database the information of a new desire (product for search).<br />
 * User inputs the information of the product and presses the submit button.<br />
 * The system receives the inputted value, checks if value is correct and<br />
 * makes a request to store the value to servers database.<br />
 * If desire stores successfully to database, the system launches the UserLoggedInActivity.<br />
 * Otherwise, displays an error message to the screen.<br />
 * Fields (All private) :<br />
 * prodNameInput - EditText in which user inputs the name of product.<br />
 * priceLowInput - EditText in which user inputs the minimum price.<br />
 * priceHighInput - EditText in which user inputs the maximum price.<br />
 * pDialog - ProgressDialog Object.<br />
 * session - SessionManagerForUsers Object. Used to get users session and check if is logged in.
 * db - SQLiteHandlerForUsers Object. Used to get OR delete on logout users info.
 **/
public class AddDesireActivity extends AppCompatActivity {

    private EditText prodNameInput, priceLowInput, priceHighInput;
    private ProgressDialog pDialog;
    private SessionManagerForUsers session;
    private SQLiteHandlerForUsers db;

    /**
     * Initializes components, sets onClick listener to submit button :<br />
     * Gets the inputted value, checks it and calls the AddDesire(...) method.<br />
     * @param savedInstanceState Bundle Object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_desire);

        //initializing session manager
        session = new SessionManagerForUsers(getApplicationContext());
        //initializing the SQLite handler
        db = new SQLiteHandlerForUsers(getApplicationContext());
        //checking if users session is logged in
        if (!session.isLoggedIn()) {
            //users session is NOT logged in
            //logout user, launching UserLoginActivity
            logoutUser();
        } else {
            //getting the EditText components
            prodNameInput = findViewById(R.id.product_name_input);
            priceLowInput = findViewById(R.id.price_low_input);
            priceHighInput = findViewById(R.id.price_high_input);

            //initializing the progress dialog
            pDialog = new ProgressDialog(this);
            //setting the progress dialog to not cancelable.
            pDialog.setCancelable(false);

            //TODO get id from home page
            //getting users info from SQLite
            User user = db.getUserDetails();
            //getting users id
            final int usersDbID = user.getDbID();

            //getting the submit button component
            Button btnSubmit = findViewById(R.id.btn_submit);
            //setting onClick listener to the submit button
            btnSubmit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //getting users inputted value (products name, products min & max prices)
                    String nameDesire = prodNameInput.getText().toString().trim();
                    String strPriceLow = priceLowInput.getText().toString().trim();
                    String strPriceHigh = priceHighInput.getText().toString().trim();

                    //checking if users id and inputted values are correct
                    if (usersDbID > 0 && isInputValid(nameDesire, strPriceLow, strPriceHigh)) {
                        //making a request to server to store the information of a new desire
                        addNewDesire(String.valueOf(usersDbID), nameDesire, strPriceLow, strPriceHigh);
                    }
                }
            });
        }
    }

    /**
     * launches the UserLoggedInActivity and finishes this activity
     */
    private void launchHomeActivity() {
        //launching UserLoggedInActivity
        Intent intent = new Intent(
                AddDesireActivity.this,
                UserLoggedInActivity.class
        );
        startActivity(intent);
        //finishing this activity
        finish();
    }

    /**
     * Stores a new desired product to the database.<br />
     * Makes a request to server to store the desires information.<br />
     * if the response is positive, displays a message and launches the UserLoggedInActivity<br />
     * Otherwise, displays an error message.
     * @param strUsersDbID (String) Users id casted to string
     * @param prodName (String) The name of desired product
     * @param strPriceLow (String) The minimum price for the product casted to string
     * @param strPriceHigh (String) The maximum price for the product casted to string
     */
    private void addNewDesire(final String strUsersDbID, final String prodName,
                              final String strPriceLow, final String strPriceHigh) {

        final String tag = AddDesireActivity.class.getSimpleName();
        String tag_string_req = "req_add_desire";

        //displays the progress dialog
        showDialog("Adding");

        //making the request
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.USER_URL_ADD_DESIRE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //outputs the response to logcat
                Log.d(tag, "Adding Desire Response : " + response);
                //dismissing the progress dialog
                hideDialog();
                try {
                    //receiving the response
                    JSONObject jObj = new JSONObject(response);
                    //checking if desire stored successfully
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        //display a toast with a success message
                        makeToast("Desire Successfully Added.");
                        //launching the home activity
                        launchHomeActivity();
                    } else {
                        //desire failed to store
                        //display a toast with an error message
                        makeToast(jObj.getString("error_msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //output to logcat the response error
                Log.e(tag, "Desire Adding Error: " + error.getMessage());
                //displaying a toast with the error message on the screen
                makeToast(error.getMessage());
                //dismissing the progress dialog
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //setting the values of the methods parameters to request parameters
                Map<String, String> params = new HashMap<>();
                params.put("users_id", strUsersDbID);
                params.put("prod_name", prodName);
                params.put("price_low", strPriceLow);
                params.put("price_high", strPriceHigh);
                return params;
            }
        };
        //adding the request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Checks if users input is valid.<br />
     * if users input is not valid, cleans the EditText of the uncorrected input,<br />
     * sets focus on it and displays a Toast with an error message.<br />
     * @param nameDesire (String) Desired products name, inputted by user
     * @param strPriceLow (String) Desired products minimum price, inputted by user
     * @param strPriceHigh (String) Desired products maximum price, inputted by user
     * @return true / false if input is valid OR not
     */
    private boolean isInputValid(String nameDesire, String strPriceLow, String strPriceHigh) {

        //getting a response from input checker
        List<Object> response = InputChecker.isAddDesireInputValid(nameDesire, strPriceLow, strPriceHigh);
        //checking if input is valid
        boolean error = (boolean) response.get(0);
        if(error) {
            //the input is not valid
            //getting the error message from response
            String msgError = (String) response.get(1);
            //getting the uncorrected input
            String unvalidInput = (String) response.get(2);
            //cleaning the input and setting focus on it
            clearUnvalidInput(unvalidInput);
            //displaying the error message
            makeToast(msgError);
            return false;
        } else return true;
    }

    /**
     * Checks which EditText contains the uncorrected input, cleans it and sets focus on it.<br />
     * @param unvalidInput (String) Which input is uncorrected
     */
    private void clearUnvalidInput(String unvalidInput) {

        switch(unvalidInput) {
            case "name" :
                prodNameInput.setText("");
                prodNameInput.requestFocus();
                break;
            case "price_low" :
                priceLowInput.setText("");
                priceLowInput.requestFocus();
                break;
            case "price_high" :
                priceLowInput.setText("");
                priceLowInput.requestFocus();
                break;
            default :
                priceLowInput.setText("");
                priceHighInput.setText("");
                priceLowInput.requestFocus();
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
                AddDesireActivity.this,
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

    /**
     * Displays the progress dialog on the screen.
     */
    private void showDialog(String message) {
        pDialog.setMessage(message);
        if (!pDialog.isShowing()) pDialog.show();
    }

    /**
     * Dismisses the progress dialog.
     */
    private void hideDialog() {
        if (pDialog.isShowing()) pDialog.dismiss();
    }
}
