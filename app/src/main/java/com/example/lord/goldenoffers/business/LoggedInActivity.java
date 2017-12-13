package com.example.lord.goldenoffers.business;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.helper.BusinessUser;
import com.example.lord.goldenoffers.helper.SQLiteHandler;
import com.example.lord.goldenoffers.helper.SessionManager;

public class LoggedInActivity extends Activity {

    private TextView tvName, tvEmail;
    private Button btnLogout, btnAddOffer, btnViewOffers;

    protected static BusinessUser BUSINESS;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        tvName = findViewById(R.id.NameTv);
        tvEmail = findViewById(R.id.EmailTv);
        btnLogout = findViewById(R.id.LogoutBtn);
        btnAddOffer = findViewById(R.id.addOfferBtn);
        btnViewOffers = findViewById(R.id.viewOffersBtn);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        //TODO must go to login ?
        BUSINESS = db.getUserDetails();
        tvName.setText(BUSINESS.getName());
        tvEmail.setText(BUSINESS.getEmail());

        btnAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAddOffer = new Intent(
                        LoggedInActivity.this,
                        AddOfferActivity.class
                );
                startActivity(gotoAddOffer);
            }
        });

        btnViewOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoViewOffer = new Intent(
                        LoggedInActivity.this,
                        MyOffersActivity.class
                );
                startActivity(gotoViewOffer);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {

        session.setLogin(false);
        db.deleteUsers();
        db.deleteOffers();
        Intent intent = new Intent(
                LoggedInActivity.this,
                BusinessLoginActivity.class
        );
        startActivity(intent);
        finish();
    }
}
