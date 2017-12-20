package com.example.lord.goldenoffers.user;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.lord.goldenoffers.business.BusinessLoginActivity;
import com.example.lord.goldenoffers.business.Offer;
import com.example.lord.goldenoffers.business.RegisterActivity;
import com.example.lord.goldenoffers.helper.SQLiteHandler;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DesireAdapter extends RecyclerView.Adapter<DesireAdapter.ProductViewHolder>  {
    private Context mCtx;
    private List<Desire> desireList;
    private ProgressDialog pDialog;
    private static final String TAG = DesireAdapter.class.getSimpleName();


    public DesireAdapter(Context mCtx, List<Desire> desireList) {
        this.mCtx = mCtx;
        this.desireList = desireList;
    }

    @Override
    public DesireAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.desire_list, null);
        return new DesireAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DesireAdapter.ProductViewHolder holder, int position) {
        final Desire desire = desireList.get(position);


        holder.textViewTitle.setText(desire.getName());
        holder.textViewMinPrice.setText(String.valueOf(desire.getPriceLow()));
        holder.textViewMaxPrice.setText(String.valueOf(desire.getPriceHigh()));


    }

    @Override
    public int getItemCount() {
        return desireList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        EditText textViewTitle,textViewMinPrice,textViewMaxPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewMinPrice = itemView.findViewById(R.id.textViewMinPrice);
            textViewMaxPrice = itemView.findViewById(R.id.textViewMaxPrice);

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