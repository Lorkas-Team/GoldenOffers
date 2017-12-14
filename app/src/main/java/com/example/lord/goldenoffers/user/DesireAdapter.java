package com.example.lord.goldenoffers.user;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lord.goldenoffers.R;

import java.util.List;

public class DesireAdapter extends RecyclerView.Adapter<DesireAdapter.ProductViewHolder>  {
    private Context mCtx;
    private List<Desire> desireList;

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
    public void onBindViewHolder(DesireAdapter.ProductViewHolder holder, int position) {
        Desire desire = desireList.get(position);


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
}