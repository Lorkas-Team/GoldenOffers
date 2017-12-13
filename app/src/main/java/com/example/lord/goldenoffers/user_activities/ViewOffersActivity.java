package com.example.lord.goldenoffers.user_activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.app.AppConfig;
import com.example.lord.goldenoffers.app.AppController;
import com.example.lord.goldenoffers.helper.Desire;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewOffersActivity extends AppCompatActivity {

    private static final String TAG = ViewOffersActivity.class.getSimpleName();
    private SessionManager session;
    private SQLiteHandlerForUsers db;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offers);
        session = new SessionManager(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        db = new SQLiteHandlerForUsers(getApplicationContext());
        List<Desire> listDesires = db.getDesires();
        if(listDesires.size() > 0) {
            setUpOffersToSQLite(listDesires);
        } else {
            //TODO list is empty
        }
    }

    private void setUpOffersToSQLite(List<Desire> listDesires) {
        for(Desire desire : listDesires) {
            getOffersByDesireBDID(desire.getDbID(), desire.getName());
        }
    }

    private void getOffersByDesireBDID(final int desireDBID, final String desireName) {

        String tag_string_req = "req_get_offers";

        pDialog.setMessage("Getting Offers");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.USER_URL_GET_OFFERS_BY_DESIRE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Getting Offers Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        JSONArray jArrOffers = jObj.getJSONArray("offers");
                        for(int pos = 0; pos < jArrOffers.length(); pos++){
/*
                            JSONArray offer = jArrOffers.getJSONArray(pos);
                            int dBID = offer.getInt(0);
                            String uid = offer.getString(1);
                            String productName = offer.getString(2);
                            String strPrice = offer.getString(3);
                            String description = offer.getString(4);
                            String image = offer.getString(5);
                            String regDate = offer.getString(6);
                            String expDate = offer.getString(7);
                            String businessName = offer.getString(8);
                            String longitude = offer.getString(9);
                            String latitude = offer.getString(10);
                            db.addOffer(
                                    dBID, desireDBID, uid, productName,
                                    strPrice, description, image, regDate,
                                    expDate, businessName, longitude, latitude
                            );*/
                        }
                        //TODO if pos == 0 offers dont exist
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Getting Offers Error: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name_desire", desireName);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
