package com.example.lord.goldenoffers.user;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lord.goldenoffers.R;

class RemoveDesireListRowAdapter extends ArrayAdapter<String> {

    public RemoveDesireListRowAdapter(Context context, String[] values) {
        super(context, R.layout.row_with_img_layout, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View view = theInflater.inflate(R.layout.row_with_img_layout, parent, false);

        String item = getItem(position);

        TextView txtView = (TextView) view.findViewById(R.id.txtView);
        txtView.setText(item);

        ImageView imgView = (ImageView) view.findViewById(R.id.imageView1);
        imgView.setImageResource(R.drawable.dot);

        return view;
    }
}

