package com.example.lord.goldenoffers.business;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.app.AppConfig;
import com.example.lord.goldenoffers.app.AppController;
import com.example.lord.goldenoffers.helper.SQLiteHandler;
import com.example.lord.goldenoffers.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import static com.example.lord.goldenoffers.app.AppConfig.URL_MY_OFFERS;


public class MyOffersActivity extends AppCompatActivity {


    ExpandableListView expListView;
    //on progress

    private SessionManager session;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    List<Offer> offerList;
    RecyclerView recyclerView;
    private static final String TAG = MyOffersActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        final String business_id = user.get("uid");

        offerList=new ArrayList<>();
        // preparing list data
        prepareListData(business_id);
        //offerList=db.getOfferDetails();


    }

    /*
     * Preparing the list data
     */
    private void prepareListData(final String business_id) {
        pDialog.setMessage("Trying to get your offers from database ...");
        showDialog();



        String tag_string_req = "req...offers";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_MY_OFFERS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject offer = array.getJSONObject(i);
                            //adding the product to product list
                            offerList.add(new Offer(
                                    offer.getInt("id"),
                                    offer.getString("uid"),
                                    offer.getString("product_name"),
                                    offer.getString("price"),
                                    offer.getString("description"),
                                    StringToBitMap(offer.getString("image")),
                                    offer.getString("regDate"),
                                    offer.getString("expDate")
                            ));


                    }

                    //creating adapter object and setting it to recyclerview
                    OfferAdapter adapter = new OfferAdapter(MyOffersActivity.this, offerList);
                    recyclerView.setAdapter(adapter);
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
                params.put("uid", business_id);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);



    }
    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
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








