package com.example.lord.goldenoffers.business;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.app.AppConfig;
import com.example.lord.goldenoffers.app.AppController;
import com.example.lord.goldenoffers.helper.SQLiteHandler;
import com.example.lord.goldenoffers.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddOfferActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText inputProductName;
    private EditText inputRegDate;
    private EditText inputExpDate;
    private DatePickerDialog datePickerDialog;
    private EditText inputPrice;
    private ImageView inputImage;
    private Button uploadImgBtn;
    private EditText inputDescription;
    private Button uploadOfferBtn;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        inputProductName = (EditText) findViewById(R.id.etProductName);
        inputRegDate = (EditText) findViewById(R.id.etRegDate);
        inputExpDate = (EditText) findViewById(R.id.etExpDate);
        inputPrice = (EditText) findViewById(R.id.etPrice);
        inputImage = (ImageView) findViewById(R.id.imageView);
        uploadImgBtn = (Button) findViewById(R.id.uploadImgbtn);
        inputDescription = (EditText) findViewById(R.id.etDescription);
        uploadOfferBtn = (Button) findViewById(R.id.uploadBtn);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());


        inputRegDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); //current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); //current day

                datePickerDialog = new DatePickerDialog(AddOfferActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        inputRegDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();

            }
        });

        inputExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); //current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); //current day

                datePickerDialog = new DatePickerDialog(AddOfferActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        inputExpDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();

            }
        });


        inputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        uploadImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        uploadOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_name = inputProductName.getText().toString().trim();
                String regDate = inputRegDate.getText().toString().trim();
                String expDate = inputExpDate.getText().toString().trim();
                String price = inputPrice.getText().toString().trim();
                String description = inputDescription.getText().toString().trim();


                if(!product_name.isEmpty() && !regDate.isEmpty() && !price.isEmpty()){

                        offerUpload(product_name, regDate, expDate, price, description);

                }else {
                    Toast.makeText(getApplicationContext(),
                            "You must fill in all fields with  *  ", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });

    }

    private void offerUpload(final String product_name, final String regDate,
             final String expDate, final String price, final String description) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Uploading Offer ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_OFFER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Add Offer Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // Offer successfully stored in MySQL
                        // Now store the offer in sqlite
                        //String oid = jObj.getString("oid");

                        JSONObject offers = jObj.getJSONObject("offers");
                        String product_name = offers.getString("product_name");
                        String regDate = offers.getString("regDate");
                        String expDate = offers.getString("expDate");
                        String price = offers.getString("price");
                        String description = offers.getString( "description");


                        // Inserting row in users table
                        db.addOffer(product_name, regDate, expDate, price, description);

                        Toast.makeText(getApplicationContext(), "Offer successfully added!", Toast.LENGTH_LONG).show();

                        // Launch logged in activity
                        Intent intent = new Intent(
                                AddOfferActivity.this,
                                LoggedInActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
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
                Log.e(TAG, "Upload Offer Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("product_name", product_name);
                params.put("regDate", regDate);
                params.put("expDate", expDate);
                params.put("price", price);
                params.put("description", description);

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
