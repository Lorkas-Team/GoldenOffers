package com.example.lord.goldenoffers.business;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.helper.Offer;
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

    List<Offer> offerList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());


        final String businessID = LoggedInActivity.BUSINESS.getUid();

        offerList=new ArrayList<>();
        prepareListData(businessID);
        //offerList=db.getOfferDetails();
    }

    private void prepareListData(final String business_id) {

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_MY_OFFERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject offer = array.getJSONObject(i);
                                if(offer.getString("uid").equals(business_id)) {
                                    //adding the product to product list
                                    float price = Float.parseFloat(offer.getString("price"));

                                    offerList.add(new Offer(
                                            offer.getInt("id"),
                                            offer.getString("uid"),
                                            offer.getString("product_name"),
                                            price, offer.getString("description"),
                                            offer.getString("photo"),
                                            offer.getString("regDate"),
                                            offer.getString("expDate")
                                    ));
                                }

                            }
                            //creating adapter object and setting it to recyclerview
                            OfferAdapter adapter = new OfferAdapter(MyOffersActivity.this, offerList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}








