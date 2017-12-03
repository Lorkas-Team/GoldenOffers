package com.example.lord.goldenoffers.user;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddDesireActivity extends AppCompatActivity {

    private Button btnSubmit;
    private EditText prodName,
            prodCateg, prodDescr,
            priceLow, priceHigh
    ;

    private static final String TAG = AddDesireActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SQLiteHandlerForUsers db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_desire);
        prodName = (EditText) findViewById(R.id.product_name_input);
        prodCateg = (EditText) findViewById(R.id.product_category_input);
        prodDescr = (EditText) findViewById(R.id.product_description_input);
        priceLow = (EditText) findViewById(R.id.price_low_input);
        priceHigh = (EditText) findViewById(R.id.price_high_input);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        db = new SQLiteHandlerForUsers(getApplicationContext());

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO get users email
                String strProdName = prodName.getText().toString().trim();
                String strProdCateg = prodCateg.getText().toString().trim();
                String strProdDescr = prodDescr.getText().toString().trim();
                String strPriceLow = priceLow.getText().toString().trim();
                String strPriceHigh = priceHigh.getText().toString().trim();
                if(allInputsFilled(strProdName, strProdCateg, strProdDescr, strPriceLow, strPriceHigh)) {
                    addNewDesire(
                            "email@email.com", strProdName, strProdCateg,
                            strProdDescr, strPriceLow, strPriceHigh
                    );
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

    //TODO add prod_categ, low < high
    private void addNewDesire(final String email, final String prodName,
                              final String prodCateg, final String prodDescr,
                              final String priceLow, final String priceHigh) {

        // Tag used to cancel the request
        String tag_string_req = "req_add_desire";

        pDialog.setMessage("Adding New Desire ...");
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
                        //id
                        JSONObject desire = jObj.getJSONObject("desire");
                        int idDesire = Integer.parseInt(desire.getString("prod_id"));
                        db.addDesire(idDesire, prodName, priceLow, priceHigh);
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
                params.put("user_email", email);
                params.put("prod_name", prodName);
                params.put("prod_descr", prodDescr);
                params.put("price_low", priceLow);
                params.put("price_high", priceHigh);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private boolean allInputsFilled(String prodName, String prodCateg,
                                    String prodDescription, String priceLow,
                                    String priceHigh) {
        //TODO regex checks
        if(!prodName.isEmpty() && !prodCateg.isEmpty() && !prodDescription.isEmpty() &&
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
