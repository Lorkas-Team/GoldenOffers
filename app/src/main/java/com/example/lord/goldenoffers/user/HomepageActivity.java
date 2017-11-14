package com.example.lord.goldenoffers.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lord.goldenoffers.R;

public class HomepageActivity extends AppCompatActivity {

    public Button AddWishButton;
    public Button ViewWishButton;
    public Button SearchButton;
    public Button logoutButton;

    public void nav() {

        AddWishButton = (Button) findViewById(R.id.AddWishButton);
        AddWishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomepageActivity.this, AddWishActivity.class);
                startActivity(intent);
            }
        });

        ViewWishButton = (Button) findViewById(R.id.ViewWishButton);
        ViewWishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomepageActivity.this, ViewWishActivity.class);
                startActivity(intent);
            }
        });

        SearchButton = (Button) findViewById(R.id.SearchButton);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomepageActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //Needs logoutButton
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);
    }
}