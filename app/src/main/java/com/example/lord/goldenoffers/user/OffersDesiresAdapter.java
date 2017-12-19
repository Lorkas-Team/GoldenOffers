package com.example.lord.goldenoffers.user;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.business.Offer;

import java.util.List;
import java.util.Locale;


public class OffersDesiresAdapter extends RecyclerView.Adapter<OffersDesiresAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Offer> offerList;

    public OffersDesiresAdapter(Context mCtx, List<Offer> offerList) {
        this.mCtx = mCtx;
        this.offerList = offerList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.offers_desires_listt, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OffersDesiresAdapter.ProductViewHolder holder, final int position) {
        final Offer offer = offerList.get(position);


        holder.imageView.setImageBitmap(offer.getPhoto());
        holder.textViewTitle.setText(offer.getProduct_name());
        holder.textViewShortDesc.setText(offer.getDescription());
        holder.textViewPrice.setText(String.valueOf(offer.getPrice()));
        holder.textViewExpDate.setText(String.valueOf(offer.getExpDate()));

        holder.mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /*
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(offer.getLatitude()),Double.parseDouble(offer.getLongitude()) );
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mCtx.startActivity(intent);
                */

                String label =  "Business name: " + offer.getBusiness_name() +"\t\n,Product name: "+offer.getProduct_name() + "\t\n,Price: " + offer.getPrice()   ;
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=%d&q=%f,%f (%s)", Double.parseDouble(offer.getLatitude()), Double.parseDouble(offer.getLongitude()), 10,Double.parseDouble(offer.getLatitude()), Double.parseDouble(offer.getLongitude()), label );
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mCtx.startActivity(intent);





            }
        });

    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewPrice, textViewRegDate, textViewExpDate;
        ImageView imageView;
        Button mapButton;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
            textViewExpDate = itemView.findViewById(R.id.expDate);
            textViewRegDate = itemView.findViewById(R.id.regDate);
            mapButton = itemView.findViewById(R.id.openMap);
        }
    }


}
