package com.example.lord.goldenoffers.user;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.app.AppConfig;
import com.example.lord.goldenoffers.app.AppController;
import com.example.lord.goldenoffers.business.MyOffersActivity;
import com.example.lord.goldenoffers.business.Offer;
import com.example.lord.goldenoffers.business.OfferAdapter;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OffersMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private  List<Offer> offerList;
    private static final String TAG = OffersMapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    RecyclerView recyclerView;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_offers);
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SQLiteHandlerForUsers db = new SQLiteHandlerForUsers(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        offerList = new ArrayList<>();
        User user = db.getUserDetails();

        final String users_id = String.valueOf(user.getDbID());
        pDialog.setMessage("Trying to get your offers from database ...");
        showDialog();
        String tag_string_req = "req...offers";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.USER_GET_OFFERS_FROM_DESIRES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get offers Response: " + response);
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
                                        offer.getString("regDate"),
                                        offer.getString("expDate"),
                                        offer.getString("name"),
                                        offer.getString("longitude"),
                                        offer.getString("latitude")
                                )
                        );


                    }
                    //creating adapter object and setting it to recyclerview
                    //OfferAdapter adapter = new OfferAdapter(OffersMapsActivity.this, offerList);
                    //recyclerView.setAdapter(adapter);

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

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("users_id", users_id);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        setContentView(R.layout.activity_offers_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

        @Override
        public void onMapReady (GoogleMap googleMap){
            mMap = googleMap;

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }



            //no working TODO
            for(Offer offer: offerList){
                LatLng latLng = new LatLng( Double.parseDouble(offer.getLongitude()),Double.parseDouble(offer.getLatitude()));
                mMap.addMarker(new MarkerOptions().position(latLng).title("Offer name: " + offer.getProduct_name() +" , price: " + offer.getPrice() + " , Business name: " + offer.getBusiness_name()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            }

        }





    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
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