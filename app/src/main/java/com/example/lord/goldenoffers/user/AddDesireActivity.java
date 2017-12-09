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
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddDesireActivity extends AppCompatActivity {

    private static final String TAG = AddDesireActivity.class.getSimpleName();

    private Button btnSubmit;
    private EditText prodNameInput, priceLowInput, priceHighInput;

    private ProgressDialog pDialog;
    private SQLiteHandlerForUsers db;
    private SessionManager session;

    //TODO low < high
    //TODO regex checks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_desire);
        prodNameInput = (EditText) findViewById(R.id.product_name_input);
        priceLowInput = (EditText) findViewById(R.id.price_low_input);
        priceHighInput = (EditText) findViewById(R.id.price_high_input);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        db = new SQLiteHandlerForUsers(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int usersID = UserLoggedInActivity.USER.getId();
                String strProdName = prodNameInput.getText().toString().trim();
                String strPriceLow = priceLowInput.getText().toString().trim();
                String strPriceHigh = priceHighInput.getText().toString().trim();

                if(allParamsSetted(usersID, strProdName, strPriceLow, strPriceHigh)) {
                    addNewDesire(String.valueOf(usersID), strProdName, strPriceLow, strPriceHigh);
                } else {
                    makeToast("Some fields are empty.");
                }
            }
        });
    }

    private void launchHomeActivity() {
        Intent intent = new Intent(
                AddDesireActivity.this,
                UserLoggedInActivity.class
        );
        startActivity(intent);
        finish();
    }

    private void addNewDesire(final String strUsersID, final String prodName,
                              final String strPriceLow, final String strPriceHigh) {

        String tag_string_req = "req_add_desire";

        pDialog.setMessage("Adding New Desire");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.USER_URL_ADD_DESIRE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Adding Desire Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONObject prod_id = jObj.getJSONObject("prod_id");
                        int prodID = prod_id.getInt("id");
                        db.addDesire(prodID, prodName, strPriceLow, strPriceHigh);

                        makeToast("Desire Successfully Added! prods id->"+prodID);
                        launchHomeActivity();
                    } else {
                        makeToast(jObj.getString("error_msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Desire Adding Error: " + error.getMessage());
                makeToast(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("users_id", strUsersID);
                params.put("prod_name", prodName);
                params.put("price_low", strPriceLow);
                params.put("price_high", strPriceHigh);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private boolean allParamsSetted(int usersID, String prodName, String priceLow, String priceHigh) {

        if(usersID >= 0 && !prodName.isEmpty() &&
                !priceLow.isEmpty() && !priceHigh.isEmpty()) {
            return true;
        } else return false;
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
