package com.example.lord.goldenoffers.user;

import android.app.ProgressDialog;
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
    private EditText prodName, priceLow, priceHigh;

    private ProgressDialog pDialog;
    private SQLiteHandlerForUsers db;
    private SessionManager session;

    //TODO low < high
    //TODO regex checks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_desire);
        prodName = (EditText) findViewById(R.id.product_name_input);
        priceLow = (EditText) findViewById(R.id.price_low_input);
        priceHigh = (EditText) findViewById(R.id.price_high_input);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        db = new SQLiteHandlerForUsers(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String usersEmail = UserLoggedInActivity.USER.getEmail();
                String strProdName = prodName.getText().toString().trim();
                String strPriceLow = priceLow.getText().toString().trim();
                String strPriceHigh = priceHigh.getText().toString().trim();

                if(allParamsSetted(usersEmail, strProdName, strPriceLow, strPriceHigh)) {
                    addNewDesire(usersEmail, strProdName, strPriceLow, strPriceHigh);
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Some fields are empty.",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    private void addNewDesire(final String usersEmail, final String prodName,
                              final String priceLow, final String priceHigh) {

        // Tag to cancel the request
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
                        db.addDesire(prodID, prodName, priceLow, priceHigh);
                        String msg = "Desire Successfully Added! prods id->"+prodID;
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        //TODO launch home activity
                    } else {
                        String errorMsg = jObj.getString("error_msg");
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
                Log.e(TAG, "Desire Adding Error: " + error.getMessage());
                Toast.makeText(
                        getApplicationContext(),
                        error.getMessage(),
                        Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("users_email", usersEmail);
                params.put("prod_name", prodName);
                params.put("price_low", priceLow);
                params.put("price_high", priceHigh);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private boolean allParamsSetted(String usersEmail, String prodName, String priceLow, String priceHigh) {

        if(!usersEmail.isEmpty() && !prodName.isEmpty() &&
                !priceLow.isEmpty() && !priceHigh.isEmpty()) {
            return true;
        } else {
            return false;
        }
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
