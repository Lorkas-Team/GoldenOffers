package com.example.lord.goldenoffers.business;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Offer> offerList;
    private static final String TAG = MyOffersActivity.class.getSimpleName();
    private ProgressDialog pDialog;

    public OfferAdapter(Context mCtx, List<Offer> offerList) {
        this.mCtx = mCtx;
        this.offerList = offerList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.offer_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Offer offer = offerList.get(position);
        pDialog = new ProgressDialog(mCtx);
        pDialog.setCancelable(false);

        holder.imageView.setImageBitmap(offer.getPhoto());
        holder.textViewTitle.setText(offer.getProduct_name());
        holder.textViewShortDesc.setText(offer.getDescription());
        holder.textViewPrice.setText(String.valueOf(offer.getPrice()));
        holder.textViewRegDate.setText(String.valueOf(offer.getRegDate()));
        holder.textViewExpDate.setText(String.valueOf(offer.getExpDate()));
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateOffers(String.valueOf(offer.getId()),String.valueOf(holder.textViewTitle.getText()),String.valueOf(holder.textViewShortDesc.getText()),String.valueOf(holder.textViewPrice.getText()),
                        String.valueOf(holder.textViewRegDate.getText()),String.valueOf(holder.textViewExpDate.getText()));

            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteOffers(String.valueOf(offer.getId()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        EditText textViewTitle, textViewShortDesc, textViewPrice, textViewRegDate , textViewExpDate;
        ImageView imageView;
        Button updateBtn,deleteBtn;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
            textViewExpDate = itemView.findViewById(R.id.expDate);
            textViewRegDate=itemView.findViewById(R.id.regDate);

            updateBtn=itemView.findViewById(R.id.updateBtn);
            deleteBtn=itemView.findViewById(R.id.deleteBtn);




        }
    }

    private void deleteOffers(final String offer_id){
        pDialog.setMessage("Trying to update your offers in database ...");
        showDialog();
        String tag_string_req = "update...offers";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.OFFER_DELETE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(mCtx, "Offer successfully deleted!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                mCtx,
                                MyOffersActivity.class);
                        mCtx.startActivity(intent);
                        ((MyOffersActivity)mCtx).finish();
                    }else{
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(mCtx,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(mCtx,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("offer_id",offer_id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    private void updateOffers(final String offer_id, final String product_name, final String description, final String price, final String regDate, final String expDate){
        pDialog.setMessage("Trying to update your offers in database ...");
        showDialog();
        String tag_string_req = "update...offers";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.OFFER_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(mCtx, "Offer successfully updated!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                mCtx,
                                MyOffersActivity.class);
                        mCtx.startActivity(intent);
                        ((MyOffersActivity)mCtx).finish();
                    }else{
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(mCtx,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(mCtx,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("offer_id",offer_id);
                params.put("product_name",product_name);
                params.put("description",description);
                params.put("price",price);
                params.put("regDate",regDate);
                params.put("expDate",expDate);

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