package com.example.lord.goldenoffers.business;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private EditText inputDescription;
    private Button uploadOfferBtn;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private final int CODE_GALLEY_REQUEST = 999;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        inputProductName = (EditText) findViewById(R.id.etProductName);
        inputRegDate = (EditText) findViewById(R.id.etRegDate);
        inputExpDate = (EditText) findViewById(R.id.etExpDate);
        inputPrice = (EditText) findViewById(R.id.etPrice);
        inputImage = (ImageView) findViewById(R.id.imageView);
        inputDescription = (EditText) findViewById(R.id.etDescription);
        uploadOfferBtn = (Button) findViewById(R.id.uploadBtn);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        final String business_id = user.get("uid");
        final String business_name = user.get("name");


        inputRegDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c1 = Calendar.getInstance();
                int mYear = c1.get(Calendar.YEAR); //current year
                int mMonth = c1.get(Calendar.MONTH); // current month
                int mDay = c1.get(Calendar.DAY_OF_MONTH); //current day

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
                final Calendar c2 = Calendar.getInstance();
                int mYear = c2.get(Calendar.YEAR); //current year
                int mMonth = c2.get(Calendar.MONTH); // current month
                int mDay = c2.get(Calendar.DAY_OF_MONTH); //current day

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
                ActivityCompat.requestPermissions(
                        AddOfferActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLEY_REQUEST
                );

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


                    if(!product_name.isEmpty() && !regDate.isEmpty() && !expDate.isEmpty() && !price.isEmpty() && bitmap != null){

                        String image = imageToString(bitmap);

                        offerUpload(business_id, business_name, product_name, regDate, expDate, price, description, image);

                    }else {
                        Toast.makeText(getApplicationContext(),
                                "You must fill in all fields with  *  ", Toast.LENGTH_LONG)
                                .show();
                    }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLEY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLEY_REQUEST);
            } else {
                Toast.makeText(getApplicationContext(),
                        "You don't have permission", Toast.LENGTH_LONG)
                        .show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CODE_GALLEY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void offerUpload(final String business_id, final String business_name, final String product_name, final String regDate,
                             final String expDate, final String price, final String description, final String image) {
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

                        JSONObject offers = jObj.getJSONObject("offers");
                        String business_id = offers.getString("business_id");
                        String business_name = offers.getString("business_name");
                        String product_name = offers.getString("product_name");
                        String regDate = offers.getString("regDate");
                        String expDate = offers.getString("expDate");
                        String price = offers.getString("price");
                        String description = offers.getString( "description");
                        String image = offers.getString("image");


                        // Inserting row in users table
                        db.addOffer(business_id, business_name, product_name, regDate, expDate, price, description, image);

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

                params.put("business_id", business_id);
                params.put("business_name", business_name);
                params.put("product_name", product_name);
                params.put("regDate", regDate);
                params.put("expDate", expDate);
                params.put("price", price);
                params.put("description", description);
                params.put("image", image);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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
